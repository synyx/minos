package org.synyx.minos.skillz.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Utility class for handling {@link File}s.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public abstract class FileUtils {

    /**
     * Creates a temporary file in the given directory with the given byte array data.
     *
     * @param tempDirectory
     * @param data
     * @return
     * @throws IOException
     */
    public static File createTempFile(File tempDirectory, byte[] data) throws IOException {

        FileOutputStream fileOutputStream = null;
        File tempFile = null;

        try {
            tempFile = File.createTempFile("temp", ".tmp", tempDirectory);

            fileOutputStream = new FileOutputStream(tempFile);

            IOUtils.write(data, fileOutputStream);
        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteQuietly(tempFile);

            throw new IOException(e);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }

        return tempFile;
    }
}
