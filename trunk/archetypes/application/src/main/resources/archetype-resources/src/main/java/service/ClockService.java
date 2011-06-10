#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import org.joda.time.DateTime;

import org.springframework.stereotype.Service;


/*
 * A simple service class that gets injected into our controller
 */
@Service
public class ClockService {

    public DateTime getCurrentTime() {

        return new DateTime();
    }
}
