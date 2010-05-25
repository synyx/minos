package org.synyx.minos.support.remoting;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


/**
 * Custom extension of {@link MappingJacksonJsonView} to extract a single model
 * object from the model map prior to unmarshalling.
 * 
 * @author Oliver Gierke
 */
public class MinosMappingJacksonJsonView extends MappingJacksonJsonView {

    /**
     * Custom {@link #filterModel(Map)} implementation that unwraps a single map
     * entry, so that it won't be marshalled into a wrapping map but standalone.
     * 
     * @param model
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Object filterModel(Map<String, Object> model) {

        Object result = super.filterModel(model);

        if (!(result instanceof Map)) {
            return result;
        }

        Map<String, Object> map = (Map<String, Object>) result;

        // Only one entry?
        if (map.entrySet().size() == 1) {
            return map.entrySet().iterator().next().getValue();
        }

        return result;
    }
}
