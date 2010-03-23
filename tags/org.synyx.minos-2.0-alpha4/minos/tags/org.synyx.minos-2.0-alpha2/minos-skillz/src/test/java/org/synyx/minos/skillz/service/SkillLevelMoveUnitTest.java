package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.skillz.dao.LevelDao;
import org.synyx.minos.skillz.domain.Level;


/**
 * Unit test for {@link Level} move methods in {@code SkillManagementImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class SkillLevelMoveUnitTest {

    private SkillManagementImpl skillManagement;

    @Mock
    private LevelDao levelDao;

    private Level level1;
    private Level level2;


    @Before
    public void setUp() {

        skillManagement = new SkillManagementImpl();
        skillManagement.setLevelDao(levelDao);

        level1 = new Level("level1", 1);
        level2 = new Level("level2", 2);
    }


    @Test
    public void movesALevelDown() throws Exception {

        when(levelDao.findLowerLevel(2)).thenReturn(Arrays.asList(level1));
        when(levelDao.readByPrimaryKey(level2.getId())).thenReturn(level2);

        skillManagement.moveLevelDown(level2);

        assertEquals(1, level2.getOrdinal().intValue());
        assertEquals(2, level1.getOrdinal().intValue());
    }


    @SuppressWarnings("unchecked")
    @Test
    public void doesNoMoveALevelDownOutOfBoundaries() throws Exception {

        when(levelDao.findLowerLevel(1)).thenReturn(Collections.EMPTY_LIST);

        skillManagement.moveLevelDown(level1);

        assertEquals(1, level1.getOrdinal().intValue());
    }


    @Test
    public void movesALevelUp() throws Exception {

        when(levelDao.findUpperLevel(1)).thenReturn(Arrays.asList(level2));
        when(levelDao.readByPrimaryKey(level1.getId())).thenReturn(level1);

        skillManagement.moveLevelDown(level1);

        assertEquals(1, level1.getOrdinal().intValue());
        assertEquals(2, level2.getOrdinal().intValue());
    }


    @SuppressWarnings("unchecked")
    @Test
    public void doesNoMoveALevelUpOutOfBoundaries() throws Exception {

        when(levelDao.findUpperLevel(1)).thenReturn(Collections.EMPTY_LIST);

        skillManagement.moveLevelUp(level1);

        assertEquals(1, level1.getOrdinal().intValue());
    }

}
