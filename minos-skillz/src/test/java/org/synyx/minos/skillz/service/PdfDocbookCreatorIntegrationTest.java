package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;


/**
 * Test for {@code PdfDocbookCreatorImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class PdfDocbookCreatorIntegrationTest extends
        AbstractDocbookCreatorIntegrationTest {

    private PdfDocbookCreatorImpl docbookCreator;
    private File pdf;
    private OutputStream outputStream;


    @Override
    @Before
    public void setUp() throws Exception {

        super.setUp();
        docbookCreator =
                new PdfDocbookCreatorImpl(
                        docbookTemplateService,
                        new ClassPathResource(
                                "/resume-template/maven/src/docbkx-stylesheet/fo/docbook.xsl"));
        pdf = File.createTempFile("test", "pdf");
        outputStream = new FileOutputStream(pdf);
    }


    @After
    public void tearDown() throws Exception {

        outputStream.close();
        pdf.delete();
    }


    @Test
    public void createsPdfWithOwnXSL() throws Exception {

        long lastModified = pdf.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamPdf(IOUtils.toString(new ClassPathResource(
                "/projectteam.xml").getInputStream()), new ClassPathResource(
                "/projectteam2fo.xsl").getFile(), outputStream);
        outputStream.flush();

        assertTrue(pdf.lastModified() > lastModified);

    }


    @Test
    public void createPdf() throws Exception {

        long lastModified = pdf.lastModified();

        Thread.sleep(1500);
        docbookCreator.streamPdf(resume, outputStream);
        outputStream.flush();

        assertTrue(pdf.lastModified() > lastModified);
    }

}
