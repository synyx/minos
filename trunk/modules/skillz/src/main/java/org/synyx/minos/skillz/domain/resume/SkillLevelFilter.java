package org.synyx.minos.skillz.domain.resume;

import java.util.Map;

import javax.persistence.Query;

import org.synyx.minos.skillz.dao.LevelDao;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.resume.ResumeFilterParameters.Builder;


/**
 * A implementation of {@link ResumeFilter} which returns resumes by there name and level.
 * 
 * @author Markus Knittig - knittig@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillLevelFilter extends ResumeFilterSupport {

    private final LevelDao levelDao;


    public SkillLevelFilter(LevelDao levelDao) {

        this.levelDao = levelDao;
    }


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

        return new Builder().add("skill", String.class, "skillz.skill").addSingleChoice("level", Level.class,
                new ReferenceDataContainer(levelDao), "skillz.level").build();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilterSupport#getQueryPartString ()
     */
    @Override
    public String getQueryPartString() {

        return "JOIN x.skillz.entries skillz WHERE skillz.skill.name = :skill AND skillz.level.ordinal >= :level";
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilterSupport#manualBindParameters (javax.persistence.Query,
     * java.util.Map)
     */
    @Override
    public void bindParameters(Query query, Map<String, Object> parameters) {

        query.setParameter("level", ((Level) parameters.get("level")).getOrdinal());
        query.setParameter("skill", parameters.get("skill"));
    }
}
