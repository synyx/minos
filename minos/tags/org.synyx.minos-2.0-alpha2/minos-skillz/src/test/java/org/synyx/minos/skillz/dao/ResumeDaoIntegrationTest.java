package org.synyx.minos.skillz.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.hades.domain.Pageable;
import org.synyx.hades.domain.Sort;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;
import org.synyx.minos.skillz.domain.resume.SkillLevelFilter;
import org.synyx.minos.test.AbstractDaoIntegrationTest;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Integration test for {@link ResumeDao}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@Transactional
public class ResumeDaoIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SkillzDao skillzDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private LevelDao levelDao;


    @Test
    public void findByQuery() throws Exception {

        User user = new User("username", "test@test.com", "password");
        userDao.save(user);
        Resume resume =
                new Resume(user, new MatrixTemplate("name"),
                        new ArrayList<Activity>());
        Category category = new Category("name");
        categoryDao.save(category);
        Skill skill = new Skill("skillname", category);
        skillzDao.save(skill);
        Level level = new Level("levelname", 0);
        levelDao.save(level);
        resume.getSkillz().add(skill, level);

        resumeDao.save(resume);

        Pageable pageable = mock(Pageable.class);
        when(pageable.getSort()).thenReturn(new Sort("lastModifiedDate"));
        when(pageable.getFirstItem()).thenReturn(0);
        when(pageable.getPageSize()).thenReturn(20);

        ResumeFilter resumeQuery = new SkillLevelFilter();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "skillname");
        parameters.put("ordinal", 0);

        List<Resume> resumes =
                resumeDao.findByFilter(pageable, resumeQuery, parameters);

        assertEquals(1, resumes.size());
    }

}
