package org.synyx.minos.skillz.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.core.web.WebTestUtils.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.SkillManagement;


/**
 * Unit test for {@link ResumeController}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ResumeControllerUnitTest {

    private ResumeController controller;

    @Mock
    private ResumeManagement resumeManagement;
    @Mock
    private SkillManagement skillManagement;
    @Mock
    private Resume resume;

    private Errors errors;
    private Model model;


    @Before
    public void setUp() {

        controller =
                new ResumeController(resumeManagement, skillManagement, null);

        errors = new BeanPropertyBindingResult(null, "");
        model = new ExtendedModelMap();
    }


    @Test
    public void rejectsInvalidResumeId() throws Exception {

        String view = controller.resume(5L, model);

        verify(resumeManagement).getResume(5L);
        assertNull(view);
        assertTrue(model.asMap().get(Core.MESSAGE) instanceof Message);
    }


    @Test
    public void rejectsInvalidFileExtension() throws Exception {

        MultipartFile multipartFile =
                new MockMultipartFile("foobar.exe", "foobar.exe", "", ""
                        .getBytes());

        controller.saveResumePhoto(resume, errors, model, multipartFile);

        assertErrorMessage(model);
    }

}
