package org.synyx.minos.skillz.service;

import java.io.File;
import java.io.OutputStream;

import org.synyx.minos.skillz.domain.Resume;


/**
 * Interface for creating PDFs from a Docbook XML file.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface PdfDocbookCreator {

    /**
     * Creates a PDF {@link OutputStream} from a Docbook XML {@link String} and
     * XSLT/XSL-FO {@link File}.
     * 
     * @param xmlString
     * @param xsltFile
     * @param outputStream
     * @throws DocbookCreationException
     */
    void streamPdf(String xmlString, File xsltFile, OutputStream outputStream)
            throws DocbookCreationException;


    /**
     * Creates a PDF {@link OutputStream} from a {@link Resume}.
     * 
     * @param resume
     * @param outputStream
     * @throws DocbookCreationException
     */
    void streamPdf(Resume resume, OutputStream outputStream)
            throws DocbookCreationException;

}
