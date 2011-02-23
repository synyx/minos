package org.synyx.minos.core.domain;

import org.synyx.minos.util.ThumbnailUtils;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;


/**
 * Value object class for images.
 *
 * @author  Markus Knittig - knittig@synyx.de
 */
@Embeddable
public class Image {

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] originalImage;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] thumbnail;

    private String formatName;

    protected Image() {
    }


    /**
     * Creates a new {@link Image} from the given attributes.
     *
     * @param  originalImage  the original image as raw data
     * @param  width  the thumbnail width
     * @param  formatName  format of the supplied image data
     */
    public Image(byte[] originalImage, double width, String formatName) {

        this();
        this.originalImage = originalImage;
        this.thumbnail = ThumbnailUtils.scale(originalImage, width, formatName);
        this.formatName = formatName;
    }

    public byte[] getOriginalImage() {

        return originalImage;
    }


    public byte[] getThumbnail() {

        return thumbnail;
    }


    public String getFormatName() {

        return formatName;
    }
}
