package org.synyx.minos.core.web.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class MultipartFileValidator implements Validator {

    private Set<String> allowExtensions =
            new HashSet<String>(Arrays.asList("png", "jpg", "gif"));


    @Override
    public boolean supports(Class<?> clazz) {

        return clazz.isAssignableFrom(MultipartFile.class);
    }


    @Override
    public void validate(Object target, Errors errors) {

        MultipartFile multipartFile = (MultipartFile) target;

        String fileName = multipartFile.getOriginalFilename();
        if (!allowExtensions.contains(StringUtils
                .getFilenameExtension(fileName))) {
            errors.reject("core.multipart.invalidExtension");
        }
    }


    /**
     * @return the allowExtensions
     */
    public Set<String> getAllowExtensions() {

        return allowExtensions;
    }


    /**
     * @param allowExtensions the allowExtensions to set
     */
    public void setAllowExtensions(Set<String> allowExtensions) {

        this.allowExtensions = allowExtensions;
    }

}