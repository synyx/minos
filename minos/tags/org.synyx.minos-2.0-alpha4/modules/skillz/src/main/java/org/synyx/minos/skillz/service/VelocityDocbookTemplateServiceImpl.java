package org.synyx.minos.skillz.service;

import java.io.StringWriter;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.ConversionTool;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.synyx.minos.skillz.domain.Level;
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
    public String createDocbookXml(Resume resume, List<Level> levels,
            String photoFilename) throws DocbookCreationException {

        VelocityContext context = new VelocityContext();
        context.put("esc", new EscapeTool());
        context.put("convert", new ConversionTool());
        context.put("date", new DateTool());
        context.put("resume", resume);
        context.put("levels", levels);
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
