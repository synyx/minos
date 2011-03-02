package org.synyx.minos.core.web.tags.table;

import org.displaytag.exception.InvalidTagAttributeValueException;

import org.displaytag.tags.el.ELTableTag;


/**
 * Custom table tag implementation tweaking tag defaults a little. Sorting is handled externally by default. A
 * {@link SpringDecoratorFactory} is registered, that allows to lookup decorators via Spring bean names using the
 * {@code decorator} attribute on {@code table} and {@code column} tags. Furthermore we configure we configure
 * displaytag library to use Spring I18N adapters.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class TableTag extends ELTableTag {

    private static final long serialVersionUID = -6332666894708234895L;

    /**
     * @throws InvalidTagAttributeValueException
     */
    public TableTag() throws InvalidTagAttributeValueException {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.displaytag.tags.TableTag#setupViewableData()
     */
    @Override
    protected void setupViewableData() {

        super.setupViewableData();
    }
}
