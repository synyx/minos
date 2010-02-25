package org.synyx.minos.skillz.service;

import static org.easymock.EasyMock.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.MatrixTemplateDao;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Unit test for {@link CreateResumeForUserAspect}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CreateResumeForUserAspectUnitTest {

    private CreateResumeForUserAspect aspect;

    private ResumeManagement resumeManagement;
    private MatrixTemplateDao templateDao;
    private ProceedingJoinPoint joinPoint;


    @Before
    public void setUp() {

        resumeManagement = createNiceMock(ResumeManagement.class);
        templateDao = createNiceMock(MatrixTemplateDao.class);
        joinPoint = createNiceMock(ProceedingJoinPoint.class);

        aspect = new CreateResumeForUserAspect(resumeManagement, templateDao);
    }


    @Test
    public void createsResumeForNewUser() throws Throwable {

        expect(resumeManagement.save(isA(Resume.class))).andReturn(null)
                .atLeastOnce();
        expect(templateDao.findDefault()).andReturn(
                new MatrixTemplate("template")).atLeastOnce();
        replay(resumeManagement, templateDao, joinPoint);

        User user = new User("username", "foo@bar.de", "password");

        aspect.createResumeForUser(joinPoint, user);

        verify(resumeManagement, templateDao, joinPoint);
    }


    @Test
    public void doesNotCreateResumesForUserUpdates() throws Throwable {

        expect(templateDao.findDefault()).andReturn(
                new MatrixTemplate("template")).anyTimes();
        replay(resumeManagement, templateDao, joinPoint);

        User user = new User("username", "foo@bar.de", "password");
        user.setId(1L);

        aspect.createResumeForUser(joinPoint, user);

        verify(resumeManagement, templateDao, joinPoint);
    }


    @Test
    public void preventsResumCreationBeforModuleIsInstalled() throws Throwable {

        // Simulate module not installed
        expect(templateDao.findDefault()).andReturn(null).anyTimes();
        replay(resumeManagement, templateDao, joinPoint);

        User user = new User("username", "foo@bar.de", "password");

        aspect.createResumeForUser(joinPoint, user);

        verify(resumeManagement, templateDao, joinPoint);
    }
}
