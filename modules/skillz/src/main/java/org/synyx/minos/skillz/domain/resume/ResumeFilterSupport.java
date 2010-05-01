package org.synyx.minos.skillz.domain.resume;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.synyx.minos.skillz.domain.Resume;


/**
 * Base class for {@link ResumeFilter} providing defaults and thus leaving only
 * really required methods to be implemented by subclasses. This class will not
 * filter {@link Resume}s on the database level and return the plain
 * {@link Resume} list as result for object level filtering.
 * 
 * @author Markus Knittig - knittig@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class ResumeFilterSupport implements ResumeFilter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.domain.resume.ResumeFilter#filter(java.util.List,
     * java.util.Map)
     */
    @Override
    public List<Resume> filter(List<Resume> resumes,
            Map<String, Object> paramaters) {

        return resumes;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.skillz.domain.resume.ResumeFilter#getQueryPartString()
     */
    @Override
    public String getQueryPartString() {

        return null;
    }


    /**
     * Default implementation simply binds the given parameters by name as is.
     * In case you declare non-primitive {@link ResumeFilterParameters} in
     * {@link ResumeFilter#getParameters()} you have to implement custom binding
     * here.
     * 
     * @see ResumeFilter#bindParameters(Query, Map)
     */
    @Override
    public void bindParameters(Query query, Map<String, Object> parameters) {

        for (Entry<String, Object> parameter : parameters.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
    }
}
