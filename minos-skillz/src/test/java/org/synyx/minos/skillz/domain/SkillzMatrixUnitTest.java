package org.synyx.minos.skillz.domain;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Unit test for {@link SkillMatrix}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillzMatrixUnitTest {

    private Category categoryOne;
    private Category categoryTwo;

    private Skill skillOne;
    private Skill skillTwo;

    private Level levelOne;
    private Level levelTwo;

    private SkillMatrix matrix;


    @Before
    public void setUp() {

        categoryOne = new Category("Foo");
        categoryTwo = new Category("Bar");

        matrix = new SkillMatrix();

        skillOne = new Skill("REST", categoryOne);
        skillTwo = new Skill("SOAP", categoryTwo);

        levelOne = new Level("+", 1);
        levelTwo = new Level("++", 2);

        matrix.add(skillOne, levelOne).add(skillTwo, levelTwo);
    }


    @Test
    public void computesAverageLevelsCorrectly() throws Exception {

        assertEquals(1.5d, matrix.getAverageLevel(), 0.1d);
        assertEquals(1d, matrix.getAverageLevel(categoryOne), 0.1d);
        assertEquals(2d, matrix.getAverageLevel(categoryTwo), 0.1d);
    }


    @Test
    public void removesSkillCorrectly() {

        int count = matrix.size();

        matrix.remove(skillOne);

        assertEquals(count - 1, matrix.size());
        assertFalse(matrix.has(skillOne));
    }


    @Test
    public void appliesCategoriesCorrectly() {

        Category newCategory = new Category("FooBar");
        Skill newSkill = new Skill("Axis", newCategory);

        matrix.setCategories(Arrays.asList(categoryTwo, newCategory));

        // Assert old category removed
        assertFalse(matrix.has(categoryOne));

        // Assert new categories connected
        assertTrue(matrix.has(categoryTwo));
        assertTrue(matrix.has(newCategory));

        // Assert kill connected
        assertTrue(matrix.has(newSkill));
    }


    @Test
    public void addsCategoryOnlyOnce() {

        assertTrue(matrix.has(categoryOne));
        int size = matrix.size();

        matrix.add(categoryOne);

        assertEquals(size, matrix.size());
    }


    @Test
    public void addsSkillOnlyOnce() {

        assertTrue(matrix.has(skillOne));
        int size = matrix.size();

        matrix.add(skillOne, levelOne);

        assertEquals(size, matrix.size());
    }
}
