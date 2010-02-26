package org.synyx.minos.skillz.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;


/**
 * Decorator class for creating {@link ZipOutputStream}s.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class Zipper {

    private final ZipOutputStream zipOutputStream;
    private String baseRelativePath;


    /**
     * Creates a {@link Zipper} instance with a {@link OutputStream}
     * 
     * @param outputStream {@link OutputStream}, must be not <code>null</code>
     */
    public Zipper(OutputStream outputStream) {

        Assert.notNull(outputStream);
        zipOutputStream = new ZipOutputStream(outputStream);
    }


    /**
     * Creates a {@link Zipper} instance with a {@link OutputStream} and an
     * optional base path.
     * 
     * @param outputStream {@link OutputStream}, must be not <code>null</code>
     * @param baseRelativePath Base path {@link String}, can be
     *            <code>null</code>
     */
    public Zipper(OutputStream outputStream, String baseRelativePath) {

        this(outputStream);
        this.baseRelativePath =
                StringUtils.removeStart(FilenameUtils.normalize(StringUtils
                        .defaultString(baseRelativePath)
                        + File.separator), "/");
    }


    /**
     * Adds a given class path resource directory to the {@link ZipOutputStream}
     * .
     * 
     * @param classpathResourcePath
     * @throws IOException
     */
    public void writeClasspathResource(String classpathResourcePath)
            throws IOException {

        Assert.notNull(classpathResourcePath);
        ResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        for (Resource resource : resolver.getResources("classpath*:"
                + FilenameUtils.normalize(classpathResourcePath
                        + File.separator) + "**")) {
            String relativePath =
                    getRelativeClasspathResourcePath(resource,
                            classpathResourcePath);

            if (!relativePath.endsWith(File.separator)) {
                writeEntry(resource.getInputStream(), relativePath);
            }
        }
    }


    /**
     * Utility method for getting the relative resources path.
     * 
     * @param resource
     * @param classpathResourcePath
     * @return
     * @throws IOException
     */
    private String getRelativeClasspathResourcePath(Resource resource,
            String classpathResourcePath) throws IOException {

        return StringUtils
                .removeStart(resource.getURL().getFile(),
                        new ClassPathResource(classpathResourcePath).getURL()
                                .getFile());
    }


    /**
     * Writes a {@link InputStream} file entry (consisting of a content
     * {@link InputStream} and a file name {@link String}) to a given
     * {@link ZipOutputStream}.
     * 
     * @param content
     * @param fileName
     * @throws IOException
     */
    public void writeEntry(InputStream content, String fileName)
            throws IOException {

        putNextEntry(fileName);
        IOUtils.copy(content, zipOutputStream);
    }


    /**
     * Writes a byte array entry (consisting of a content byte array and a file
     * name {@link String}) to a given {@link ZipOutputStream}.
     * 
     * @param content
     * @param fileName
     * @throws IOException
     */
    public void writeEntry(byte[] content, String fileName) throws IOException {

        putNextEntry(fileName);
        IOUtils.write(content, zipOutputStream);
    }


    /**
     * Writes a {@link String} entry (consisting of a content {@link String} and
     * a file name {@link String}) to a given {@link ZipOutputStream}.
     * 
     * @param content
     * @param fileName
     * @throws IOException
     */
    public void writeEntry(String content, String fileName) throws IOException {

        putNextEntry(fileName);
        IOUtils.write(content, zipOutputStream);
    }


    /**
     * Puts the {@link ZipOutputStream} to the next entry with the given file
     * path which will be append to the base path.
     * 
     * @param filePath
     * @throws IOException
     * @see ZipOutputStream#putNextEntry(ZipEntry)
     */
    private void putNextEntry(String filePath) throws IOException {

        zipOutputStream.putNextEntry(new ZipEntry(FilenameUtils
                .normalize(baseRelativePath + filePath)));
    }


    /**
     * Closes the {@link ZipOutputStream} quietly.
     * 
     * @see ZipOutputStream#close()
     */
    public void close() {

        IOUtils.closeQuietly(zipOutputStream);
    }

}
