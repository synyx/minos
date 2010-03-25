package org.synyx.minos.skillz.dao;

import java.util.List;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Project;



/**
 * DAO interface to manage {@link Activity} instances.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ActivityDao extends ExtendedGenericDao<Activity, Long> {

    /**
     * Returns all {@link Activity}s that reference the given project.
     * 
     * @param project
     * @return
     */
    List<Activity> findByProject(Project project);
}
