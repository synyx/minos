package org.synyx.minos.skillz.dao;

import java.util.List;
import java.util.Map;

import org.synyx.hades.domain.Pageable;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;


/**
 * Custom DAO interface for {@link Resume} instances.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface ResumeDaoCustom {

    /**
     * Returns a {@link List} of {@link Resume}s for the given
     * {@link ResumeFilterSupport} and parameters.
     */
    List<Resume> findByFilter(Pageable pageable, ResumeFilter resumeFilter,
            Map<String, Object> parameters);

}
