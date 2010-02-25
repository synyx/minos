package org.synyx.minos.skillz.service;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.synyx.minos.skillz.domain.Resume;


/**
 * Interface for creating PDFs/Maven ZIPs from a Docbook XML file.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface DocbookCreator {

    /**
     * Creates a PDF {@link OutputStream} from a Docbook XML {@link String} and
     * XSLT/XSL-FO {@link File}.
     * 
     * @param xmlString
     * @param xsltFile
     * @param outputStream
     * @throws FOPException
     * @throws TransformerException
     */
    void streamPdf(String xmlString, File xsltFile, OutputStream outputStream)
            throws FOPException, TransformerException;


    /**
     * Creates a PDF {@link OutputStream} from a {@link Resume}.
     * 
     * @param resume
     * @param outputStream
     * @throws Exception
     */
    void streamPdf(Resume resume, OutputStream outputStream) throws Exception;


    /**
     * Creates a ZIP {@link OutputStream} from a {@link Resume} instance that
     * contains a Maven Docbook project with to resume as Docbook XML and the
     * default Docbook XSLT/XSL-FO.
     * 
     * @param resume
     * @param outputStream
     */
    void streamMavenZip(Resume resume, OutputStream outputStream)
            throws Exception;

}
