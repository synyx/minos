package org.synyx.minos.skillz.service;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;

import java.util.List;
import java.util.Map;


/**
 * Interface for resume administration services.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public interface ResumeAdminstration {

    /**
     * Returns all {@link Resume}s.
     *
     * @return
     */
    List<Resume> getResumes();


    /**
     * Returns the requested page of {@link Resume}s.
     *
     * @param request
     * @return
     */
    Page<Resume> getResumes(Pageable request);


    /**
     * Returns all available {@link ResumeFilter}s.
     *
     * @return
     */
    List<ResumeFilter> getResumeFilters();


    /**
     * Returns the {@link ResumeFilter} for the given query name.
     *
     * @param filterName
     * @return
     */
    ResumeFilter getResumeFilter(String filterName);


    /**
     * Returns a {@link Page} of {@link Resume}s for the given {@link ResumeFilter} and parameters.
     *
     * @param pageable
     * @param resumeFilter
     * @param parameters
     * @return
     */
    Page<Resume> getResumesByFilter(Pageable pageable, ResumeFilter resumeFilter, Map<String, String[]> parameters);
}
