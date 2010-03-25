package org.synyx.minos.core.authentication;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.InitializingBean;


/**
 * Abstract base class to lookup permissions from constants. Implement
 * {@link #getClassesToScan()} to provide all classes to be scanned for
 * permissions. Each of them has to be declared as {@code public}, {@code
 * static}, {@code final} fields.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class ReflectivePermissionAwareSupport implements
        PermissionAware, InitializingBean {

    protected static final Collection<Class<?>> NONE = Collections.emptyList();

    private Collection<String> permissions;


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.authentication.PermissionAware#getPermissions()
     */
    @Override
    public Collection<String> getPermissions() {

        return this.permissions;
    }


    /**
     * Looks up all {@link Permission} instances from the given {@link Class}.
     * 
     * @param clazz
     * @return
     */
    protected Collection<String> getPermissionsFrom(Class<?> clazz) {

        Collection<String> permissions = new HashSet<String>();

        for (Field field : clazz.getDeclaredFields()) {

            String permission = getPermissionFromField(field);

            if (null != permission) {
                permissions.add(permission);
            }
        }

        return permissions;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        Collection<String> permissions = new HashSet<String>();

        for (Class<?> clazz : getClassesToScan()) {

            permissions.addAll(getPermissionsFrom(clazz));
        }

        this.permissions = permissions;
    }


    /**
     * Returns whether the given field is to be considered as permission
     * constant. Therefore it has to be public static final and of type
     * {@link String}.
     * 
     * @param field
     * @return
     */
    private static boolean isPermissionConstant(Field field) {

        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers());
        boolean isPublic = Modifier.isPublic(field.getModifiers());

        boolean isPermission = String.class.equals(field.getType());

        return isPermission && isStatic && isFinal && isPublic;
    }


    /**
     * Returns the {@link Permission} captured in the given {@link Field} or
     * {@literal null} if the given {@link Field} according to the spec tied to
     * {@link #isPermissionConstant(Field)}.
     * 
     * @param field
     * @return
     */
    private static String getPermissionFromField(Field field) {

        if (!isPermissionConstant(field)) {

            return null;
        }

        try {
            return (String) field.get(null);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }


    /**
     * Return the classes to be scanned for {@link Permission} instances
     * declared in constant fields.
     * 
     * @return
     */
    protected abstract Collection<Class<?>> getClassesToScan();
}