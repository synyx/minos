package org.synyx.minos.skillz.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.ProjectDao;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.test.AbstractDaoIntegrationTest;
import org.synyx.minos.umt.dao.UserDao;



/**
 * Integration test for {@link ProjectDao}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class ProjectDaoIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    private User user;
    private Project publicProject;
    private Project privateProject;


    @Before
    public void setUp() {

        user = userDao.save(new User("username", "user@mail.com", "password"));

        publicProject = new Project("common project");
        privateProject = new Project("custom project", null, user);

        projectDao.save(Arrays.asList(publicProject, privateProject));
    }


    @Test
    public void testFindsPublicProjectsOnly() throws Exception {

        List<Project> projects = projectDao.findPublicProjects();
        assertEquals(1, projects.size());
        assertTrue(projects.contains(publicProject));
    }


    @Test
    public void testFindsPrivateProjects() throws Exception {

        List<Project> projects = projectDao.findProjectsFor(user);
        assertEquals(1, projects.size());
        assertTrue(projects.contains(privateProject));
    }
}