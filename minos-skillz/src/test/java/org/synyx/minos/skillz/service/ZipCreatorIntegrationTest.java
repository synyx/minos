package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for {@code ZipCreatorImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class ZipCreatorIntegrationTest extends
        AbstractDocbookCreatorIntegrationTest {

    private ResumeZipCreator docbookCreator;

    private File zip;
    private OutputStream outputStream;


    @Override
    @Before
    public void setUp() throws Exception {

        super.setUp();
        docbookCreator = new ZipDocbookCreatorImpl(docbookTemplateService);
        zip = File.createTempFile("test", "zip");
        outputStream = new FileOutputStream(zip);
    }


    @After
    public void tearDown() throws Exception {

        outputStream.close();
        zip.delete();
    }


    @Test
    public void createsMavenZip() throws Exception {

        long lastModified = zip.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamZip(resume, outputStream);
        outputStream.flush();

        assertTrue(zip.lastModified() > lastModified);
    }

}
