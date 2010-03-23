package org.synyx.minos.skillz.domain;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Unit test for {@link Project}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ProjectUnitTest {

    private static final String LOREM_IPSUM =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.\nCum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate";
    private static final String FIRST_PARAGRAPH =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. ...";


    @Test
    public void abbreviatesDescriptionCorrectly() throws Exception {

        Project project = new Project("name", LOREM_IPSUM);

        assertEquals(FIRST_PARAGRAPH, project.getAbstract());
    }
}
