package org.synyx.minos.skillz.domain.resume;

import java.util.Collection;

import org.springframework.util.Assert;


/**
 * Abstract class for describing {@link ResumeFilter} parameters with one ore
 * more choices.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public abstract class AbstractChoiceParameter extends ResumeFilterParameter {

    private final ReferenceDataContainer referenceDataContainer;


    /**
     * Constructor for {@link AbstractChoiceParameter} with the given name type,
     * and reference data container. The name will also be used as resource
     * bundle key for i18n lookup.
     * 
     * @see ResumeFilterParameter#ResumeFilterParameter(String, Class, String)
     * @param name
     * @param type
     * @param genericDao
     * @param messageKey
     */
    public AbstractChoiceParameter(String name, Class<?> type,
            ReferenceDataContainer referenceDataContainer, String messageKey) {

        super(name, type, messageKey);

        Assert.notNull(referenceDataContainer);
        this.referenceDataContainer = referenceDataContainer;
    }


    /**
     * Returns the given parameter choices as a {@link Collection}.
     * 
     * @see ReferenceDataContainer#getReferenceData()
     * @return
     */
    public Collection<?> getReferenceData() {

        return referenceDataContainer.getReferenceData();
    }

}
