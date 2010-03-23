package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.domain.Persistable;


/**
 * Abstract base class to implement {@link PropertyEditor}s that can retrieve
 * {@link Persistable} from a {@link Long} id. Translation into text is done by
 * returning the
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractEntityPropertyEditor<T extends Persistable<Long>>
        extends PropertyEditorSupport {

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String idAsString) throws IllegalArgumentException {

        if (StringUtils.isBlank(idAsString)) {
            setValue(null);
            return;
        }

        Long id = Long.parseLong(idAsString);
        setValue(lookupForId(id));
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getAsText() {

        Persistable<Long> value = (Persistable<Long>) getValue();

        if (null == value) {
            return null;
        }

        return null == value.getId() ? null : value.getId().toString();
    }


    /**
     * Perform the actual lookup of the entity.
     * 
     * @param id
     * @return
     */
    protected abstract T lookupForId(Long id);
}