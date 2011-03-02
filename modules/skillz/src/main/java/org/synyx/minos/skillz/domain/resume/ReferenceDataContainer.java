package org.synyx.minos.skillz.domain.resume;

import org.springframework.util.Assert;

import org.synyx.hades.dao.GenericDao;

import java.util.Collection;


/**
 * Container class for {@link Collection} reference data.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class ReferenceDataContainer {

    private GenericDao<?, ?> genericDao;
    private Collection<?> collection;

    /**
     * Creates a {@link ReferenceDataContainer} with a generic DAO to fetch the reference data.
     *
     * @param genericDao
     */
    public ReferenceDataContainer(GenericDao<?, ?> genericDao) {

        Assert.notNull(genericDao);
        this.genericDao = genericDao;
    }


    /**
     * Creates a {@link ReferenceDataContainer} with a collection as reference data.
     *
     * @param collection
     */
    public ReferenceDataContainer(Collection<?> collection) {

        Assert.notNull(collection);
        this.collection = collection;
    }

    /**
     * Returns the given parameter choices as a {@link Collection}.
     *
     * @return
     */
    public Collection<?> getReferenceData() {

        if (genericDao == null) {
            return collection;
        } else {
            return genericDao.readAll();
        }
    }
}
