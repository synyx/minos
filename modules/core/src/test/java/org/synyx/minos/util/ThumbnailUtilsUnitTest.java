package org.synyx.minos.util;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.Test;


/**
 * Unit test for {@link ThumbnailUtils}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class ThumbnailUtilsUnitTest {

    @Test
    public void scaleImage() throws Exception {

        BufferedImage image =
                ImageIO.read(getClass().getResourceAsStream("logo.png"));
        BufferedImage scaledImage = ThumbnailUtils.scale(image, 200);

        assertEquals(200, scaledImage.getWidth());
    }
}
