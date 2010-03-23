package org.synyx.minos.skillz.service;

import org.synyx.minos.skillz.domain.Resume;


/**
 * Interface for creating Docbook XML based on a template.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface DocbookTemplateService {

    /**
     * Creates a Docbook XML from a {@link Resume}.
     * 
     * @param resume The resume to fill the template
     * @param photoFilename The photoFilename that is used in the template
     * @return The Docbook XML as {@link String}
     * @throws DocbookCreationException
     */
    String createDocbookXml(Resume resume, String photoFilename)
            throws DocbookCreationException;

}
