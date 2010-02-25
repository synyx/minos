package org.synyx.minos.skillz.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateMidnight;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.SimpleLifecycle;
import org.synyx.minos.skillz.dao.ActivityDao;
import org.synyx.minos.skillz.dao.CategoryDao;
import org.synyx.minos.skillz.dao.LevelDao;
import org.synyx.minos.skillz.dao.MatrixTemplateDao;
import org.synyx.minos.skillz.dao.ProjectDao;
import org.synyx.minos.skillz.dao.ResponsibilityDao;
import org.synyx.minos.skillz.dao.ResumeDao;
import org.synyx.minos.skillz.dao.SkillzDao;
import org.synyx.minos.skillz.dao.SkillzMatrixDao;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Responsibility;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.SkillEntry;
import org.synyx.minos.skillz.domain.SkillMatrix;
import org.synyx.minos.skillz.security.SkillzPermissions;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Installer to setup basic data.
 * <p>
 * FIXME: Use services instead of DAOs to get rid of the {@code @Transactional}
 * annotation.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class SkillzInstaller extends SimpleLifecycle {

    private static final String LOREM_IPSUM =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    private UserDao userDao;

    private RoleDao roleDao;

    private SkillzDao skillzDao;

    private CategoryDao categoryDao;

    private SkillzMatrixDao matrixDao;

    private LevelDao levelDao;

    private ProjectDao projectDao;

    private ActivityDao activityDao;

    private ResumeDao resumeDao;

    private MatrixTemplateDao templateDao;

    private ResponsibilityDao responsibilityDao;


    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }


    /**
     * @param roleDao the roleDao to set
     */
    public void setRoleDao(RoleDao roleDao) {

        this.roleDao = roleDao;
    }


    /**
     * @param skillzDao the skillzDao to set
     */
    public void setSkillzDao(SkillzDao skillzDao) {

        this.skillzDao = skillzDao;
    }


    /**
     * @param categoryDao the categoryDao to set
     */
    public void setCategoryDao(CategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }


    /**
     * @param matrixDao the matrixDao to set
     */
    public void setMatrixDao(SkillzMatrixDao matrixDao) {

        this.matrixDao = matrixDao;
    }


    /**
     * @param levelDao the levelDao to set
     */
    public void setLevelDao(LevelDao levelDao) {

        this.levelDao = levelDao;
    }


    /**
     * @param projectDao the projectDao to set
     */
    public void setProjectDao(ProjectDao projectDao) {

        this.projectDao = projectDao;
    }


    /**
     * @param activityDao the activityDao to set
     */
    public void setActivityDao(ActivityDao activityDao) {

        this.activityDao = activityDao;
    }


    /**
     * @param resumeDao the resumeDao to set
     */
    public void setResumeDao(ResumeDao resumeDao) {

        this.resumeDao = resumeDao;
    }


    /**
     * @param templateDao the templateDao to set
     */
    public void setTemplateDao(MatrixTemplateDao templateDao) {

        this.templateDao = templateDao;
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
     * @see com.synyx.minos.core.module.SimpleLifecycle#onStart()
     */
    @Override
    public void onStart() {

        if (0 == skillzDao.count()) {

            initPermissions();

            int count = 0;
            List<Level> levels = new ArrayList<Level>();
            for (String name : Arrays.asList("o", "+", "++", "+++")) {

                Level level = new Level(name, count++);

                if (count == 1) {
                    level.setDefault(true);
                }

                levels.add(level);
                levelDao.save(level);
            }

            Category webServices = new Category("WebServices");

            Skill rest = new Skill("Rest", webServices);
            Skill soap = new Skill("SOAP", webServices);

            categoryDao.save(webServices);

            Category frameworks = new Category("Frameworks");

            Skill spring = new Skill("Spring", frameworks);
            Skill hibernate = new Skill("Hibernate", frameworks);

            categoryDao.save(frameworks);

            List<String> responsibilities =
                    Arrays.asList("Architektur", "Design", "Implementierung",
                            "Test", "User Interface", "Projektmanagement",
                            "Teamleitung");

            for (String responsibility : responsibilities) {
                responsibilityDao.save(new Responsibility(responsibility));
            }

            MatrixTemplate developersTemplate =
                    new MatrixTemplate("Developers");
            developersTemplate.add(webServices);
            developersTemplate.setDefault(true);

            MatrixTemplate adminsTemplate = new MatrixTemplate("Admins");
            adminsTemplate.add(frameworks);

            templateDao.save(Arrays.asList(developersTemplate, adminsTemplate));

            Project description1 = new Project("Project1", LOREM_IPSUM);
            Project description2 = new Project("Project2", LOREM_IPSUM);

            projectDao.save(Arrays.asList(description1, description2));

            for (User user : userDao.readAll()) {

                SkillMatrix matrix = new SkillMatrix();
                matrix.setTemplate(developersTemplate);

                for (SkillEntry entry : matrix.getEntries()) {
                    Collections.shuffle(levels);
                    entry.setLevel(levels.get(0));
                }

                matrixDao.save(matrix);

                Activity reference1 =
                        new Activity(description1,
                                new DateMidnight(2009, 3, 1), null);
                reference1.setAdditionalDescription(LOREM_IPSUM);

                Activity reference2 =
                        new Activity(description2,
                                new DateMidnight(2009, 3, 1),
                                new DateMidnight());
                reference2.setAdditionalDescription("Foo");
                reference2.setOmitProjectDescription(true);

                activityDao.save(Arrays.asList(reference1, reference2));

                Resume resume =
                        new Resume(user, matrix, Arrays.asList(reference1,
                                reference2));

                resumeDao.save(resume);
            }
        }
    }


    /**
     * Initializes basic skill module permissions to the default roles.
     */
    private void initPermissions() {

        Role userRole = roleDao.findUserRole();
        userRole.add(SkillzPermissions.SKILLZ_USER);

        Role adminRole = roleDao.findAdminRole();
        adminRole.add(SkillzPermissions.SKILLZ_ADMINISTRATION);

        roleDao.save(Arrays.asList(userRole, adminRole));
    }
}
