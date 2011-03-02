package org.synyx.minos.skillz.service;

import org.springframework.core.convert.ConversionService;

import org.springframework.transaction.annotation.Transactional;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.PageImpl;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.ActivityDao;
import org.synyx.minos.skillz.dao.ResumeDao;
import org.synyx.minos.skillz.dao.SkillzMatrixDao;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;
import org.synyx.minos.skillz.domain.resume.ResumeAttributeFilter;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;
import org.synyx.minos.skillz.domain.resume.ResumeFilterParameters;
import org.synyx.minos.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Implementation of resume based services.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class ResumeManagementImpl implements ResumeManagement, ResumeAdminstration {

    private final ResumeDao resumeDao;
    private final ActivityDao activityDao;
    private final SkillzMatrixDao matrixDao;
    private final ConversionService conversionService;
    private List<ResumeFilter> resumeFilters = new ArrayList<ResumeFilter>();
    private List<ResumeAttributeFilter> resumeAttributeFilters = new ArrayList<ResumeAttributeFilter>();

    /**
     * Creates a new {@link ResumeManagementImpl}.
     *
     * @param resumeDao
     * @param activityDao
     * @param matrixDao
     */
    public ResumeManagementImpl(ResumeDao resumeDao, ActivityDao activityDao, SkillzMatrixDao matrixDao,
        ConversionService conversionService) {

        Assert.notNull(resumeDao);
        Assert.notNull(activityDao);
        Assert.notNull(matrixDao);
        Assert.notNull(conversionService);

        this.resumeDao = resumeDao;
        this.activityDao = activityDao;
        this.matrixDao = matrixDao;
        this.conversionService = conversionService;
    }

    /**
     * @param resumeFilters the resumeQueries to set
     */
    public void setResumeFilters(List<ResumeFilter> resumeFilters) {

        this.resumeFilters = resumeFilters;
    }


    /**
     * @param resumeAttributeFilters the resumeAttributeFilters to set
     */
    public void setResumeAttributeFilters(List<ResumeAttributeFilter> resumeAttributeFilters) {

        this.resumeAttributeFilters = resumeAttributeFilters;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.service.ResumeManagement#getResume(org.synyx.minos .core.domain.User)
     */
    public Resume getResume(User user) {

        return resumeDao.findBySubject(user);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#getResume(java.lang.Long)
     */
    public Resume getResume(Long id) {

        return resumeDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos. skillz.domain.Resume)
     */
    public Resume save(Resume resume) {

        return resumeDao.save(resume);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos. skillz.domain.SkillMatrix)
     */
    public SkillMatrix save(SkillMatrix matrix) {

        matrix.acknowledge();

        return matrixDao.save(matrix);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos. skillz.domain.Resume,
     * com.synyx.minos.skillz.domain.MatrixTemplate)
     */
    public Resume save(Resume resume, MatrixTemplate template) {

        SkillMatrix matrix = resume.getSkillz();
        matrix.setTemplate(template);

        return resumeDao.save(resume);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#save(com.synyx.minos. skillz.domain.Activity)
     */
    public Activity save(Activity reference) {

        Activity result = activityDao.save(reference);

        User user = result.getCreatedBy();
        Resume resume = getResume(user).add(result);

        resumeDao.save(resume);

        return result;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#delete(com.synyx.minos .skillz.domain.Activity)
     */
    public void delete(Activity reference) {

        Resume resume = getResume(reference.getCreatedBy());
        resume.remove(reference);

        resumeDao.save(resume);
        activityDao.delete(reference);
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.service.ResumeManagement#delete(org.synyx.minos .skillz.domain.Resume)
     */
    @Override
    public void delete(Resume resume) {

        resumeDao.delete(resume);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.skillz.service.ResumeManagement#getReference(java.lang .Long)
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
     * @see org.synyx.minos.skillz.service.ResumeManagement#getResumes(org.synyx. hades.domain.Pageable)
     */
    @Override
    public Page<Resume> getResumes(Pageable request) {

        return resumeDao.readAll(request);
    }


    @Override
    public Page<Resume> getResumesByFilter(Pageable pageable, ResumeFilter resumeFilter,
        Map<String, String[]> parameters) {

        ResumeFilterParameters filterParameters = resumeFilter.getParameters();
        Map<String, Object> filteredParamaters = filterParameters.getTypedParameters(parameters, conversionService);

        List<Resume> resumes = resumeDao.findByFilter(pageable, resumeFilter, filteredParamaters);

        List<Resume> filteredResumes = resumeFilter.filter(resumes, filteredParamaters);

        return new PageImpl<Resume>(paginate(filteredResumes, pageable), pageable, filteredResumes.size());
    }


    private List<Resume> paginate(List<Resume> resumes, Pageable pageable) {

        int toIndex = Math.min(resumes.size(), (pageable.getPageNumber() + 1) * pageable.getPageSize());

        return resumes.subList(pageable.getPageNumber() * pageable.getPageSize(), toIndex);
    }


    @Override
    public List<ResumeFilter> getResumeFilters() {

        return resumeFilters;
    }


    @Override
    public ResumeFilter getResumeFilter(String queryName) {

        for (ResumeFilter query : resumeFilters) {
            if (query.getMessageKey().equals(queryName)) {
                return query;
            }
        }

        return null;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.service.ResumeManagement#getFilteredResume(java .util.List)
     */
    @Override
    public Resume getFilteredResume(User user, List<ResumeAttributeFilter> filters) {

        Resume resume = getResume(user);

        for (ResumeAttributeFilter filter : filters) {
            filter.filter(resume);
        }

        return resume;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.service.ResumeManagement#getResumeAttributeFilters ()
     */
    @Override
    public List<ResumeAttributeFilter> getResumeAttributeFilters() {

        return resumeAttributeFilters;
    }
}
