package org.synyx.minos.skillz.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;


/**
 * A {@link Skill} captures a name for an activity, technology, habit sombody
 * can be judged about. {@link Skill}s have to be carried in {@link Category}s
 * to group them together.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Skill extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = 2208910823506762294L;

    private String name;

    @ManyToOne(optional = false)
    private Category category;


    protected Skill() {

    }


    /**
     * Creates a new {@link Skill} and assigns it to the given category;
     * 
     * @param string
     */
    public Skill(String name, Category category) {

        Assert.hasText(name, "Name must not be empty!");
        Assert.notNull(category);

        this.name = name;
        this.category = category;
        this.category.add(this);
    }


    /**
     * @return the name
     */
    public String getName() {

        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }


    /**
     * Assignes the skill to the given {@link Category}. Removes it from the old
     * {@link Category}.
     * 
     * @param category
     */
    public Skill setCategory(Category category) {

        // Remove skill from old category
        if (null != this.category) {
            this.category.getSkillz().remove(this);
        }

        this.category = category;

        if (!this.category.has(this)) {
            this.category.getSkillz().add(this);
        }

        return this;
    }


    /**
     * Returns the {@link Category} the skill is assigned to.
     * 
     * @return the category
     */
    public Category getCategory() {

        return category;
    }


    /**
     * Returns whether the {@link Skill} is assigned to the given
     * {@link Category}.
     * 
     * @param category
     * @return
     */
    public boolean has(Category category) {

        return this.category.equals(category);
    }


    @PreRemove
    public void preDelete() {

        this.category.remove(this);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hades.domain.support.AbstractPersistable#toString()
     */
    @Override
    public String toString() {

        return String.format("%s - %s", getName(), getCategory().getName());
    }
}
