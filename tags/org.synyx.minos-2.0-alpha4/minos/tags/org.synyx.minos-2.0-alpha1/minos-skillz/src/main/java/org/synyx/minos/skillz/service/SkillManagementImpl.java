package org.synyx.minos.skillz.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.dao.CategoryDao;
import org.synyx.minos.skillz.dao.LevelDao;
import org.synyx.minos.skillz.dao.MatrixTemplateDao;
import org.synyx.minos.skillz.dao.ProjectDao;
import org.synyx.minos.skillz.dao.ResumeDao;
import org.synyx.minos.skillz.dao.SkillzDao;
import org.synyx.minos.skillz.dao.SkillzMatrixDao;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Implementation of skill management services.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class SkillManagementImpl implements SkillManagement {

    private ProjectDao projectDao;
    private ResumeDao resumeDao;
    private SkillzMatrixDao matrixDao;
    private SkillzDao skillzDao;
    private CategoryDao categoryDao;
    private LevelDao levelDao;
    private MatrixTemplateDao templateDao;


    /**
     * Setter to inject the {@link ProjectDao}.
     * 
     * @param projectDao the projectDao to set
     */
    public void setProjectDao(ProjectDao projectDao) {

        this.projectDao = projectDao;
    }


    /**
     * Setter to inject the {@link CategoryDao}.
     * 
     * @param categoryDao the categoryDao to set
     */
    public void setCategoryDao(CategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }


    /**
     * Setter to inject the {@link LevelDao}.
     * 
     * @param levelDao the levelDao to set
     */
    public void setLevelDao(LevelDao levelDao) {

        this.levelDao = levelDao;
    }


    /**
     * Setter to inject the {@link SkillzMatrixDao}.
     * 
     * @param matrixDao the matrixDao to set
     */
    public void setMatrixDao(SkillzMatrixDao matrixDao) {

        this.matrixDao = matrixDao;
    }


    /**
     * Setter to inject the {@link ResumeDao}.
     * 
     * @param resumeDao the resumeDao to set
     */
    public void setResumeDao(ResumeDao resumeDao) {

        this.resumeDao = resumeDao;
    }


    /**
     * Setter to inject the {@link SkillzDao}.
     * 
     * @param skillzDao the skillzDao to set
     */
    public void setSkillzDao(SkillzDao skillzDao) {

        this.skillzDao = skillzDao;
    }


    /**
     * Setter to inject the {@link MatrixTemplateDao}.
     * 
     * @param templateDao the templateDao to set
     */
    public void setTemplateDao(MatrixTemplateDao templateDao) {

        this.templateDao = templateDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#save(com.synyx.minos.skillz
     * .domain.Skill)
     */
    @Override
    public Skill save(Skill skill) {

        // Add skill to existing matrixes
        if (skill.isNew()) {

            List<SkillMatrix> matrixes =
                    matrixDao.findByCategory(skill.getCategory());
            Level defaultLevel = getDefaultLevel();

            for (SkillMatrix matrix : matrixes) {
                matrix.add(skill, defaultLevel);
            }
        }

        return skillzDao.save(skill);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#moveSkill(com.synyx.minos
     * .skillz.domain.Skill, com.synyx.minos.skillz.domain.Category)
     */
    @Override
    public void moveSkill(Skill skill, Category category) {

        Category oldCategory = skill.getCategory();
        skill.setCategory(category);

        categoryDao.save(oldCategory);
        categoryDao.save(category);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#delete(com.synyx.minos
     * .skillz.domain.Skill)
     */
    @Override
    public void delete(Skill skill) {

        List<SkillMatrix> matrixes = matrixDao.findBySkill(skill);

        for (SkillMatrix matrix : matrixes) {
            matrix.remove(skill);
        }

        matrixDao.save(matrixes);
        skillzDao.delete(skill);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getCategories()
     */
    @Override
    public List<Category> getCategories() {

        return categoryDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#getCategory(java.lang.
     * Long)
     */
    @Override
    public Category getCategory(Long id) {

        return categoryDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getProjects()
     */
    @Override
    public List<Project> getProjects() {

        return projectDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getPublicProjects()
     */
    @Override
    public List<Project> getPublicProjects() {

        return projectDao.findPublicProjects();
    }


    /*
     * (non-Javadoc)
     * 
     * @seecom.synyx.minos.skillz.service.SkillManagement#
     * getPrivateProjectsForCurrentUser()
     */
    @Override
    public List<Project> getPrivateProjectsFor(User user) {

        return projectDao.findProjectsFor(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#save(com.synyx.minos.skillz
     * .domain.Project)
     */
    @Override
    public Project save(Project project) {

        return projectDao.save(project);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#deleteProject(java.lang
     * .Long)
     */
    @Override
    public void delete(Project project) {

        List<Resume> resumes = resumeDao.findByProject(project);

        for (Resume resume : resumes) {
            resume.remove(project);
        }

        resumeDao.save(resumes);
        projectDao.delete(project);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getDefaultLevel()
     */
    @Override
    public Level getDefaultLevel() {

        return levelDao.findDefault();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getLevels()
     */
    @Override
    public List<Level> getLevels() {

        return levelDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#save(com.synyx.minos.skillz
     * .domain.Level)
     */
    @Override
    public Level save(Level level) {

        if (level.isDefault()) {
            levelDao.undefaultAllBut(level);
        }

        return levelDao.save(level);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#getProject(java.lang.Long)
     */
    @Override
    public Project getProject(Long id) {

        return projectDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.skillz.service.SkillManagement#getTemplates()
     */
    @Override
    public List<MatrixTemplate> getTemplates() {

        return templateDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#delete(com.synyx.minos
     * .skillz.domain.MatrixTemplate)
     */
    @Override
    public void delete(MatrixTemplate template) {

        templateDao.delete(template);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#getTemplate(java.lang.
     * Long)
     */
    @Override
    public MatrixTemplate getTemplate(Long id) {

        return templateDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.skillz.service.SkillManagement#save(com.synyx.minos.skillz
     * .domain.MatrixTemplate)
     */
    @Override
    public MatrixTemplate save(MatrixTemplate template) {

        if (template.isDefault()) {
            templateDao.undefaultAllBut(template);
        }

        template = templateDao.save(template);

        // Update existing SkillMatrix instances
        for (SkillMatrix matrix : matrixDao.findByTemplate(template)) {
            matrix.setTemplate(template);
            matrixDao.save(matrix);
        }

        return template;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.SkillManagement#save(org.synyx.minos.skillz
     * .domain.Category)
     */
    @Override
    public Category save(Category category) {

        return categoryDao.save(category);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.SkillManagement#delete(org.synyx.minos
     * .skillz.domain.Category)
     */
    @Override
    public void delete(Category category) {

        categoryDao.delete(category);
    }
}
