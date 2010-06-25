package org.synyx.minos.test.integration;

import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.xml.XmlConfiguration;


public class AbstractJUnit4JettyIntegrationTest {

    private static final String DEFAULT_JETTY_CONFIG = "jetty.xml";

    private static Server server;


    /**
     * Returns the location of jetty server configuration file. Defaults to
     * {@code jetty.xml}.
     * 
     * @return
     */
    protected static String getJettyConfigLocation() {

        return DEFAULT_JETTY_CONFIG;
    }


    /**
     * Starts Jetty Server.
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void startServer() throws Exception {

        server = new Server();

        URL config =
                AbstractJUnit4JettyIntegrationTest.class.getClassLoader()
                        .getResource(getJettyConfigLocation());

        new XmlConfiguration(config).configure(server);
        server.start();
    }


    /**
     * Shuts down Jetty Server.
     * 
     * @throws Exception
     */
    @AfterClass
    public static void stopServer() throws Exception {

        server.stop();
    }
}