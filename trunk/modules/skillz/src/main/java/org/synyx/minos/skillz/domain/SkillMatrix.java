package org.synyx.minos.skillz.domain;

import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;


/**
 * A skill matrix binds {@link Level}s to a set of {@link Skill}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class SkillMatrix extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = 8926027934220678416L;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<SkillEntry> entries;

    @ManyToOne(cascade = { PERSIST, MERGE })
    private MatrixTemplate template;


    /**
     * Creates an empty {@link SkillMatrix}.
     */
    public SkillMatrix() {

        this(new ArrayList<SkillEntry>());
    }


    /**
     * 
     */
    public SkillMatrix(List<SkillEntry> entries) {

        this.entries = entries;
    }


    public SkillMatrix(MatrixTemplate template) {

        this();
        setTemplate(template);
    }


    /**
     * Returns the {@link MatrixTemplate} the {@link SkillMatrix} is bound to.
     * 
     * @return the template
     */
    public MatrixTemplate getTemplate() {

        return template;
    }


    /**
     * Binds the {@link SkillMatrix} to the given {@link MatrixTemplate}. This
     * will cause all categories of the {@link MatrixTemplate} not currently
     * bound to the matrix being assigned to it. Furthermore all
     * {@link Category}s and thus {@link SkillEntry}s for its {@link Skill}s
     * will be removed.
     * 
     * @param template
     * @return
     */
    public SkillMatrix setTemplate(MatrixTemplate template) {

        Assert.notNull(template);

        this.template = template;
        setCategories(template.getCategories());

        return this;
    }


    /**
     * Sets the given {@link Category} collection. Essentially merges the
     * currently existing categories with the given ones by leaving the ones
     * existing in both collections untouched, adding not yet contained ones to
     * the {@link SkillMatrix} and returning the ones to be removed.
     * 
     * @param categories
     * @return
     */
    void setCategories(Collection<Category> categories) {

        for (Category oldCategory : getCategories()) {

            if (!categories.contains(oldCategory)) {
                remove(oldCategory);
            }
        }

        for (Category category : categories) {
            add(category);
        }
    }


    /**
     * Adds the given category to the {@link SkillMatrix}. Drops duplicates and
     * will create {@link SkillEntry}s without any level assigned.
     * 
     * @param category
     * @return
     */
    public SkillMatrix add(Category category) {

        if (has(category)) {
            return this;
        }

        for (SkillEntry entry : SkillEntry.from(category, this)) {
            add(entry);
        }

        return this;
    }


    /**
     * Returns whether the matrix contains the given {@link Category}. Might not
     * be the case if it had been created from a {@link MatrixTemplate} not
     * containing the {@link Category}.
     * 
     * @param category
     * @return
     */
    public boolean has(Category category) {

        for (SkillEntry entry : entries) {
            if (entry.has(category)) {
                return true;
            }
        }

        return false;
    }


    public boolean has(Skill skill) {

        for (SkillEntry entry : entries) {
            if (entry.has(skill)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Adds the given {@link SkillEntry} to the matrix.
     * 
     * @param entry
     * @return
     */
    public SkillMatrix add(SkillEntry entry) {

        if (has(entry.getSkill())) {
            return this;
        }

        entry.setMatrix(this);
        this.entries.add(entry);

        return this;
    }


    /**
     * Adds a new {@link SkillEntry} to the matrix by providing {@link Skill}
     * and {@link Level}.
     * 
     * @param skill
     * @param level
     * @return
     */
    public SkillMatrix add(Skill skill, Level level) {

        return add(new SkillEntry(skill, this, level));
    }


    /**
     * Returns a {@link SkillEntry} for the given {@link Skill} from the matrix.
     * 
     * @param skill
     */
    public SkillMatrix remove(Skill skill) {

        if (!has(skill)) {
            return this;
        }

        SkillEntry toRemove = null;

        for (SkillEntry entry : entries) {
            if (entry.has(skill)) {
                toRemove = entry;
            }
        }

        if (null != toRemove) {
            entries.remove(toRemove);
        }

        return this;
    }


    /**
     * Removes the {@link Category} from the {@link SkillMatrix}.
     * 
     * @param category
     * @return
     */
    public SkillMatrix remove(Category category) {

        if (!has(category)) {
            return this;
        }

        for (Skill skill : category.getSkillz()) {
            remove(skill);
        }

        return this;
    }


    /**
     * Returns the size of the matrix.
     * 
     * @return
     */
    public int size() {

        return entries.size();
    }


    /**
     * Returns all {@link SkillEntry}s of the matrix.
     * 
     * @return the entries
     */
    public List<SkillEntry> getEntries() {

        return Collections.unmodifiableList(entries);
    }


    /**
     * Returns all {@link SkillEntry}s of the given {@link Category}.
     * 
     * @param category
     * @return
     */
    public List<SkillEntry> getEntries(Category category) {

        List<SkillEntry> entries = new ArrayList<SkillEntry>();

        for (SkillEntry entry : getEntries()) {

            if (category.equals(entry.getCategory())) {
                entries.add(entry);
            }
        }

        return entries;
    }


    /**
     * Sets the {@link SkillEntry}s.
     * 
     * @param entries
     * @return
     */
    public SkillMatrix setEntries(List<SkillEntry> entries) {

        this.entries = new ArrayList<SkillEntry>();

        for (SkillEntry entry : entries) {

            this.add(entry);
        }
        return this;
    }


    /**
     * Returns a map of {@link Category}s to their connected {@link SkillEntry}
     * s.
     * 
     * @return
     */
    public Map<Category, List<SkillEntry>> getMap() {

        Map<Category, List<SkillEntry>> result =
                new HashMap<Category, List<SkillEntry>>();

        for (SkillEntry entry : getEntries()) {

            Category category = entry.getCategory();

            if (!result.containsKey(entry.getCategory())) {
                result.put(category, new ArrayList<SkillEntry>());
            }

            result.get(category).add(entry);
        }

        return Collections.unmodifiableMap(result);
    }


    /**
     * Returns the index of the given {@link SkillEntry} inside the matrix.
     * 
     * @param entry
     * @return
     */
    int getIndex(SkillEntry entry) {

        return this.entries.indexOf(entry);
    }


    /**
     * Returns the average skill {@link Level} through all the
     * {@link SkillEntry}.
     * 
     * @return
     */
    public double getAverageLevel() {

        return getAverageLevel(getEntries());
    }


    /**
     * Returns the average skill {@link Level} for the given {@link Category}.
     * 
     * @param category
     * @return
     */
    public double getAverageLevel(Category category) {

        return getAverageLevel(getEntries(category));
    }


    /**
     * Marks all {@link SkillEntry}s as acknowledged.
     * 
     * @return
     */
    public SkillMatrix acknowledge() {

        for (SkillEntry entry : getEntries()) {
            entry.setAcknowledged(true);
        }
        return this;
    }


    /**
     * Calculates the average {@link Level} for the given {@link SkillEntry}s.
     * 
     * @param entries
     * @return
     */
    private double getAverageLevel(Collection<SkillEntry> entries) {

        Assert.notNull(entries);

        if (getEntries().isEmpty()) {
            return 0.0;
        }

        double average = 0.0;

        for (SkillEntry entry : entries) {
            average += entry.getLevel().getOrdinal();
        }

        return average / entries.size();
    }


    /**
     * Returns all categories available in the {@link SkillMatrix}.
     * 
     * @return
     */
    private List<Category> getCategories() {

        List<Category> result = new ArrayList<Category>();
        for (SkillEntry entry : entries) {
            if (!result.contains(entry.getCategory())) {
                result.add(entry.getCategory());
            }
        }

        return Collections.unmodifiableList(result);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hades.domain.support.AbstractPersistable#toString()
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        for (SkillEntry entry : entries) {
            builder.append(entry.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
