package org.synyx.minos.skillz.service;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Velocity implementation of {@link DocbookTemplateService}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class VelocityDocbookTemplateServiceImpl implements
        DocbookTemplateService {

    private final VelocityEngine velocityEngine;
    private final String templateFile;


    public VelocityDocbookTemplateServiceImpl(VelocityEngine velocityEngine,
            String templateFile) {

        this.velocityEngine = velocityEngine;
        this.templateFile = templateFile;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.DocbookTemplateService#createDocbookXml
     * (org.synyx.minos.skillz.domain.Resume)
     */
    @Override
    public String createDocbookXml(Resume resume, String photoFilename)
            throws DocbookCreationException {

        VelocityContext context = new VelocityContext();
        context.put("resume", resume);
        if (resume.getPhoto() != null) {
            context.put("photoFile", photoFilename);
        }

        StringWriter stringWriter = new StringWriter();
        try {
            Template template = velocityEngine.getTemplate(templateFile);
            template.merge(context, stringWriter);
        } catch (Exception e) {
            throw new DocbookCreationException("Failed to merge template!", e);
        }

        return stringWriter.toString();
    }

}
