package org.synyx.minos.skillz.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.synyx.minos.skillz.domain.Level;
import org.synyx.minos.test.AbstractDaoIntegrationTest;


/**
 * Integration test for {@link LevelDao}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class LevelDaoImplIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private LevelDao levelDao;


    @Before
    public void setUp() {

        for (int i = 0; i < 10; i++) {

            Level level = new Level("Level" + i, i);

            if (i == 0) {
                level.setDefault(true);
            }

            levelDao.saveAndFlush(level);
        }
    }


    @Test
    public void undefaultsLevelsCorrectly() throws Exception {

        assertDefaultlevel(0);

        List<Level> levels = levelDao.readByExample(new Level("Level8", 8));

        Level level = levels.get(0);
        level.setDefault(true);

        levelDao.undefaultAllBut(level);
        levelDao.save(level);

        assertDefaultlevel(8);
    }


    /**
     * Asserts, that the level with the given ordinal is the default one.
     * 
     * @param ordinal
     */
    private void assertDefaultlevel(int ordinal) {

        Level defaultLevel = levelDao.findDefault();

        assertNotNull(defaultLevel);
        assertEquals(ordinal, defaultLevel.getOrdinal().intValue());
    }
}
