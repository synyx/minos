package org.synyx.minos.core.web.validation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * Unit test for {@link MultipartFileValidator}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MultipartFileValidatorUnitTest {

    private MultipartFileValidator validator = new MultipartFileValidator();


    @Test
    public void supportsFileExtensions() throws Exception {

        supportsFileWithName("foo.jpg");
        supportsFileWithName("foo.JPG");
    }


    @Test
    public void supportsMultipartFiles() throws Exception {

        assertTrue(validator.supports(MultipartFile.class));
        assertTrue(validator.supports(CommonsMultipartFile.class));
    }


    private void supportsFileWithName(String name) {

        MockMultipartFile file = new MockMultipartFile(name, new byte[0]);
        Errors errors = new BeanPropertyBindingResult(file, "name");

        validator.validate(file, errors);

        assertTrue(errors.hasErrors());
    }
}
