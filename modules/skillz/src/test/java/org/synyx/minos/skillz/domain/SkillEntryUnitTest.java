package org.synyx.minos.skillz.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.SkillEntry;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Unit test for {@link SkillEntry}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillEntryUnitTest {

    private Category category;
    private Skill skill;
    private SkillMatrix matrix;
    private Level level;


    @Before
    public void setUp() {

        category = new Category("category");
        skill = new Skill("skill", category);
        matrix = new SkillMatrix();
        level = new Level("level", 0);
    }


    @Test
    public void hasSkillAndCategory() throws Exception {

        SkillEntry entry = new SkillEntry(skill, matrix, level);
        assertTrue(entry.has(skill));
        assertTrue(entry.has(category));
    }
}
