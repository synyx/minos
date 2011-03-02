package org.synyx.minos.skillz.domain.resume;

import org.synyx.minos.skillz.domain.Resume;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;


/**
 * Interface that describes a resume filter.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public interface ResumeFilter {

    /**
     * Message key for displaying the filter name on the page and identifying the filter in the request.
     *
     * @return
     */
    String getMessageKey();


    /**
     * Returns the query part after the SELECT FROM part for filtering of {@link Resume}s on database level. E.g. <code>
     * JOIN x.skillz.entries skillz WHERE skillz.skill.name = :name
     * </code>
     * , {@literal null} or empty if to disable filtering on database level.
     *
     * @return
     */
    String getQueryPartString();


    /**
     * Callback method for filtering {@link Resume}s on the object level.
     *
     * @param resumes
     * @param paramaters
     * @return
     */
    List<Resume> filter(List<Resume> resumes, Map<String, Object> paramaters);


    /**
     * Returns an instance of {@link ResumeFilterParameters} which wraps the {@link ResumeFilterParameter}s.
     *
     * @return
     */
    ResumeFilterParameters getParameters();


    /**
     * Binds the given parameters to the given {@link Query}.
     *
     * @param query
     * @param parameters
     */
    void bindParameters(Query query, Map<String, Object> parameters);
}
