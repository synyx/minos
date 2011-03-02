package org.synyx.minos.skillz.dao;

import org.synyx.hades.dao.GenericDao;

import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Project;

import java.util.List;


/**
 * DAO interface to manage {@link Activity} instances.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ActivityDao extends GenericDao<Activity, Long> {

    /**
     * Returns all {@link Activity}s that reference the given project.
     *
     * @param project
     * @return
     */
    List<Activity> findByProject(Project project);
}
