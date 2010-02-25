package org.synyx.minos.skillz.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.MatrixTemplateDao;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Aspect to intercept calls to {@link UserManagement#save(User)} and creates
 * new {@link Resume}s for every {@link User} newly created.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Aspect
class CreateResumeForUserAspect {

    static final String POINTCUT =
            "execution(* org.synyx.minos.umt.service.UserManagement.save(..)) && args(user)";

    private MatrixTemplateDao templateDao;
    private ResumeManagement resumeManagement;


    /**
     * Creates a new {@link CreateResumeForUserAspect}.
     * 
     * @param resumeManagement
     * @param templateDao
     */
    public CreateResumeForUserAspect(ResumeManagement resumeManagement,
            MatrixTemplateDao templateDao) {

        this.resumeManagement = resumeManagement;
        this.templateDao = templateDao;
    }


    @Around(POINTCUT)
    public Object createResumeForUser(ProceedingJoinPoint joinPoint, User user)
            throws Throwable {

        boolean moduleNotInstalledYet = null == templateDao.findDefault();
        boolean creationNotRequired = !user.isNew();

        if (moduleNotInstalledYet || creationNotRequired) {
            return joinPoint.proceed();
        }

        Object result = joinPoint.proceed();

        resumeManagement
                .save(new Resume(user, templateDao.findDefault(), null));

        return result;
    }
}
