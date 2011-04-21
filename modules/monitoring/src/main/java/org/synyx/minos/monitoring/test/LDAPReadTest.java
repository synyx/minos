package org.synyx.minos.monitoring.test;

import org.springframework.ldap.core.LdapTemplate;

import org.synyx.minos.monitoring.service.MonitoringTest;
import org.synyx.minos.monitoring.service.MonitoringTestResult;

import java.util.List;


/**
 * Tests a simple read from the LDAP.
 *
 * @author  Marc Kannegießer - kannegiesser@synyx.de
 */
public class LDAPReadTest extends MonitoringTest {

    private static final String DEFAULT_DESCRIPTION =
        "Tests connection to LDAP-Server by reading the expected ou=Contacts node.";
    private static final String DEFAULT_NAME = "LDAP-READ";

    private LdapTemplate ldapTemplate;

    public LDAPReadTest(LdapTemplate ldapTemplate) {

        super(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public MonitoringTestResult execute() {

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
