package org.synyx.minos.core.domain;

/**
 * Abstract base class for entities centered around a name element. Primarily use of this class is to prevent duplicate
 * {@link #equals(Object)} and {@link #hashCode()} of simple value object implementations abstracting a name.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractNamedEntity {

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbstractNamedEntity)) {
            return false;
        }

        AbstractNamedEntity that = (AbstractNamedEntity) obj;

        return null == getValue() ? false : getValue().equals(that.getValue());
    }


    @Override
    public int hashCode() {

        return null == getValue() ? 0 : getValue().hashCode();
    }


    protected abstract String getValue();
}
