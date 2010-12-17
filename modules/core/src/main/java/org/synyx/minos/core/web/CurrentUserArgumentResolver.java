package org.synyx.minos.core.web;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;


/**
 * {@link WebArgumentResolver} to inject the currently authenticated {@link User} into a web controller method.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CurrentUserArgumentResolver implements WebArgumentResolver {

    private AuthenticationService authenticationService;


    /**
     * Creates a new {@link CurrentUserArgumentResolver}.
     * 
     * @param authenticationService
     */
    @Autowired
    public CurrentUserArgumentResolver(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.bind.support.WebArgumentResolver#resolveArgument
     * (org.springframework.core.MethodParameter, org.springframework.web.context.request.NativeWebRequest)
     */
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

        boolean isUserType = User.class.equals(methodParameter.getParameterType());
        boolean hasAnnotation = false;

        for (Annotation annotation : methodParameter.getParameterAnnotations()) {
            if (annotation instanceof CurrentUser) {
                hasAnnotation = true;
                break;
            }
        }

        if (isUserType && hasAnnotation) {
            return authenticationService.getCurrentUser();
        }

        return UNRESOLVED;
    }
}
