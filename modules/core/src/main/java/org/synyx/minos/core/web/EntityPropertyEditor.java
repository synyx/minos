package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.io.Serializable;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.Persistable;


/**
 * Generic {@link PropertyEditor} to map {@link Persistable}s handled by a
 * {@link GenericDao} to their id's and vice versa.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EntityPropertyEditor<T extends GenericDao<S, Serializable>, S>
        extends AbstractEntityPropertyEditor<S> {

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
    public static <T extends GenericDao<S, Serializable>, S> EntityPropertyEditor<T, S> create(
            T dao) {

        return new EntityPropertyEditor<T, S>(dao);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.core.web.AbstractEntityPropertyEditor#lookupForId(java
     * .lang.Long)
     */
    @Override
    protected S lookupForId(Long id) {

        return dao.readByPrimaryKey(id);
    }
}
