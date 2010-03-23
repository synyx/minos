package org.synyx.minos.skillz.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.skillz.SkillzPermissions;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Responsibility;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.SkillEntry;
import org.synyx.minos.skillz.domain.SkillMatrix;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.SkillManagement;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.util.Assert;


/**
 * Installer to setup basic data.
 * <p>
 * FIXME: Use services instead of DAOs to get rid of the {@code @Transactional}
 * annotation.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class SkillzInstaller extends SimpleNoOpLifecycle {

    private static final String LOREM_IPSUM =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    private final SkillManagement skillManagement;
    private final ResumeManagement resumeManagement;

    private final UserManagement userManagement;


    /**
     * Creates a new {@link SkillzInstaller}.
     * 
     * @param skillManagement
     * @param resumeManagement
     * @param userManagement
     */
    public SkillzInstaller(SkillManagement skillManagement,
            ResumeManagement resumeManagement, UserManagement userManagement) {

        Assert.notNull(skillManagement);
        Assert.notNull(resumeManagement);
        Assert.notNull(userManagement);

        this.skillManagement = skillManagement;
        this.resumeManagement = resumeManagement;
        this.userManagement = userManagement;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.SimpleNoOpLifecycle#install()
     */
    @Override
    public void install() {

        initPermissions();

        int count = 0;
        List<Level> levels = new ArrayList<Level>();
        for (String name : Arrays.asList("o", "+", "++", "+++")) {

            Level level = new Level(name, count++);

            if (count == 1) {
                level.setDefault(true);
            }

            levels.add(level);
            skillManagement.save(level);
        }

        Category webServices = new Category("WebServices");

        new Skill("Rest", webServices);
        new Skill("SOAP", webServices);

        skillManagement.save(webServices);

        Category frameworks = new Category("Frameworks");

        new Skill("Spring", frameworks);
        new Skill("Hibernate", frameworks);

        skillManagement.save(frameworks);

        List<String> responsibilities =
                Arrays.asList("Architektur", "Design", "Implementierung",
                        "Test", "User Interface", "Projektmanagement",
                        "Teamleitung");

        for (String responsibility : responsibilities) {
            skillManagement.save(new Responsibility(responsibility));
        }

        MatrixTemplate developersTemplate = new MatrixTemplate("Developers");
        developersTemplate.add(webServices);
        developersTemplate.setDefault(true);

        MatrixTemplate adminsTemplate = new MatrixTemplate("Admins");
        adminsTemplate.add(frameworks);

        skillManagement.save(developersTemplate);
        skillManagement.save(adminsTemplate);

        Project description1 = new Project("Project1", LOREM_IPSUM);
        Project description2 = new Project("Project2", LOREM_IPSUM);

        skillManagement.save(description1);
        skillManagement.save(description2);

        for (User user : userManagement.getUsers()) {

            SkillMatrix matrix = new SkillMatrix();
            matrix.setTemplate(developersTemplate);

            for (SkillEntry entry : matrix.getEntries()) {
                Collections.shuffle(levels);
                entry.setLevel(levels.get(0));
            }

            resumeManagement.save(matrix);

            Resume resume = new Resume(user, matrix, null);

            resumeManagement.save(resume);
        }
    }


    /**
     * Initializes basic skill module permissions to the default roles.
     */
    private void initPermissions() {

        Role userRole = userManagement.getRole(Role.USER_NAME);
        userRole.add(SkillzPermissions.SKILLZ_USER);

        Role adminRole = userManagement.getRole(Role.ADMIN_NAME);
        adminRole.add(SkillzPermissions.SKILLZ_ADMINISTRATION);

        userManagement.save(userRole);
        userManagement.save(adminRole);
    }
}
