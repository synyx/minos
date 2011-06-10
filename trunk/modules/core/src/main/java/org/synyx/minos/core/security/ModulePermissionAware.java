package org.synyx.minos.core.security;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import org.synyx.minos.core.module.Module;

import java.util.ArrayList;
import java.util.Collection;


/**
 * {@link PermissionAware} that looks up permissions for the configured {@link Module} doing reflection lookups on a
 * class matching the pattern <code>${module.basePackage}.${module.id}Permissions</code> with the id capitalized, e.g.
 * the permissions constant class for {@code umt} module would be expected in {@link org.synyx.minos.umt.UmtPermissions}
 * .
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ModulePermissionAware extends ReflectivePermissionAwareSupport {

    private static final String PERMISSION_CLASS_TEMPLATE = "%s.%sPermissions";

    private final Module module;

    /**
     * Creates a new {@link ModulePermissionAware} for the given {@link Module}.
     *
     * @param  module  a minos {@code Module}
     */
    public ModulePermissionAware(Module module) {

        this.module = module;
    }

    @Override
    protected Collection<Class<?>> getClassesToScan() {

        String id = StringUtils.capitalize(module.getIdentifier());

        String className = String.format(PERMISSION_CLASS_TEMPLATE, module.getBasePackage(), id);

        if (ClassUtils.isPresent(className, null)) {
            try {
                Class<?> clazz = ClassUtils.forName(className, null);
                Collection<Class<?>> result = new ArrayList<Class<?>>();
                result.add(clazz);

                return result;
            } catch (ClassNotFoundException e) {
                return NONE;
            } catch (LinkageError e) {
                return NONE;
            }
        }

        return NONE;
    }
}
