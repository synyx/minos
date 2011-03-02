package org.synyx.minos.skillz.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;

import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.MatrixTemplate;

import java.util.List;


/**
 * DAO interface to handle {@link MatrixTemplate} instances.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface MatrixTemplateDao extends GenericDao<MatrixTemplate, Long> {

    /**
     * Returns the default {@link MatrixTemplate}.
     *
     * @return
     */
    @Query("select t from MatrixTemplate t where t.isDefault = true")
    MatrixTemplate findDefault();


    /**
     * Removes the default flag from all {@link MatrixTemplate}s but the given one.
     *
     * @param template
     */
    @Modifying
    @Query("update MatrixTemplate t set t.isDefault = false where t <> ?")
    void undefaultAllBut(MatrixTemplate template);


    /**
     * Returns a {@link List} of all {@link MatrixTemplate}s containing the given {@link Category}.
     *
     * @param category
     * @return
     */
    @Query("select t from MatrixTemplate t where ?1 member of t.categories")
    List<MatrixTemplate> findByCategory(Category category);


    MatrixTemplate findByName(String name);
}
