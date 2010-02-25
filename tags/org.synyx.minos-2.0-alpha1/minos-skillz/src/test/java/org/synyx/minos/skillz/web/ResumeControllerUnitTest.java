package org.synyx.minos.skillz.web;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.Minos;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.SkillManagement;
import org.synyx.minos.skillz.web.ResumeController;



/**
 * Unit test for {@link ResumeController}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ResumeControllerUnitTest {

    private ResumeController controller;

    private ResumeManagement resumeManagement;
    private SkillManagement skillManagement;

    private Model model;


    @Before
    public void setUp() {

        resumeManagement = createNiceMock(ResumeManagement.class);
        skillManagement = createNiceMock(SkillManagement.class);

        controller = new ResumeController(resumeManagement, skillManagement);

        model = new ExtendedModelMap();
    }


    @Test
    public void rejectsInvalidResumeId() throws Exception {

        expect(resumeManagement.getResume(5L)).andReturn(null);
        replay(resumeManagement, skillManagement);

        String view = controller.resume(5L, model);

        assertNull(view);
        assertTrue(model.asMap().get(Minos.MESSAGE) instanceof Message);
    }
}
