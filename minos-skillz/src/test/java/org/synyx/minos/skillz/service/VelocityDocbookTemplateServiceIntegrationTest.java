package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.Image;
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
                new VelocityDocbookTemplateServiceImpl(createVelocityEngine(),
                        "resume-template/resume.vm");
        resume =
                new Resume(new User("username", "foo@bar.de", "password"),
                        new MatrixTemplate("template"),
                        new ArrayList<Activity>());
    }


    @Test
    public void createsDocbookXml() throws Exception {

        String docbookXml =
                docbookTemplateService.createDocbookXml(resume, null);

        assertThat(docbookXml, containsString("<entry>foo@bar.de</entry>"));
    }


    @Test
    public void createsDocbookXmlWithReference() throws Exception {

        resume
                .add(new Activity(new Project("projectname"),
                        new DateMidnight()));

        String docbookXml =
                docbookTemplateService.createDocbookXml(resume, null);

        assertThat(docbookXml, containsString("<entry>projectname</entry>"));
    }


    @Test
    public void createsDocbookXmlWithSkill() throws Exception {

        resume.getSkillz().add(
                new Skill("skillname", new Category("categoryname")),
                new Level("levelname", 0));

        String docbookXml =
                docbookTemplateService.createDocbookXml(resume, null);

        assertThat(docbookXml, both(
                containsString("<entry>categoryname</entry>")).and(
                containsString("<listitem>skillname (levelname)</listitem>")));
    }


    @Test
    public void createsDocbookXmlWithPhoto() throws Exception {

        Image image = mock(Image.class);
        when(image.getFormatName()).thenReturn("png");
        resume.setPhoto(image);

        String docbookXml =
                docbookTemplateService.createDocbookXml(resume, "foobar.png");

        assertThat(
                docbookXml,
                containsString("<inlinegraphic fileref=\"foobar.png\" format=\"png\" />"));
    }


    private VelocityEngine createVelocityEngine() {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine
                .setProperty("class.resource.loader.class",
                        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("class.resource.loader.cache", "false");
        return velocityEngine;
    }

}
