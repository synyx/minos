package org.synyx.minos.skillz.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public abstract class AbstractDocbookCreatorIntegrationTest {

    protected DocbookTemplateService docbookTemplateService;
    protected Resume resume;


    public void setUp() throws Exception {

        User user = new User("username", "test@test.com", "password");
        resume =
                new Resume(user, new MatrixTemplate("name"),
                        new ArrayList<Activity>());
        Category category = new Category("categoryname");
        Skill skill = new Skill("skillname", category);
        Level level = new Level("levelname", 0);
        resume.getSkillz().add(skill, level);
        resume.setTitle("Dr.");

        docbookTemplateService = mock(DocbookTemplateService.class);
        when(
                docbookTemplateService.createDocbookXml((Resume) anyObject(),
                        anyString())).thenReturn(
                IOUtils.toString(new ClassPathResource("/docbookexample.xml")
                        .getInputStream()));
    }

}
