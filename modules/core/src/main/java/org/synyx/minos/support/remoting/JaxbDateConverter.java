package org.synyx.minos.support.remoting;

import org.joda.time.DateTime;

import javax.xml.bind.DatatypeConverter;


/**
 * Converter class that can be used to marshal {@link DateTime} instances to XML. Declare this in your JAXB binding
 * customization file.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class JaxbDateConverter {

    /**
     * Parses an XML dateTime string to an {@link DateTime}.
     *
     * @param s
     * @return
     */
    public static DateTime parseDate(String s) {

        return new DateTime(DatatypeConverter.parseDateTime(s).getTime());
    }


    /**
     * Creates the XML string representation of an {@link DateTime}.
     *
     * @param dateTime
     * @return
     */
    public static String printDate(DateTime dateTime) {

        if (null == dateTime) {
            return null;
        }

        return DatatypeConverter.printDateTime(dateTime.toGregorianCalendar());
    }
}
