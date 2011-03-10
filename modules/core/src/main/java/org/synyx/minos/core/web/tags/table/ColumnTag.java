package org.synyx.minos.core.web.tags.table;

import org.displaytag.tags.el.ELColumnTag;

import org.synyx.minos.core.web.MinosColumnDecorator;


/**
 * Column tag customization tweaking certain defaults. Using this tag, columns are sortable by default and a
 * {@link MinosColumnDecorator} is registered.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ColumnTag extends ELColumnTag {

    private static final long serialVersionUID = 8993596377337641729L;

    /**
     * Creates a new {@link ColumnTag}.
     */
    public ColumnTag() {

        setDecorator(MinosColumnDecorator.class.getName());
        setSortable(true);
    }

    @Override
    public void setProperty(String value) {

        setSortName(value);
        setSortProperty(value);
        super.setProperty(value);
    }
}
