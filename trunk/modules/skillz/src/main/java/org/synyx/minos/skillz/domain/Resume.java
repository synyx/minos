package org.synyx.minos.skillz.domain;

import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.joda.time.DateMidnight;
import org.springframework.util.Assert;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.Image;
import org.synyx.minos.core.domain.User;


/**
 * Represents a users qualifications in terms of a {@link SkillMatrix} and a list of {@link Activity}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Resume extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -8231358570600262628L;

    @OneToOne
    private User subject;
    private Date birthday;

    private String title;
    private String position;
    private String certifications;
    private String publications;

    @Embedded
    private Image photo;

    private String foreignLanguages;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private final List<Activity> references;

    @OneToOne(cascade = { PERSIST, MERGE, REMOVE })
    private SkillMatrix skillz;


    /**
     * Creates an empty {@link Resume}.
     */
    protected Resume() {

        this.references = new ArrayList<Activity>();
    }


    /**
     * Creates a new {@link Resume} for the given {@link User}.
     * 
     * @param subject the {@link User} to create the {@link Resume} for
     * @param skillz the {@link SkillMatrix} to be captured for the {@link User}
     * @param references the {@link User}s references
     */
    public Resume(User subject, SkillMatrix skillz, List<Activity> references) {

        Assert.notNull(subject);
        Assert.notNull(skillz);

        this.subject = subject;
        this.skillz = skillz;
        this.references = new ArrayList<Activity>();

        if (null != references) {
            for (Activity reference : references) {
                Assert.isTrue(reference.isNew(),
                        "References must be saved seperatly to prevent missing createdBy and createdDate!");
                add(reference);
            }
        }
    }


    /**
     * Creates a new {@link Resume} for the given {@link User} creating the {@link SkillMatrix} from the given
     * {@link MatrixTemplate}.
     * 
     * @param subject
     * @param template
     * @param references
     */
    public Resume(User subject, MatrixTemplate template, List<Activity> references) {

        this(subject, new SkillMatrix(template), references);
    }


    /**
     * Returns all foreign languages a {@link User} speaks.
     * 
     * @return
     */
    public String getForeignLanguages() {

        return foreignLanguages;
    }


    /**
     * @param foreignLanguages the foreignLanguages to set
     */
    public void setForeignLanguages(String foreignLanguages) {

        this.foreignLanguages = foreignLanguages;
    }


    /**
     * Returns the subject the resume is held for.
     * 
     * @return the subject
     */
    public User getSubject() {

        return subject;
    }


    /**
     * @return the birthday
     */
    public DateMidnight getBirthday() {

        return null == birthday ? null : new DateMidnight(birthday);
    }


    /**
     * @param birthday the birthday to set
     */
    public Resume setBirthday(DateMidnight birthday) {

        this.birthday = null == birthday ? null : birthday.toDate();
        return this;
    }


    /**
     * @return the title
     */
    public String getTitle() {

        return title;
    }


    /**
     * @param title the title to set
     */
    public void setTitle(String title) {

        this.title = title;
    }


    /**
     * @return the title
     */
    public String getPosition() {

        return position;
    }


    /**
     * @param title the title to set
     */
    public void setPosition(String title) {

        this.position = title;
    }


    /**
     * @return the certifications
     */
    public String getCertifications() {

        return certifications;
    }


    /**
     * @param certifications the certifications to set
     */
    public void setCertifications(String certifications) {

        this.certifications = certifications;
    }


    /**
     * @return the publications
     */
    public String getPublications() {

        return publications;
    }


    /**
     * @param publications the publications to set
     */
    public void setPublications(String publications) {

        this.publications = publications;
    }


    /**
     * @return the photo
     */
    public Image getPhoto() {

        return photo;
    }


    /**
     * @param photo the photo to set
     */
    public void setPhoto(Image photo) {

        this.photo = photo;
    }


    /**
     * Returns all references.
     * 
     * @return the references
     */
    public List<Activity> getReferences() {

        Collections.sort(references);
        return Collections.unmodifiableList(references);
    }


    /**
     * Adds the given {@link Activity} to the references. Prevents duplicate references, thus you can safely call
     * {@link #add(Activity)} multiple times for a single {@link Activity}.
     * 
     * @param reference
     * @return
     */
    public Resume add(Activity reference) {

        if (!references.contains(reference)) {
            references.add(reference);
        }

        return this;
    }


    /**
     * Removes the given {@link Activity} from the references.
     * 
     * @param reference
     * @return
     */
    public Resume remove(Activity reference) {

        references.remove(reference);
        return this;
    }


    /**
     * Removes all {@link Activity}s assigned to the given {@link Project} from the {@link Resume}.
     * 
     * @param project
     * @return
     */
    public Resume remove(Project project) {

        List<Activity> toRemove = new ArrayList<Activity>();

        for (Activity reference : references) {

            if (reference.hasProject(project)) {
                toRemove.add(reference);
            }
        }

        references.removeAll(toRemove);
        return this;
    }


    /**
     * Returns the skillz.
     * 
     * @return the skillz
     */
    public SkillMatrix getSkillz() {

        return skillz;
    }
}
