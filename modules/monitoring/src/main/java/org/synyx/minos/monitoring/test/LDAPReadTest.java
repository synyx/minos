package org.synyx.minos.monitoring.test;

import org.springframework.ldap.core.LdapTemplate;

import org.synyx.minos.monitoring.service.MonitoringTest.MonitoringTestCommand;
import org.synyx.minos.monitoring.service.MonitoringTestResult;

import java.util.List;


/**
 * Tests a simple read from the LDAP.
 *
 * @author  Marc Kannegießer - kannegiesser@synyx.de
 */
public class LDAPReadTest implements MonitoringTestCommand {

    private LdapTemplate ldapTemplate;

    public LDAPReadTest(LdapTemplate ldapTemplate) {

        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public MonitoringTestResult getResult() {

        @SuppressWarnings("unchecked")
        List<String> existing = ldapTemplate.listBindings("");

        for (String e : existing) {
            if ("ou=Contacts".equals(e)) {
                return MonitoringTestResult.success("Successfull read ou=Contacts from LDAP.");
            }
        }

        return MonitoringTestResult.error("Could not read ou=Contacts from LDAP.");
    }
}
