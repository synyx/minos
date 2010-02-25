package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.Persistable;


/**
 * Generic {@link PropertyEditor} to map {@link Persistable}s handled by a
 * {@link GenericDao} to their id's and vice versa.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EntityPropertyEditor<T extends GenericDao<?, Long>> extends
        PropertyEditorSupport {

    private T dao;


    /**
     * Creates a new {@link EntityPropertyEditor} for the given dao.
     * 
     * @param dao
     */
    protected EntityPropertyEditor(T dao) {

        this.dao = dao;
    }


    /**
     * Static factory method to create {@link EntityPropertyEditor} instances.
     * 
     * @param <T>
     * @param dao
     * @return
     */
    public static <T extends GenericDao<?, Long>> EntityPropertyEditor<T> create(
            T dao) {

        return new EntityPropertyEditor<T>(dao);
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String idAsString) throws IllegalArgumentException {

        if (StringUtils.isBlank(idAsString)) {
            setValue(null);
            return;
        }

        Long id = Long.parseLong(idAsString);
        setValue(dao.readByPrimaryKey(id));
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getAsText() {

        Persistable<Long> value = (Persistable<Long>) getValue();
        return null == value ? "" : value.getId().toString();
    }
}
