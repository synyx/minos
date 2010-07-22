package org.synyx.minos.skillz.domain;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.TestConstants;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;


/**
 * Unit test for {@link Resume}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ResumeUnitTest {

    private Activity activityOne;
    private Activity activityTwo;


    @Before
    public void setUp() {

        activityOne = new Activity(new Project("project1"), new DateMidnight());
        activityTwo = new Activity(new Project("project2"), new DateMidnight());
    }


    @Test
    public void removesProjectCorrectly() throws Exception {

        Resume resume = new Resume(TestConstants.USER, new SkillMatrix(), Arrays.asList(activityOne, activityTwo));

        resume.remove(activityOne.getProject());

        assertEquals(1, resume.getReferences().size());
        assertFalse(resume.getReferences().contains(activityOne));
    }
}
