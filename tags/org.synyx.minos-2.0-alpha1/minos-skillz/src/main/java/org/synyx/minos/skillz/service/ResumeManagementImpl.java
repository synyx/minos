package org.synyx.minos.skillz.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.ActivityDao;
import org.synyx.minos.skillz.dao.ResponsibilityDao;
import org.synyx.minos.skillz.dao.ResumeDao;
import org.synyx.minos.skillz.dao.SkillzMatrixDao;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Responsibility;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Implementation of resume based services.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class ResumeManagementImpl implements ResumeManagement {

    private AuthenticationService authenticationService;

    private ResumeDao resumeDao;
    private ActivityDao activityDao;
    private SkillzMatrixDao matrixDao;
    private ResponsibilityDao responsibilityDao;


    /**
     * Setter to inject {@link ResumeDao}.
     * 
     * @param resumeDao the resumeDao to set
     */
    public void setResumeDao(ResumeDao resumeDao) {

        this.resumeDao = resumeDao;
    }


    /**
     * Setter to inject {@link ActivityDao}.
     * 
     * @param activityDao the activityDao to set
     */
    public void setActivityDao(ActivityDao activityDao) {

        this.activityDao = activityDao;
    }


    /**
     * Setter to inject {@link SkillzMatrixDao}.
     * 
     * @param matrixDao the matrixDao to set
     */
    public void setMatrixDao(SkillzMatrixDao matrixDao) {

        this.matrixDao = matrixDao;
    }


    /**
     * Setter to inject {@link AuthenticationService} to lookup the current
     * {@link User}.
     * 
     * @param authenticationService the authenticationService to set
     */
    public void setAuthenticationService(
            AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }


    /**
     * @param responsibilityDao the responsibilityDao to set
     */
    public void setResponsibilityDao(ResponsibilityDao responsibilityDao) {

        this.responsibilityDao = responsibilityDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.ResumeManagement#getResume()
     */
    public Resume getResume() {

        return resumeDao.findBySubject(authenticationService.getCurrentUser());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#getResume(java.lang.Long)
     */
    public Resume getResume(Long id) {

        return resumeDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos.
     * skillz.domain.Resume)
     */
    public Resume save(Resume resume) {

        return resumeDao.save(resume);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos.
     * skillz.domain.SkillMatrix)
     */
    public SkillMatrix save(SkillMatrix matrix) {

        matrix.acknowledge();

        return matrixDao.save(matrix);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos.
     * skillz.domain.Resume, com.synyx.minos.skillz.domain.MatrixTemplate)
     */
    public Resume save(Resume resume, MatrixTemplate template) {

        SkillMatrix matrix = resume.getSkillz();
        matrix.setTemplate(template);

        return resumeDao.save(resume);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos.
     * skillz.domain.Activity)
     */
    public Activity save(Activity reference) {

        Resume resume = getResume().add(reference);

        resumeDao.save(resume);
        return activityDao.save(reference);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#delete(com.synyx.minos
     * .skillz.domain.Activity)
     */
    public void delete(Activity reference) {

        Resume resume = getResume();
        resume.remove(reference);

        resumeDao.save(resume);
        activityDao.delete(reference);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#getReference(java.lang
     * .Long)
     */
    public Activity getReference(Long id) {

        return activityDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.ResumeManagement#getResumes()
     */
    public List<Resume> getResumes() {

        return resumeDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.ResumeManagement#getResumes(org.synyx.
     * hades.domain.Pageable)
     */
    @Override
    public Page<Resume> getResumes(Pageable request) {

        return resumeDao.readAll(request);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.ResumeManagement#getResponsibilities()
     */
    @Override
    public List<Responsibility> getResponsibilities() {

        return responsibilityDao.readAll();
    }
}
