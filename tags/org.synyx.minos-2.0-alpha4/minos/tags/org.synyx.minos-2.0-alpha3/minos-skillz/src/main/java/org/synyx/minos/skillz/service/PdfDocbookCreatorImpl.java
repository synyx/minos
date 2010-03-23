package org.synyx.minos.skillz.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.synyx.minos.core.domain.Image;
import org.synyx.minos.skillz.domain.Resume;

import com.icl.saxon.TransformerFactoryImpl;


/**
 * Implementation of {@link PdfDocbookCreator}. Uses Saxon's implementation of
 * {@link TransformerFactory}, as the default JDK {@link TransformerFactory}
 * doesn't work.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class PdfDocbookCreatorImpl implements PdfDocbookCreator {

    private final TransformerFactory transformerFactory;
    private final DocbookTemplateService docbookTemplateService;
    private final Resource defaultXsltResource;


    public PdfDocbookCreatorImpl(DocbookTemplateService docbookTemplateService,
            Resource defaultXsltResource) {

        System.setProperty("javax.xml.transform.TransformerFactory",
                "com.icl.saxon.TransformerFactoryImpl");
        this.transformerFactory = TransformerFactory.newInstance();
        this.docbookTemplateService = docbookTemplateService;
        this.defaultXsltResource = defaultXsltResource;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.PdfDocbookCreator#streamPdf(java.lang.
     * String, java.io.File, java.io.OutputStream)
     */
    @Override
    public void streamPdf(Resume resume, OutputStream outputStream)
            throws DocbookCreationException {

        File tmpPhotoFile = null;
        String tmpPhotoFileName = null;
        if (resume.getPhoto() != null) {
            tmpPhotoFile = createTmpPhotoFile(resume.getPhoto());
            tmpPhotoFileName = tmpPhotoFile.getAbsolutePath();
        }

        String docbookXml =
                docbookTemplateService.createDocbookXml(resume,
                        tmpPhotoFileName);
        streamPdf(docbookXml, null, outputStream);

        if (tmpPhotoFile != null) {
            tmpPhotoFile.delete();
        }
    }


    /**
     * Creates a photo file in the system's tmp directory.
     * 
     * @param image
     * @return
     * @throws DocbookCreationException
     */
    private File createTmpPhotoFile(Image image)
            throws DocbookCreationException {

        File tmpPhotoFile = null;
        try {
            tmpPhotoFile = File.createTempFile("photo", null);
            IOUtils.write(image.getOriginalImage(), new FileOutputStream(
                    tmpPhotoFile));
        } catch (Exception e) {
            throw new DocbookCreationException(
                    "Failed to create temporary photo file!", e);
        }
        return tmpPhotoFile;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.service.PdfDocbookCreator#streamPdf(org.synyx.
     * minos.skillz.domain.Resume, java.io.OutputStream)
     */
    @Override
    public void streamPdf(String xmlString, File xsltFile,
            OutputStream outputStream) throws DocbookCreationException {

        Assert.notNull(xmlString);
        Assert.notNull(outputStream);

        try {
            Fop fop = createFop(outputStream);

            Transformer transformer = createTransformer(xsltFile);
            transformer.setParameter("versionParam", "2.0");

            Source source = new StreamSource(new StringReader(xmlString));

            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(source, result);
        } catch (Exception e) {
            throw new DocbookCreationException(
                    "Failed to apply FOP XSLT transformation!", e);
        }
    }


    /**
     * Creates a {@link Fop} instance. Uses the Saxon
     * {@link TransformerFactoryImpl}.
     * 
     * @param outputStream
     * @return
     * @throws FOPException
     */
    private Fop createFop(OutputStream outputStream) throws FOPException {

        FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        return fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
                outputStream);
    }


    /**
     * Creates a {@link Transformer} instance with the given XSLT file or a
     * default XSLT file if <code>null</code> was given.
     * 
     * @param xsltFile
     * @return
     * @throws TransformerConfigurationException
     * @throws IOException
     */
    private Transformer createTransformer(File xsltFile)
            throws TransformerConfigurationException, IOException {

        if (xsltFile == null) {
            if (defaultXsltResource == null) {
                throw new IllegalArgumentException(
                        "Missing 'defaultXsltResource' property!");
            } else {
                return transformerFactory.newTransformer(new StreamSource(
                        defaultXsltResource.getFile()));
            }
        } else {
            return transformerFactory
                    .newTransformer(new StreamSource(xsltFile));
        }
    }

}
