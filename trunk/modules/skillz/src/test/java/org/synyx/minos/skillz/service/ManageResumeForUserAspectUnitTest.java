package org.synyx.minos.skillz.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Unit test for {@link ManageResumeForUserAspect}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ManageResumeForUserAspectUnitTest {

    private ManageResumeForUserAspect aspect;
    private User user;

    @Mock
    private ResumeManagement resumeManagement;
    @Mock
    private SkillManagement skillManagement;
    @Mock
    private ProceedingJoinPoint joinPoint;


    @Before
    public void setUp() {

        aspect =
                new ManageResumeForUserAspect(resumeManagement, skillManagement);
        user = new User("username", "foo@bar.de", "password");
    }


    @Test
    public void createsResumeForNewUser() throws Throwable {

        when(skillManagement.getDefaultTemplate()).thenReturn(
                new MatrixTemplate("template"));

        aspect.createResumeForUser(joinPoint, user);

        verify(resumeManagement).save(isA(Resume.class));
    }


    @Test
    public void doesNotCreateResumesForUserUpdates() throws Throwable {

        when(skillManagement.getDefaultTemplate()).thenReturn(
                new MatrixTemplate("template"));
        user.setId(1L);

        aspect.createResumeForUser(joinPoint, user);

        verifyNoMoreInteractions(resumeManagement);
    }


    @Test
    public void preventsResumCreationBeforModuleIsInstalled() throws Throwable {

        aspect.createResumeForUser(joinPoint, user);

        verify(skillManagement).getDefaultTemplate();
    }


    @Test
    public void deletesResumeForDeletedUser() throws Throwable {

        when(resumeManagement.getResume(user)).thenReturn(
                new Resume(user, new MatrixTemplate("name"),
                        new ArrayList<Activity>()));

        aspect.deleteResumeForUser(joinPoint, user);

        verify(resumeManagement).delete(isA(Resume.class));
    }

}
