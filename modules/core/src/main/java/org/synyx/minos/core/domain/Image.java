package org.synyx.minos.core.domain;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.synyx.minos.util.ThumbnailUtils;


/**
 * Value object class for images.
 * 
 * @author Markus Knittig - knittig@synyx.de
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
     * @param originalImage The original image
     * @param width The width for scaling the original image to a thumbnail
     */
    public Image(byte[] originalImage, double width, String formatName) {

        this();
        this.originalImage = originalImage;
        this.thumbnail = ThumbnailUtils.scale(originalImage, width, formatName);
        this.formatName = formatName;
    }


    /**
     * @return the originalImage
     */
    public byte[] getOriginalImage() {

        return originalImage;
    }


    /**
     * @return the thumbnail
     */
    public byte[] getThumbnail() {

        return thumbnail;
    }


    /**
     * @return the formatName
     */
    public String getFormatName() {

        return formatName;
    }

}
