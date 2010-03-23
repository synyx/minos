package org.synyx.minos.skillz.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.Fop;
import org.xml.sax.SAXException;


/**
 * Factory service for FOP XSLT transformation.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
interface FopXsltService {

    /**
     * Creates a {@link Fop} instance.
     * 
     * @param outputStream
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ConfigurationException
     */
    Fop createFop(OutputStream outputStream) throws SAXException, IOException,
            ConfigurationException;


    /**
     * Creates a {@link Transformer} instance with the given XSLT file or a
     * default XSLT file if <code>null</code> was given.
     * 
     * @param xsltFile
     * @return
     * @throws TransformerConfigurationException
     * @throws IOException
     */
    Transformer createTransformer(File xsltFile)
            throws TransformerConfigurationException, IOException;

}
