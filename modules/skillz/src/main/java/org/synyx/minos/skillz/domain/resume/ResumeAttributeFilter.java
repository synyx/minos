package org.synyx.minos.skillz.domain.resume;

import org.synyx.minos.skillz.domain.Resume;


/**
 * Interface that describes a {@link Resume} attribute filter.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface ResumeAttributeFilter {

    /**
     * Message key for displaying the filter name on the page and identifying
     * the filter in the request.
     * 
     * @return
     */
    String getMessageKey();


    /**
     * Method that filters a {@link Resume} instance.
     * 
     * @param resume
     */
    void filter(Resume resume);

}
