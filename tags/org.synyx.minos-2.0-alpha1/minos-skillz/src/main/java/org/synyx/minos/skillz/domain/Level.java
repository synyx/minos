package org.synyx.minos.skillz.domain;

import javax.persistence.Entity;

import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Level extends AbstractAuditable<User, Long> implements
        Comparable<Level> {

    private static final long serialVersionUID = 123978123526845L;

    private boolean isDefault;
    private Integer ordinal;
    private String name;


    protected Level() {

    }


    public Level(String name, Integer ordinal) {

        this.name = name;
        this.ordinal = ordinal;
    }


    /**
     * @return the name
     */
    public String getName() {

        return name;
    }


    /**
     * @return the ordinal
     */
    public Integer getOrdinal() {

        return ordinal;
    }


    /**
     * @return the isDefault
     */
    public boolean isDefault() {

        return isDefault;
    }


    /**
     * @param isDefault the isDefault to set
     */
    public void setDefault(boolean isDefault) {

        this.isDefault = isDefault;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Level o) {

        return this.ordinal - o.ordinal;
    }
}