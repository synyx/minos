package org.synyx.minos.skillz.service;

import java.util.List;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Skill;


/**
 * Interface for skill management services.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface SkillManagement {

    /**
     * Returns all available {@link Level}s.
     * 
     * @return
     */
    List<Level> getLevels();


    /**
     * Saves the given level.
     * 
     * @param level
     * @return
     */
    Level save(Level level);


    /**
     * Returns the default {@link Level}.
     * 
     * @return
     */
    Level getDefaultLevel();


    /**
     * Returns all projects.
     * 
     * @return
     */
    List<Project> getProjects();


    /**
     * Returns all commonly available projects.
     * 
     * @return
     */
    List<Project> getPublicProjects();


    /**
     * Returns all projects that are privately owned by the given {@link User}.
     * 
     * @param user
     * @return
     */
    List<Project> getPrivateProjectsFor(User user);


    /**
     * Returns the project with the given id.
     * 
     * @param id
     * @return
     */
    Project getProject(Long id);


    /**
     * Saves the given {@link Project}.
     * 
     * @param project
     * @return
     */
    Project save(Project project);


    /**
     * Deletes the given project.
     * 
     * @param project
     */
    void delete(Project project);


    /**
     * Returns all {@link Category}.
     * 
     * @return
     */
    List<Category> getCategories();


    /**
     * Returns the {@link Category} with the given id.
     * 
     * @param id
     * @return
     */
    Category getCategory(Long id);


    /**
     * Saves the given {@link Skill}.
     * 
     * @param skill
     */
    Skill save(Skill skill);


    /**
     * @param skill
     * @param category
     * @return
     */
    void moveSkill(Skill skill, Category category);


    /**
     * Deletes the given {@link Skill}.
     * 
     * @param skill
     */
    void delete(Skill skill);


    /**
     * Returns all {@link MatrixTemplate}s.
     * 
     * @return
     */
    List<MatrixTemplate> getTemplates();


    /**
     * Deletes a {@link MatrixTemplate}.
     * 
     * @param template
     */
    void delete(MatrixTemplate template);


    /**
     * Saves a {@link MatrixTemplate}.
     * 
     * @param template
     */
    MatrixTemplate save(MatrixTemplate template);


    /**
     * Returns a {@link MatrixTemplate} with the given id.
     * 
     * @param id
     * @return
     */
    MatrixTemplate getTemplate(Long id);


    /**
     * Saves a given category.
     * 
     * @param category
     */
    Category save(Category category);


    /**
     * @param category
     */
    void delete(Category category);
}
