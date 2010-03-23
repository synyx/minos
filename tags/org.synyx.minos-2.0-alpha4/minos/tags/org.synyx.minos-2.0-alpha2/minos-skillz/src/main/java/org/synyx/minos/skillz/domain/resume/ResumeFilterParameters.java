package org.synyx.minos.skillz.domain.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.synyx.minos.util.Assert;


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
            Map<String, String> plainParameters,
            ConversionService conversionService) {

        Map<String, Object> result = new HashMap<String, Object>();

        for (ResumeFilterParameter parameter : parameters) {

            String key = parameter.getName();
            String stringValue = plainParameters.get(key);

            Assert.notNull(stringValue);
            result.put(key, conversionService.convert(stringValue, parameter
                    .getType()));
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
         * Adds a paramter with the given name and type.
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
         * Returns the {@link ResumeFilterParameters} instance created.
         * 
         * @return
         */
        public ResumeFilterParameters build() {

            return this.instance;
        }
    }
}
