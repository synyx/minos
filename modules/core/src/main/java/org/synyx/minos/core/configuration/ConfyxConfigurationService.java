package org.synyx.minos.core.configuration;

import org.synyx.minos.core.domain.User;

import com.synyx.confyx.Confyx;
import com.synyx.confyx.StandardSubject;
import com.synyx.confyx.Subject;
import com.synyx.confyx.exception.ConfyxPolicyViolationException;
import com.synyx.confyx.family.Family;


/**
 * Confyx based implementation of {@link ConfigurationService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ConfyxConfigurationService implements ConfigurationService {

    private Confyx confyx;


    /**
     * @param repository the repository to set
     */
    public void setConfyx(Confyx confyx) {

        this.confyx = confyx;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.confyx.ConfigurationService#getConfigValue(java.
     * lang.String, com.synyx.minos.core.domain.User)
     */
    public <T> T getConfigValue(String config, User user) {

        Family<T> family = confyx.getFamily(config);

        return family.getDefaultMember(toConfigUser(user));

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.confyx.ConfigurationService#setConfigValue(java.
     * lang.String, com.synyx.minos.core.domain.User, T)
     */
    public <T> void setConfigValue(String config, User user, T value) {

        Family<T> family = confyx.getFamily(config);

        try {
            family.saveDefaultMember(toConfigUser(user), value);
        } catch (ConfyxPolicyViolationException e) {
            // FIXME How to react on policy violations
            e.printStackTrace();
        }
    }


    /**
     * Returns the {@link ConfigUserBean} for the given {@link User}.
     * 
     * @param user
     * @return
     */
    private Subject toConfigUser(User user) {

        return new StandardSubject(user.getId().toString());
    }
}
