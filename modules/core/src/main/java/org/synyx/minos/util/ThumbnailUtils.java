package org.synyx.minos.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Utility class for creating thumbnails.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public final class ThumbnailUtils {

    private ThumbnailUtils() {
    }

    /**
     * Scales a {@link BufferedImage} to the given width.
     */
    public static BufferedImage scale(BufferedImage image, double width) {

        double scale = width / image.getWidth();

        AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp op = new AffineTransformOp(transform, null);

        return op.filter(image, null);
    }

    /**
     * @see #scale(BufferedImage, double)
     */
    public static byte[] scale(byte[] image, double width, String formatName) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream(image);

        try {
            ImageIO.write(scale(ImageIO.read(inputStream), width), formatName, outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
