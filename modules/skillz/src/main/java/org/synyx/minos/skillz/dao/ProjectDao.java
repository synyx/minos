package org.synyx.minos.skillz.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Project;


/**
 * DAO interface to manage {@link Project} instances.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ProjectDao extends GenericDao<Project, Long> {

    /**
     * Returns all {@link Project}s publically available to all {@link User}s.
     * 
     * @return
     */
    @Query("select p from Project p where p.owner = null")
    List<Project> findPublicProjects();


    /**
     * Returns all {@link Project}s that are customly created by the given {@link User}.
     * 
     * @param user
     * @return
     */
    @Query("select p from Project p where p.owner = ?")
    List<Project> findProjectsFor(User user);


    /**
     * Returns the {@link Project} by the given name.
     * 
     * @param name
     * @return
     */
    @Query("select p from Project p where p.name = ?")
    Project findProjectByName(String name);
}
