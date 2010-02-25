package org.synyx.minos.skillz.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.synyx.minos.core.domain.Image;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Implementation of a basic PDF/Maven ZIP creator.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class DocbookCreatorImpl implements DocbookCreator {

    @Override
    public void streamPdf(Resume resume, OutputStream outputStream)
            throws Exception {

        File tmpPhotoFile = null;
        String tmpPhotoFileName = null;
        if (resume.getPhoto() != null) {
            tmpPhotoFile = getTmpPhotoFile(resume.getPhoto());
            tmpPhotoFileName = tmpPhotoFile.getAbsolutePath();
        }
        String docbookXml = createDocbookXml(resume, tmpPhotoFileName);
        streamPdf(docbookXml, null, outputStream);
        if (tmpPhotoFile != null) {
            tmpPhotoFile.delete();
        }
    }


    /**
     * Creates a Docbook XML from a velocity template.
     * 
     * @param resume The resume to fill the template
     * @param photoFilename The photoFilename that is used in the template
     * @return The Docbook XML as {@link String}
     * @throws Exception
     */
    private String createDocbookXml(Resume resume, String photoFilename)
            throws Exception {

        VelocityContext context = new VelocityContext();
        context.put("resume", resume);
        if (resume.getPhoto() != null) {
            context.put("photoFile", photoFilename);
        }
        Template template =
                getVelocityEngine().getTemplate("resume-template/resume.vm");
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }


    private VelocityEngine getVelocityEngine() {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine
                .setProperty("class.resource.loader.class",
                        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("class.resource.loader.cache", "false");
        return velocityEngine;
    }


    private File getTmpPhotoFile(Image image) throws IOException {

        File tmpPhotoFile = File.createTempFile("photo", null);
        streamPhoto(image, new FileOutputStream(tmpPhotoFile));
        return tmpPhotoFile;
    }


    private void streamPhoto(Image image, OutputStream outputStream)
            throws IOException {

        try {
            outputStream.write(image.getOriginalImage());
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }


    public void streamPdf(String xmlString, File xsltFile,
            OutputStream outputStream) throws FOPException,
            TransformerException {

        System.setProperty("javax.xml.transform.TransformerFactory",
                "com.icl.saxon.TransformerFactoryImpl");
        FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        Fop fop =
                fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
                        outputStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        if (xsltFile == null) {
            transformer =
                    factory.newTransformer(new StreamSource(new File(getClass()
                            .getResource("/resume-template/docbook.xsl")
                            .getFile())));
        } else {
            transformer = factory.newTransformer(new StreamSource(xsltFile));
        }

        transformer.setParameter("versionParam", "2.0");

        Source source = new StreamSource(new StringReader(xmlString));

        Result result = new SAXResult(fop.getDefaultHandler());

        transformer.transform(source, result);
    }


    @Override
    public void streamMavenZip(Resume resume, OutputStream outputStream)
            throws Exception {

        ZipOutputStream zipOutputStream =
                new ZipOutputStream(new BufferedOutputStream(outputStream));
        PrintWriter printWriter = null;

        try {
            writeZipEntry("/resume-template/pom.xml", "resume/pom.xml",
                    zipOutputStream);

            if (resume.getPhoto() != null) {
                zipOutputStream.putNextEntry(new ZipEntry(
                        "resume/src/docbkx/media/photo.png"));
                streamPhoto(resume.getPhoto(), zipOutputStream);
            }

            writeZipEntry("/resume-template/docbook.xsl",
                    "resume/src/docbkx-stylesheet/fo/docbook.xsl",
                    zipOutputStream);

            zipOutputStream.putNextEntry(new ZipEntry(
                    "resume/src/docbkx/resume.xml"));
            printWriter = new PrintWriter(zipOutputStream);
            printWriter.write(createDocbookXml(resume, "media/photo.png"));
        } finally {
            IOUtils.closeQuietly(printWriter);
            IOUtils.closeQuietly(zipOutputStream);
        }

    }


    private void writeZipEntry(String resourceName, String fileName,
            ZipOutputStream zipOutputStream) throws IOException {

        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        writeFile(resourceName, zipOutputStream);
    }


    private void writeFile(String resourceName, ZipOutputStream zipOutputStream)
            throws IOException {

        InputStream inputStream = getClass().getResourceAsStream(resourceName);
        byte[] buffer = new byte[16384];
        int bytesRead = 0;
        try {
            while ((bytesRead = inputStream.read()) != -1) {
                zipOutputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        zipOutputStream.flush();
    }

}
