package org.synyx.minos.skillz.service;

import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.Resume;

import java.io.File;
import java.io.OutputStream;

import java.util.List;


/**
 * Interface for creating PDFs from a Docbook XML file.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public interface PdfDocbookCreator {

    /**
     * Creates a temporary (aka 'with a unique file name') PDF {@link File} from a {@link Resume} instance.
     *
     * @param tempDirectory
     * @param resume
     * @param levels
     * @return
     * @throws DocbookCreationException
     */
    File createTempPdfFile(File tempDirectory, Resume resume, List<Level> levels) throws DocbookCreationException;


    /**
     * Creates a PDF {@link OutputStream} from a Docbook XML {@link String} and XSLT/XSL-FO {@link File}.
     *
     * @param xmlString
     * @param xsltFile
     * @param outputStream
     * @throws DocbookCreationException
     */
    void streamPdf(String xmlString, File xsltFile, OutputStream outputStream) throws DocbookCreationException;


    /**
     * Creates a PDF {@link OutputStream} from a {@link Resume}.
     *
     * @param resume
     * @param levels
     * @param outputStream
     * @throws DocbookCreationException
     */
    void streamPdf(Resume resume, List<Level> levels, OutputStream outputStream) throws DocbookCreationException;
}
