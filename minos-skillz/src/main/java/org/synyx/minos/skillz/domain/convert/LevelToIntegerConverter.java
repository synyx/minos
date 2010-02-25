package org.synyx.minos.skillz.domain.convert;

import org.springframework.core.convert.converter.Converter;
import org.synyx.minos.skillz.domain.Level;


/**
 * Converts a {@link Level} to a {@link Integer} by returning its ordinal.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class LevelToIntegerConverter implements Converter<Level, Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang
     * .Object)
     */
    @Override
    public Integer convert(Level source) {

        return source.getOrdinal();
    }

}
