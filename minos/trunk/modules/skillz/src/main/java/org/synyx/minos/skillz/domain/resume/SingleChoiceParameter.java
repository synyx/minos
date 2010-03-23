package org.synyx.minos.skillz.domain.resume;

/**
 * Class for describing {@link ResumeFilter} parameters with a single choice.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class SingleChoiceParameter extends AbstractChoiceParameter {

    /**
     * @see AbstractChoiceParameter#AbstractChoiceParameter(String, Class,
     *      ReferenceDataContainer, String)
     * @param name
     * @param type
     * @param referenceDataContainer
     * @param messageKey
     */
    public SingleChoiceParameter(String name, Class<?> type,
            ReferenceDataContainer referenceDataContainer, String messageKey) {

        super(name, type, referenceDataContainer, messageKey);
    }

}
