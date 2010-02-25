package org.synyx.minos.skillz.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Unit test for {@link CreateResumeForUserAspect}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateResumeForUserAspectUnitTest {

    private CreateResumeForUserAspect aspect;

    @Mock
    private ResumeManagement resumeManagement;
    @Mock
    private SkillManagement skillManagement;
    @Mock
    private ProceedingJoinPoint joinPoint;


    @Before
    public void setUp() {

        aspect =
                new CreateResumeForUserAspect(resumeManagement, skillManagement);
    }


    @Test
    public void createsResumeForNewUser() throws Throwable {

        when(skillManagement.getDefaultTemplate()).thenReturn(
                new MatrixTemplate("template"));
        User user = new User("username", "foo@bar.de", "password");

        aspect.createResumeForUser(joinPoint, user);

        verify(resumeManagement, atLeastOnce()).save(isA(Resume.class));
    }


    @Test
    public void doesNotCreateResumesForUserUpdates() throws Throwable {

        when(skillManagement.getDefaultTemplate()).thenReturn(
                new MatrixTemplate("template"));
        User user = new User("username", "foo@bar.de", "password");
        user.setId(1L);

        aspect.createResumeForUser(joinPoint, user);

        verifyNoMoreInteractions(resumeManagement);
    }


    @Test
    public void preventsResumCreationBeforModuleIsInstalled() throws Throwable {

        User user = new User("username", "foo@bar.de", "password");

        aspect.createResumeForUser(joinPoint, user);

        verify(skillManagement, atLeastOnce()).getDefaultTemplate();
    }
}
