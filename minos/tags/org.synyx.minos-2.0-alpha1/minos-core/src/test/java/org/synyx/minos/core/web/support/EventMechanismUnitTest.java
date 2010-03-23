package org.synyx.minos.core.web.support;

import static org.synyx.minos.TestConstants.*;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.synyx.minos.core.web.Minos;

import com.synyx.utils.test.easymock.EasyMockUtils;


/**
 * Unit test for {@code EventOrchestrator}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public class EventMechanismUnitTest {

    private static final String UMT_VIEW = "umtView";
    private static final String PMT_VIEW = "pmtView";

    @Autowired
    @Qualifier("umt")
    private Controller umtController;

    @Autowired
    @Qualifier("pmt")
    private Controller pmtController;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


    /**
     * Sets up mocks for {@code HttpServletRequest} and {@code
     * HttpServletResponse}.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }


    /**
     * Tests simple throwing of an event and handling it.
     * 
     * @throws Exception
     */
    @Test
    public void testConfiguration() throws Exception {

        // Create model and view for original controller
        ModelAndView umtMav = new ModelAndView(UMT_VIEW);
        umtMav.addObject(Minos.EVENT_KEY, new UserCreatedEvent(USER));
        umtMav.addObject("user", USER);

        expectControllerToReturn(umtController, umtMav);

        // Create model and view for target controller
        ModelAndView mockMav = new ModelAndView(PMT_VIEW);
        mockMav.addObject(Minos.EVENT_KEY,
                new ProjectDeletedEvent("MOCK_EVENT"));
        mockMav.addObject("bar", "foo");

        expectControllerToReturn(pmtController, mockMav);

        // Execute original controller
        ModelAndView mav = umtController.handleRequest(request, response);

        EasyMockUtils.verify(umtController, pmtController);

        // Assert the resulting model and view contains the original
        // controller's view
        Assert.assertEquals(UMT_VIEW, mav.getViewName());

        ModelMap map = mav.getModelMap();

        // Assert we find the original controllers model attributes
        Assert.assertTrue(map.containsAttribute("user"));
        Assert.assertEquals(USER, map.get("user"));

        // Assert we find the target controllers model attributes
        Assert.assertTrue(map.containsAttribute("bar"));
        Assert.assertEquals("foo", map.get("bar"));
    }


    /**
     * Adds expectation for one call to {@code
     * Controller#handleRequest(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)} returning the given {@code
     * ModelAndView} as result.
     * 
     * @param advisedController
     * @param mav
     * @throws Exception
     */
    private void expectControllerToReturn(Controller advisedController,
            ModelAndView mav) throws Exception {

        Controller controller = EasyMockUtils.unwrap(advisedController);

        EasyMock.expect(controller.handleRequest(request, response)).andReturn(
                mav).anyTimes();
        EasyMock.replay(controller);
    }
}
