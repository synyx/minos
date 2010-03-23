package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.test.util.TestUtils;


/**
 * Test for {@code DocbookCreatorImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class DocbookCreatorIntegrationTest {

    private DocbookCreator docbookCreator;
    private Resume resume;


    @Before
    public void setUp() {

        docbookCreator = new DocbookCreatorImpl();

        User user = new User("username", "test@test.com", "password");
        resume =
                new Resume(user, new MatrixTemplate("name"),
                        new ArrayList<Activity>());
        Category category = new Category("categoryname");
        Skill skill = new Skill("skillname", category);
        Level level = new Level("levelname", 0);
        resume.getSkillz().add(skill, level);
        resume.setTitle("Dr.");
    }


    @Test
    public void createsPdfWithOwnXSL() throws Exception {

        File pdf = File.createTempFile("test", "pdf");
        OutputStream outputStream = new FileOutputStream(pdf);
        long lastModified = pdf.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamPdf(TestUtils
                .loadResourceAsString("/projectteam.xml"), TestUtils
                .loadResourceAsFile("/projectteam2fo.xsl"), outputStream);
        outputStream.close();

        assertTrue(pdf.lastModified() > lastModified);
    }


    @Test
    public void createsMavenZip() throws Exception {

        File zip = File.createTempFile("test", "zip");
        OutputStream outputStream = new FileOutputStream(zip);
        long lastModified = zip.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamMavenZip(resume, outputStream);
        outputStream.close();

        assertTrue(zip.lastModified() > lastModified);
        zip.delete();
    }


    @Test
    @Ignore
    public void createPdf() throws Exception {

        File pdf = File.createTempFile("test", "pdf");
        OutputStream outputStream = new FileOutputStream(pdf);
        long lastModified = pdf.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamPdf(resume, outputStream);
        outputStream.close();

        assertTrue(pdf.lastModified() > lastModified);
        pdf.delete();
    }

}
