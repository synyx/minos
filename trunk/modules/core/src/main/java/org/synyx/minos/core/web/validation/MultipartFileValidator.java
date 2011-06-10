package org.synyx.minos.core.web.validation;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Validator for {@link MultipartFile}s. Allows to define a {@link Set} of supported file extensions to upload. Defaults
 * to {@value #DEFAULT_ALLOWED_EXTENSIONS}.
 *
 * @author Markus Knittig - knittig@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MultipartFileValidator implements Validator {

    private static final Set<String> DEFAULT_ALLOWED_EXTENSIONS = new HashSet<String>(Arrays.asList("png", "jpg",
                "gif"));

    private Set<String> supportedExceptions = DEFAULT_ALLOWED_EXTENSIONS;

    /**
     * Set all supported file extensions to be uploaded.
     *
     * @param supportedExceptions the allowExtensions to set
     */
    public void setSupportedExceptions(Set<String> supportedExceptions) {

        this.supportedExceptions = CollectionUtils.isEmpty(supportedExceptions) ? DEFAULT_ALLOWED_EXTENSIONS
                                                                                : supportedExceptions;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return MultipartFile.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {

        MultipartFile multipartFile = (MultipartFile) target;

        String fileName = multipartFile.getOriginalFilename();

        if (!supportedExceptions.contains(StringUtils.getFilenameExtension(fileName.toLowerCase()))) {
            errors.reject("core.multipart.invalidExtension");
        }
    }
}
