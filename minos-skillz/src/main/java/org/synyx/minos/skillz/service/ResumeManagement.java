package org.synyx.minos.skillz.service;

import java.util.List;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Responsibility;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Interface for resume management services.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ResumeManagement {

    /**
     * Returns the {@link Resume} of the current {@link User}.
     * 
     * @return
     */
    Resume getResume();


    /**
     * Returns the {@link Resume} for the given {@link User}.
     * 
     * @param id
     * @return
     */
    Resume getResume(Long id);


    /**
     * Saves the given {@link Resume}.
     * 
     * @param resume
     * @return
     */
    Resume save(Resume resume);


    /**
     * Saves the given {@link SkillMatrix}.
     * 
     * @param matrix
     * @return
     */
    SkillMatrix save(SkillMatrix matrix);


    /**
     * Applies the given {@link MatrixTemplate} to the given {@link Resume}.
     * Will update an already existing {@link SkillMatrix} linked to the
     * {@link Resume} according to the {@link MatrixTemplate} or create a
     * completely new {@link SkillMatrix} for a new {@link Resume}.
     * 
     * @param resume
     * @param template
     * @return
     */
    Resume save(Resume resume, MatrixTemplate template);


    /**
     * Returns an {@link Activity} by its id.
     * 
     * @param id
     * @return
     */
    Activity getReference(Long id);


    /**
     * Saves a given {@link Activity}.
     * 
     * @param reference
     * @return
     */
    Activity save(Activity reference);


    /**
     * Deletes the given {@link Activity}.
     * 
     * @param reference
     */
    void delete(Activity reference);


    /**
     * Returns all {@link Resume}s.
     * 
     * @return
     */
    List<Resume> getResumes();


    Page<Resume> getResumes(Pageable request);


    /**
     * @return
     */
    List<Responsibility> getResponsibilities();
}
