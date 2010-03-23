package org.synyx.minos.skillz.service;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import org.synyx.minos.skillz.domain.Resume;


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
     * @see org.synyx.minos.skillz.service.ResumeZipCreator#streamZip(
     * org.synyx.minos.skillz.domain.Resume, java.io.OutputStream)
     */
    @Override
    public void streamZip(Resume resume, OutputStream outputStream)
            throws ZipCreationException {

        Zipper zipper =
                new Zipper(new BufferedOutputStream(outputStream), "/resume");

        try {
            zipper.writeClasspathResource("/resume-template/maven");

            if (resume.getPhoto() != null) {
                zipper.writeEntry(resume.getPhoto().getOriginalImage(),
                        "src/docbkx/media/photo.png");
            }

            zipper.writeEntry(docbookTemplateService.createDocbookXml(resume,
                    "media/photo.png"), "src/docbkx/resume.xml");
        } catch (Exception e) {
            throw new ZipCreationException("Failed to create Resume ZIP!", e);
        } finally {
            zipper.close();
        }
    }

}
