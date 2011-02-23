package org.synyx.minos.core.security;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;

import java.io.IOException;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Custom {@link AuthenticationSuccessHandler} that allows defining target views based on the authenticated {@link User}
 * s {@link Role}.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class MappingAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DUPLICATE_ROLE_MAPPING_TEMPLATE =
        "Role %s mapped twice! Check your maping configuration!";

    private Multimap<String, String> mapping = HashMultimap.create();

    /**
     * Configure the target views depending on the assigned {@link Role}. Be sure not to map a {@link Role} to two
     * target views.
     *
     * @param  mappings  the mappings to set
     */
    public void setMappings(Map<String, String[]> mappings) {

        for (Entry<String, String[]> entry : mappings.entrySet()) {
            for (String role : entry.getValue()) {
                if (this.mapping.containsValue(role)) {
                    throw new IllegalArgumentException(String.format(DUPLICATE_ROLE_MAPPING_TEMPLATE, role));
                } else {
                    this.mapping.put(entry.getKey(), role);
                }
            }
        }
    }


    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof MinosUserDetails)) {
            super.handle(request, response, authentication);

            return;
        }

        User user = ((MinosUserDetails) principal).getUser();

        for (Entry<String, String> entry : mapping.entries()) {
            if (user.hasRole(entry.getValue())) {
                getRedirectStrategy().sendRedirect(request, response, entry.getKey());

                return;
            }
        }

        super.handle(request, response, authentication);
    }
}
