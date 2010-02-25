package org.synyx.minos.skillz.domain.resume;

import org.synyx.minos.skillz.domain.resume.ResumeFilterParameters.Builder;


/**
 * A implementation of {@link ResumeFilter} which returns resumes by there name
 * and level.
 * 
 * @author Markus Knittig - knittig@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillLevelFilter extends ResumeFilterSupport {

    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilter#getMessageKey()
     */
    @Override
    public String getMessageKey() {

        return "skillz.skill";
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilter#getParameters()
     */
    @Override
    public ResumeFilterParameters getParameters() {

        return new Builder().add("name", String.class, "skillz.skill").add(
                "ordinal", Integer.class, "skillz.level").build();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.domain.resume.ResumeFilterSupport#getQueryPartString
     * ()
     */
    @Override
    public String getQueryPartString() {

        return "JOIN x.skillz.entries skillz WHERE skillz.skill.name = :name AND skillz.level.ordinal >= :ordinal";
    }
}
