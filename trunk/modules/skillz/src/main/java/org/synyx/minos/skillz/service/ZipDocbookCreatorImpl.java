package org.synyx.minos.skillz.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.util.FileUtils;


/**
 * Implementation of {@link ResumeZipCreator}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class ZipDocbookCreatorImpl implements ResumeZipCreator {

    private final DocbookTemplateService docbookTemplateService;


    public ZipDocbookCreatorImpl(DocbookTemplateService docbookTemplateService) {

        this.docbookTemplateService = docbookTemplateService;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.service.ResumeZipCreator#streamZip( org.synyx.minos.skillz.domain.Resume,
     * java.io.OutputStream)
     */
    @Override
    public void streamZip(Resume resume, List<Level> levels, OutputStream outputStream) throws ZipCreationException {

        Zipper zipper = new Zipper(new BufferedOutputStream(outputStream), "/resume");

        try {
            zipper.writeClasspathResource("/resume-template/maven");

            if (resume.getPhoto() != null) {
                zipper.writeEntry(resume.getPhoto().getOriginalImage(), "src/docbkx/media/photo.png");
            }

            zipper.writeEntry(docbookTemplateService.createDocbookXml(resume, levels, "media/photo.png", Boolean.FALSE),
                    "src/docbkx/resume.xml");
        } catch (Exception e) {
            throw new ZipCreationException("Failed to create Resume ZIP!", e);
        } finally {
            zipper.close();
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.service.ResumeZipCreator#createTempZipFile(java .io.File,
     * org.synyx.minos.skillz.domain.Resume, java.util.List)
     */
    @Override
    public File createTempZipFile(File tempDirectory, Resume resume, List<Level> levels) throws ZipCreationException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        streamZip(resume, levels, outputStream);

        try {
            return FileUtils.createTempFile(tempDirectory, outputStream.toByteArray());
        } catch (IOException e) {
            throw new ZipCreationException("Failed to create temporary file!", e);
        }
    }

}
