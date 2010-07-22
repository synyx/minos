package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;


/**
 * Test for {@link VelocityDocbookTemplateServiceImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class VelocityDocbookTemplateServiceIntegrationTest {

    private DocbookTemplateService docbookTemplateService;
    private Resume resume;


    @Before
    public void setUp() {

        docbookTemplateService =
                new VelocityDocbookTemplateServiceImpl(createVelocityEngine(), "resume-template/resume.vm");
        User user = new User("username", "foo@bar.de", "password");
        user.setFirstname("\"foo\"");
        user.setLastname("bar");
        resume = new Resume(user, new MatrixTemplate("template"), new ArrayList<Activity>());
        resume.setBirthday(new DateMidnight(1982, 1, 1));
    }


    @Test
    public void createsDocbookXml() throws Exception {

        String docbookXml = docbookTemplateService.createDocbookXml(resume, new ArrayList<Level>(), null);

        assertThat(docbookXml, both(containsString("&quot;foo&quot; bar")).and(containsString("1982")));
    }


    @Test
    public void createsDocbookXmlWithReference() throws Exception {

        resume.add(new Activity(new Project("projectname"), new DateMidnight(2010, 2, 12)));

        String docbookXml = docbookTemplateService.createDocbookXml(resume, new ArrayList<Level>(), null);

        assertThat(docbookXml, both(containsString("projectname")).and(containsString("[Februar 2010 - heute]")));
    }


    @Test
    public void createsDocbookXmlWithSkill() throws Exception {

        Category category = new Category("categoryname");
        List<Level> levels = new ArrayList<Level>();
        levels.add(new Level("level0", 0));
        levels.add(new Level("level1", 1));
        resume.getSkillz().add(new Skill("skill1", category), levels.get(0));
        resume.getSkillz().add(new Skill("skill2", category), levels.get(1));

        String docbookXml = docbookTemplateService.createDocbookXml(resume, levels, null);

        assertThat(docbookXml, both(containsString("categoryname")).and(containsString("skill2")));
        assertThat(docbookXml, both(containsString("level1")).and(containsString("X")));
    }


    private VelocityEngine createVelocityEngine() {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("class.resource.loader.cache", "false");
        velocityEngine.setProperty("input.encoding", "utf-8");
        velocityEngine.setProperty("output.encoding", "utf-8");
        return velocityEngine;
    }

}
