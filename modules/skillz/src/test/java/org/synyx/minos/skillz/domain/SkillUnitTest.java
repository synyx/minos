package org.synyx.minos.skillz.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Skill;


/**
 * Unit test for {@link Skill}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillUnitTest {

    private static final Category CATEGORY = new Category("Category");
    private static final Category NEW_CATEGORY = new Category("New Category");


    @Test(expected = IllegalArgumentException.class)
    public void preventsNoName() throws Exception {

        new Skill(null, CATEGORY);
    }


    @Test(expected = IllegalArgumentException.class)
    public void preventsNoCategory() throws Exception {

        new Skill("Skill", null);
    }


    @Test
    public void reassigningCategory() throws Exception {

        Skill skill = new Skill("Skill", CATEGORY);
        assertEquals(CATEGORY, skill.getCategory());
        assertTrue(CATEGORY.has(skill));

        skill.setCategory(NEW_CATEGORY);
        assertEquals(NEW_CATEGORY, skill.getCategory());
        assertFalse(CATEGORY.has(skill));
        assertTrue(NEW_CATEGORY.has(skill));
    }
}
