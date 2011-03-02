package org.synyx.minos.skillz.domain;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;

import javax.persistence.Entity;


/**
 * Abstracts a responsibility one took in a {@link Project}. Used to specify what the subject did for that project in
 * particular.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Responsibility extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = 123834538123L;

    private String name;

    protected Responsibility() {
    }


    /**
     * Creates a new {@link Responsibility} with the given name.
     *
     * @param name
     */
    public Responsibility(String name) {

        Assert.hasText(name, "Name must not be empty!");

        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.hades.domain.support.AbstractPersistable#toString()
     */
    @Override
    public String toString() {

        return getName();
    }
}
