package org.synyx.minos.skillz.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Provides aspects for to manage the relation between {@link User} and
 * {@link Resume}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Markus Knittig - knittig@synyx.de
 */
@Aspect
class ManageResumeForUserAspect {

    private final SkillManagement skillManagement;
    private final ResumeManagement resumeManagement;


    /**
     * Creates a new {@link ManageResumeForUserAspect}.
     * 
     * @param resumeManagement
     * @param skillManagement
     */
    public ManageResumeForUserAspect(ResumeManagement resumeManagement,
            SkillManagement skillManagement) {

        this.resumeManagement = resumeManagement;
        this.skillManagement = skillManagement;
    }


    /**
     * Aspect method that intercept calls to {@link UserManagement#save(User)}
     * and creates new {@link Resume}s for every {@link User} newly created.
     * 
     * @param joinPoint
     * @param user
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.synyx.minos.umt.service.UserManagement.save(..)) && args(user)")
    public Object createResumeForUser(ProceedingJoinPoint joinPoint, User user)
            throws Throwable {

        boolean moduleNotInstalledYet =
                null == skillManagement.getDefaultTemplate();
        boolean creationNotRequired = !user.isNew();

        if (moduleNotInstalledYet || creationNotRequired) {
            return joinPoint.proceed();
        }

        Object result = joinPoint.proceed();

        resumeManagement.save(new Resume(user, skillManagement
                .getDefaultTemplate(), null));

        return result;
    }


    /**
     * Aspect method that intercept calls to {@link UserManagement#delete(User)}
     * and deletes the {@link Resume}s for every {@link User} deleted.
     * 
     * @param joinPoint
     * @param user
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.synyx.minos.umt.service.UserManagement.delete(..)) && args(user)")
    public Object deleteResumeForUser(ProceedingJoinPoint joinPoint, User user)
            throws Throwable {

        Object result = joinPoint.proceed();

        resumeManagement.delete(resumeManagement.getResume(user));

        return result;
    }

}
