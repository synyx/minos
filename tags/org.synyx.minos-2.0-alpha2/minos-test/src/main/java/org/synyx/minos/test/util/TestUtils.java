package org.synyx.minos.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Utility class for common test helpers.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class TestUtils {

    /**
     * Loads a resource as a {@link String}.
     * 
     * @see ClassLoader#getResourceAsStream(String)
     */
    public static String loadResourceAsString(String name) throws IOException {

        InputStream inputStream = TestUtils.class.getResourceAsStream(name);
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }


    /**
     * Loads a resource as a {@link File}.
     * 
     * @see ClassLoader#getResourceAsStream(String)
     */
    public static File loadResourceAsFile(String name) throws IOException {

        return new File(TestUtils.class.getResource(name).getFile());
    }

}
