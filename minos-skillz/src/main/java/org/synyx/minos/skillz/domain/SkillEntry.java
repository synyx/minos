package org.synyx.minos.skillz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;



/**
 * A {@link SkillEntry} binds a {@link Level} instance to a {@link Skill}. This
 * expresses the level of ability somebody carries for the references skill.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class SkillEntry extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = 1064909794542423245L;

    @ManyToOne
    private Skill skill;

    @ManyToOne
    private Level level;

    @ManyToOne
    private SkillMatrix matrix;

    private boolean acknowledged;


    protected SkillEntry() {

    }


    /**
     * Creates a new {@link SkillEntry}.
     * 
     * @param skill
     * @param matrix
     * @param level
     */
    public SkillEntry(Skill skill, SkillMatrix matrix, Level level) {

        Assert.notNull(skill);
        Assert.notNull(matrix);

        this.skill = skill;
        this.level = level;
        this.matrix = matrix;
        this.acknowledged = false;
    }


    /**
     * Returns the name of the entry.
     * 
     * @return
     */
    public String getName() {

        return this.skill.getName();
    }


    /**
     * Returns the {@link Category} the entry is assigned to.
     * 
     * @return
     */
    public Category getCategory() {

        return this.skill.getCategory();
    }


    /**
     * Returns the {@link Level} that is assigned to the underlying
     * {@link Skill}. Might be {@code null} in cases the {@link SkillEntry} is
     * freshly created.
     * 
     * @return the level
     */
    public Level getLevel() {

        return level;
    }


    /**
     * Set the {@link Level} assigned to the underlying {@link Skill}.
     * 
     * @param level the level to set
     */
    public void setLevel(Level level) {

        // Drop acknowledgement if reset
        if (null == level) {
            this.acknowledged = false;
        }

        this.level = level;
    }


    /**
     * Sets a link to the containing {@link SkillMatrix}.
     * 
     * @param matrix the matrix to set
     */
    void setMatrix(SkillMatrix matrix) {

        this.matrix = matrix;
    }


    /**
     * Returns whether the entry belongs to the given {@link Category}.
     * 
     * @param category
     * @return
     */
    public boolean has(Category category) {

        if (null == category) {
            return false;
        }

        return category.has(skill);
    }


    /**
     * Returns the {@link Skill} the {@link SkillEntry} is assigned to.
     * 
     * @return
     */
    Skill getSkill() {

        return this.skill;
    }


    /**
     * Returns whether the {@link SkillEntry} is assigned to the given
     * {@link Skill}.
     * 
     * @param skill
     * @return
     */
    public boolean has(Skill skill) {

        return this.skill.equals(skill);
    }


    /**
     * Returns the index of the entry inside the {@link SkillMatrix}.
     * 
     * @return
     */
    public int getIndex() {

        return matrix.getIndex(this);
    }


    /**
     * Returns whether the {@link SkillEntry} is acknowledged by the user
     * meaning he has actively linked the {@link Skill} to the {@link Level}.
     * 
     * @return the acknowledged
     */
    public boolean isAcknowledged() {

        return acknowledged;
    }


    /**
     * Sets whether the {@link SkillEntry} is accepted by a {@link User}.
     * 
     * @param acknowledged the acknowledged to set
     */
    public void setAcknowledged(boolean acknowledged) {

        this.acknowledged = acknowledged;
    }


    /**
     * Creates skill entries for all {@link Skill}s of the given
     * {@link Category}.
     * 
     * @param category
     * @param matrix
     * @return
     */
    static List<SkillEntry> from(Category category, SkillMatrix matrix) {

        List<SkillEntry> entries = new ArrayList<SkillEntry>();

        for (Skill skill : category.getSkillz()) {
            entries.add(new SkillEntry(skill, matrix, null));
        }

        return entries;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hades.domain.support.AbstractPersistable#toString()
     */
    @Override
    public String toString() {

        return String.format("%s: %s", getName(), getLevel().getName());
    }
}
