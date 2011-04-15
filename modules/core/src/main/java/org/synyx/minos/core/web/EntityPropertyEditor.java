package org.synyx.minos.core.web;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.SimpleTypeConverter;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.Persistable;
import org.synyx.hades.util.ClassUtils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import java.io.Serializable;


/**
 * Generic {@link PropertyEditor} to map {@link Persistable}s handled by a {@link GenericDao} to their id's and vice
 * versa.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EntityPropertyEditor<T extends Serializable> extends PropertyEditorSupport {

    private final GenericDao<?, T> dao;
    private final PropertyEditorRegistry registry;

    /**
     * Creates a new {@link EntityPropertyEditor} for the given dao.
     *
     * @param dao
     * @param registry
     */
    public EntityPropertyEditor(GenericDao<?, T> dao, PropertyEditorRegistry registry) {

        this.dao = dao;
        this.registry = registry;
    }

    @Override
    public void setAsText(String idAsString) {

        if (StringUtils.isBlank(idAsString)) {
            setValue(null);

            return;
        }

        setValue(dao.readByPrimaryKey(getId(idAsString)));
    }


    @Override
    public String getAsText() {

        Persistable<?> value = (Persistable<?>) getValue();

        if (null == value) {
            return null;
        }

        return null == value.getId() ? null : value.getId().toString();
    }


    /**
     * Returns the actual typed id. Looks up an available customly registered {@link PropertyEditor} from the
     * {@link PropertyEditorRegistry} before falling back on a {@link SimpleTypeConverter} to translate the
     * {@link String} id into the type one.
     *
     * @param idAsString
     * @return
     */
    @SuppressWarnings("unchecked")
    private T getId(String idAsString) {

        Class<T> idClass = (Class<T>) ClassUtils.getIdClass(dao.getClass());

        PropertyEditor idEditor = registry.findCustomEditor(idClass, null);

        if (idEditor != null) {
            idEditor.setAsText(idAsString);

            return (T) idEditor.getValue();
        }

        return new SimpleTypeConverter().convertIfNecessary(idAsString, idClass);
    }
}
