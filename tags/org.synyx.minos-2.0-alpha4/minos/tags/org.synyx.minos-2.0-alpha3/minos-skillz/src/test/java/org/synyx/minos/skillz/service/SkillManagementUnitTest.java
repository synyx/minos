package org.synyx.minos.skillz.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.hades.domain.Sort;
import org.synyx.minos.skillz.dao.LevelDao;
import org.synyx.minos.skillz.domain.Level;


/**
 * Unit test for {@code SkillManagementImpl}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class SkillManagementUnitTest {

    private SkillManagementImpl skillManagement;

    @Mock
    private LevelDao levelDao;


    @Before
    public void setUp() {

        skillManagement = new SkillManagementImpl();
        skillManagement.setLevelDao(levelDao);
    }


    @Test
    public void setsInitialOrdinal() throws Exception {

        when(levelDao.readAll()).thenReturn(new ArrayList<Level>());

        Level level = new Level("Test", null);
        skillManagement.save(level);

        verify(levelDao).save(level);
        assertEquals(0, level.getOrdinal().intValue());
    }


    @Test
    public void setsOrdinalWithOneLevel() throws Exception {

        when(levelDao.readAll(any(Sort.class))).thenReturn(
                Arrays.asList(new Level("Test", 0)));

        Level level = new Level("Test2", null);

        skillManagement.save(level);

        verify(levelDao).save(level);
        assertEquals(1, level.getOrdinal().intValue());
    }


    @Test
    public void preservesExistingOrdinal() throws Exception {

        when(levelDao.readAll()).thenReturn(new ArrayList<Level>());

        Level level = new Level("Test2", 2);
        skillManagement.save(level);

        verify(levelDao).save(level);
        assertEquals(2, level.getOrdinal().intValue());
    }

}
