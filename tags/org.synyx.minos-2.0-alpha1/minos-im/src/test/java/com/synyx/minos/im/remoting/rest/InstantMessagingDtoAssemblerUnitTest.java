package com.synyx.minos.im.remoting.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.synyx.minos.TestConstants;
import org.synyx.minos.core.remoting.rest.AbstractDtoAssembler;
import org.synyx.minos.core.remoting.rest.dto.IdentifyableDto;
import org.synyx.minos.im.remoting.rest.InstantMessageDtoAssembler;
import org.synyx.minos.umt.web.UmtUrls;



/**
 * Unit test for {@link InstantMessageDtoAssembler}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class InstantMessagingDtoAssemblerUnitTest {

    private AbstractDtoAssembler dtoAssembler;


    @Before
    public void setUp() {

        dtoAssembler = new InstantMessageDtoAssembler();
    }


    /**
     * Tests that user links get rendered correctly. TODO: Move into test class
     * for {@link AbstractDtoAssembler} as the functionality was extracted into
     * this class.
     */
    @Test
    public void rendersUserLinksCorrectly() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/minos/rest");

        IdentifyableDto userDto =
                dtoAssembler.toIdentifyable(TestConstants.USER,
                        UmtUrls.EDIT_USER, request);

        System.out.println(userDto.getHref());
    }
}
