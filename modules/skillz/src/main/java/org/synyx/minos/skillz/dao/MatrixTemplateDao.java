package org.synyx.minos.skillz.dao;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;
import org.synyx.minos.skillz.domain.MatrixTemplate;


/**
 * DAO interface to handle {@link MatrixTemplate} instances.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface MatrixTemplateDao extends
        ExtendedGenericDao<MatrixTemplate, Long> {

    /**
     * Returns the default {@link MatrixTemplate}.
     * 
     * @return
     */
    @Query("select t from MatrixTemplate t where t.isDefault = true")
    MatrixTemplate findDefault();


    /**
     * Removes the default flag from all {@link MatrixTemplate}s but the given
     * one.
     * 
     * @param template
     */
    @Modifying
    @Query("update MatrixTemplate t set t.isDefault = false where t <> ?")
    void undefaultAllBut(MatrixTemplate template);
}
