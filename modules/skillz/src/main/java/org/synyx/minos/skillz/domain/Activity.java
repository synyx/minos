package org.synyx.minos.skillz.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;


/**
 * An activity abstracts work someone has done for a particular {@link Project} ofer a given frame in time, as well as
 * her responsibilities she took in that project.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Activity extends AbstractAuditable<User, Long> implements Comparable<Activity> {

    private static final long serialVersionUID = 3743848895842525208L;

    @ManyToOne
    private Project project;

    private boolean omitProjectDescription;

    @Lob
    private String additionalDescription;

    @Temporal(TemporalType.DATE)
    @Column(name = "project_start")
    private Date start;

    @Temporal(TemporalType.DATE)
    @Column(name = "project_end")
    private Date end;

    @ManyToMany
    private List<Responsibility> responsibilities;


    protected Activity() {

        this.responsibilities = new ArrayList<Responsibility>();
    }


    /**
     * Creates a new {@link Activity} for the given {@link Project} and the given time frame. Allows to create a
     * {@link Activity} for ongoing project activity by providing {@code null} as end date.
     * 
     * @param project
     * @param start
     * @param end the end date of the reference. Might be {@code null} to indicate still running project activity.
     */
    public Activity(Project project, DateMidnight start, DateMidnight end) {

        this();

        Assert.notNull(project);
        Assert.notNull(start);

        this.project = project;
        this.start = start.toDate();
        this.end = null == end ? null : end.toDate();
    }


    /**
     * Creates a new {@link Activity} for the given {@link Project} indicating that the owner is still actively working
     * in the {@link Project}.
     * 
     * @param project
     * @param start
     */
    public Activity(Project project, DateMidnight start) {

        this(project, start, null);
    }


    /**
     * Returns the project the {@link Activity} was done for.
     * 
     * @return the project
     */
    public Project getProject() {

        return project;
    }


    /**
     * Assigns the activity to the given {@link Project}.
     * 
     * @param project
     */
    public void setProject(Project project) {

        this.project = project;
    }


    /**
     * Returns whether the additional description held for this {@link Activity} shall replace the {@link Project}'s
     * description entirely. Will only return {@literal true} if there is an additional description at all.
     * 
     * @see #getAdditionalDescription()
     * @return the omitProjectDescription
     */
    public boolean isOmitProjectDescription() {

        return omitProjectDescription && !StringUtils.isBlank(additionalDescription);
    }


    /**
     * @param omitProjectDescription the omitProjectDescription to set
     */
    public void setOmitProjectDescription(boolean omitProjectDescription) {

        this.omitProjectDescription = omitProjectDescription;
    }


    /**
     * Returns additional information regarding the {@link Activity} for the linked {@link Project}. Thus users can
     * leave additional notes privately held for them. If {@link #setOmitProjectDescription(boolean)} the additional
     * description will replace the {@link Project}'s description entirely.
     * 
     * @see #setOmitProjectDescription(boolean)
     * @return the additionalDescription
     */
    public String getAdditionalDescription() {

        return additionalDescription;
    }


    /**
     * @param additionalDescription the additionalDescription to set
     */
    public void setAdditionalDescription(String additionalDescription) {

        this.additionalDescription = additionalDescription;
    }


    /**
     * Returns the start of the activity for the {@link Project}.
     * 
     * @return the start
     */
    public DateMidnight getStart() {

        return new DateMidnight(start);
    }


    /**
     * Sets the start of the activity for the {@link Project}.
     * 
     * @param start the start to set
     */
    public void setStart(DateMidnight start) {

        this.start = start == null ? null : start.toDate();
    }


    /**
     * Returns the end of the {@link Activity} for the {@link Project}. Might return {@code null} to indicate ongoing
     * involvement.
     * 
     * @return the end
     */
    public DateMidnight getEnd() {

        return null != end ? new DateMidnight(end) : null;
    }


    /**
     * Sets the end of the {@link Activity} for the {@link Project}. Allows to indicate ongoing involvement by passing
     * {@code null}.
     * 
     * @param end the end to set
     */
    public void setEnd(DateMidnight end) {

        this.end = null == end ? null : end.toDate();
    }


    /**
     * Returns whether the project that is referenced is still active, meaning the owner of this {@link Activity} is
     * still working on the project.
     * 
     * @return
     */
    public boolean getIsStillActive() {

        return null == end;
    }


    /**
     * Returns the responsibilities the owner took in the {@link Project}.
     * 
     * @return the responsibilities
     */
    public List<Responsibility> getResponsibilities() {

        return Collections.unmodifiableList(responsibilities);
    }


    /**
     * @param responsibilities the responsibilities to set
     */
    public void setResponsibilities(List<Responsibility> responsibilities) {

        if (responsibilities == null) {
            this.responsibilities = Collections.emptyList();
        } else {
            this.responsibilities = responsibilities;
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Activity that) {

        if (getStart().isBefore(that.getStart())) {
            return 1;
        }

        if (getStart().isAfter(that.getStart())) {
            return -1;
        }

        return 0;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hades.domain.support.AbstractPersistable#toString()
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("Project reference: ");
        builder.append(project.toString());
        builder.append(" - From ");
        builder.append(start).append(" to ").append(end);

        return builder.toString();
    }


    /**
     * Returns wheter the {@link Activity} is assigned to the given {@link Project}.
     * 
     * @param project
     * @return
     */
    public boolean hasProject(Project project) {

        return this.project.equals(project);
    }
}
