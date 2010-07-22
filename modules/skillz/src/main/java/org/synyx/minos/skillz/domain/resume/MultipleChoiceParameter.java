package org.synyx.minos.skillz.domain.resume;

/**
 * Class for describing {@link ResumeFilter} parameters with multiple choices.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class MultipleChoiceParameter extends AbstractChoiceParameter {

    /**
     * @see AbstractChoiceParameter#AbstractChoiceParameter(String, Class, ReferenceDataContainer, String)
     * @param name
     * @param type
     * @param referenceDataContainer
     * @param messageKey
     */
    public MultipleChoiceParameter(String name, Class<?> type, ReferenceDataContainer referenceDataContainer,
            String messageKey) {

        super(name, type, referenceDataContainer, messageKey);
    }

}
