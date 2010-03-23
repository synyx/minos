package org.synyx.minos.skillz.domain.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.synyx.hades.dao.GenericDao;


/**
 * Wrapper class to capture {@link ResumeFilterParameter}s and provide a single
 * point for type conversion.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Markus Knittig - knittig@synyx.de
 */
public class ResumeFilterParameters {

    private final List<ResumeFilterParameter> parameters;


    /**
     * Creates a new {@link ResumeFilterParameters} instance
     */
    private ResumeFilterParameters() {

        this.parameters = new ArrayList<ResumeFilterParameter>();
    }


    /**
     * Return a {@link List} of the raw {@link ResumeFilterParameter}s.
     * 
     * @return
     */
    public List<ResumeFilterParameter> getRawParameters() {

        return parameters;
    }


    /**
     * Converts the given plain parameters into a typed parameter map using the
     * given conversion service.
     * 
     * @param plainParameters
     * @param conversionService
     * @return
     */
    public Map<String, Object> getTypedParameters(
            Map<String, String[]> plainParameters,
            ConversionService conversionService) {

        Map<String, Object> result = new HashMap<String, Object>();

        for (ResumeFilterParameter parameter : parameters) {

            String key = parameter.getName();
            String[] stringValues = plainParameters.get(key);

            if (parameter instanceof MultipleChoiceParameter) {
                List<Object> list = new ArrayList<Object>();
                if (stringValues != null) {
                    for (String stringValue : stringValues) {
                        list.add(conversionService.convert(stringValue,
                                parameter.getType()));
                    }
                }
                result.put(key, list);
            } else {
                // XXX Workaround: Prevent String to String convention,
                // because it somehow calls the StringToPersistableConverter
                if (parameter.getType().equals(String.class)) {
                    result.put(key, stringValues[0]);
                } else {
                    result.put(key, conversionService.convert(stringValues[0],
                            parameter.getType()));
                }
            }
        }

        return result;
    }

    /**
     * Builder to create {@link ResumeFilterParameters} instances easily.
     * 
     * @author Oliver Gierke - gierke@synyx.de
     */
    public static class Builder {

        private final ResumeFilterParameters instance;


        /**
         * Creates a new {@link Builder} instance.
         */
        public Builder() {

            this.instance = new ResumeFilterParameters();
        }


        /**
         * Adds a parameter with the given name and type.
         * 
         * @see ResumeFilterParameter#ResumeFilterParameter(String, Class)
         * @param name
         * @param type
         * @return
         */
        public Builder add(String name, Class<?> type) {

            this.instance.parameters.add(new ResumeFilterParameter(name, type));
            return this;
        }


        /**
         * Adds a parameter with the given name, type and resource bundle key.
         * 
         * @see ResumeFilterParameter#ResumeFilterParameter(String, Class,
         *      String)
         * @param name
         * @param type
         * @param messageKey
         * @return
         */
        public Builder add(String name, Class<?> type, String messageKey) {

            this.instance.parameters.add(new ResumeFilterParameter(name, type,
                    messageKey));
            return this;
        }


        /**
         * Adds a single choice parameter with the given name, type and
         * reference data container.
         * 
         * @see SingleChoiceParameter#SingleChoiceParameter(String, Class,
         *      GenericDao)
         * @param name
         * @param type
         * @param referenceDataContainer
         * @return
         */
        public Builder addSingleChoice(String name, Class<?> type,
                ReferenceDataContainer referenceDataContainer) {

            this.instance.parameters.add(new SingleChoiceParameter(name, type,
                    referenceDataContainer, name));
            return this;
        }


        /**
         * Adds a single choice parameter with the given name, type, reference
         * data container and resource bundle key.
         * 
         * @see SingleChoiceParameter#SingleChoiceParameter(String, Class,
         *      GenericDao, String)
         * @param name
         * @param type
         * @param referenceDataContainer
         * @param messageKey
         * @return
         */
        public Builder addSingleChoice(String name, Class<?> type,
                ReferenceDataContainer referenceDataContainer, String messageKey) {

            this.instance.parameters.add(new SingleChoiceParameter(name, type,
                    referenceDataContainer, messageKey));
            return this;
        }


        /**
         * Adds a multiple choice parameter with the given name, type and
         * reference data container.
         * 
         * @see MultipleChoiceParameter#MultipleChoiceParameter(String, Class,
         *      GenericDao)
         * @param name
         * @param type
         * @param referenceDataContainer
         * @return
         */
        public Builder addMultipleChoice(String name, Class<?> type,
                ReferenceDataContainer referenceDataContainer) {

            this.instance.parameters.add(new MultipleChoiceParameter(name,
                    type, referenceDataContainer, name));
            return this;
        }


        /**
         * Adds a multiple choice parameter with the given name, type, reference
         * data container and resource bundle key.
         * 
         * @see MultipleChoiceParameter#MultipleChoiceParameter(String, Class,
         *      GenericDao, String)
         * @param name
         * @param type
         * @param referenceDataContainer
         * @param messageKey
         * @return
         */
        public Builder addMultipleChoice(String name, Class<?> type,
                ReferenceDataContainer referenceDataContainer, String messageKey) {

            this.instance.parameters.add(new MultipleChoiceParameter(name,
                    type, referenceDataContainer, messageKey));
            return this;
        }


        /**
         * Returns the {@link ResumeFilterParameters} instance created.
         * 
         * @return
         */
        public ResumeFilterParameters build() {

            return this.instance;
        }
    }
}
