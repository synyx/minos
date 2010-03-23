package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.base.BaseDateTime;
import org.joda.time.base.BaseLocal;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * {@link PropertyEditor} to turn web request parameters into {@link DateTime}
 * or {@link DateMidnight} instances. Allows configuring additional parsing
 * patterns to be tried on {@link String} to {@link DateTime}/
 * {@link DateMidnight} conversion to be more lenient on the incoming way.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class DateTimeEditor extends PropertyEditorSupport {

    private static final Log LOG = LogFactory.getLog(DateTimeEditor.class);
    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormat.mediumDateTime();

    private DateTimeFormatter formatter;
    private List<DateTimeFormatter> additionalParsers;
    private boolean toDateMidnight;
    private boolean toLocalTime;


    /**
     * Creates a new {@link DateTimeEditor} with default formatter of
     * {@value #DEFAULT_FORMATTER}.
     */
    public DateTimeEditor(Locale locale) {

        this(DEFAULT_FORMATTER.withLocale(locale));
    }


    /**
     * Creates a new {@link DateTimeEditor} for the given {@link Locale} and
     * {@link String} pattern.
     * 
     * @param locale
     * @param pattern
     */
    public DateTimeEditor(Locale locale, String pattern) {

        this(DateTimeFormat.forPattern(pattern).withLocale(locale));
    }


    /**
     * Creates a new {@link DateTimeEditor}. If {@code null} is provided as
     * {@code formatter} a default formatter of {@value #DEFAULT_FORMATTER} will
     * be used.
     * 
     * @param formatter
     */
    public DateTimeEditor(DateTimeFormatter formatter) {

        this.formatter = null == formatter ? DEFAULT_FORMATTER : formatter;
        this.additionalParsers = new ArrayList<DateTimeFormatter>();
    }


    /**
     * Enables the editor to return a {@link DateMidnight} over a
     * {@link DateTime} on parsing. This will automagically drop any prior
     * settings of parsing to {@link LocalTime}.
     * 
     * @see #forLocalTime()
     * @return
     */
    public DateTimeEditor forDateMidnight() {

        this.toDateMidnight = true;
        this.toLocalTime = false;
        return this;
    }


    /**
     * Enables the editor to return a {@link LocalTime} over a {@link DateTime}
     * on parsing. This will automagically drop prior settings of parsing to
     * {@link DateMidnight}.
     * 
     * @see #forDateMidnight()
     * @return
     */
    public DateTimeEditor forLocalTime() {

        this.toLocalTime = true;
        this.toDateMidnight = false;
        return this;
    }


    /**
     * Allows to register additional parsing patterns to act more lenient on
     * parsing incoming {@link String} values.
     * 
     * @param patterns
     * @return
     */
    public DateTimeEditor withAdditionalParsersFor(String... patterns) {

        for (String pattern : patterns) {
            this.additionalParsers.add(DateTimeFormat.forPattern(pattern));
        }
        return this;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {

        if (getValue() != null) {
            if (toLocalTime) {
                return ((BaseLocal) getValue()).toString(formatter);
            } else {
                return ((BaseDateTime) getValue()).toString(formatter);
            }
        }

        return "";
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        List<DateTimeFormatter> parsers = new ArrayList<DateTimeFormatter>();
        parsers.add(formatter);
        parsers.addAll(additionalParsers);

        for (DateTimeFormatter parser : parsers) {
            try {
                DateTime result = parser.parseDateTime(text);

                if (toDateMidnight) {
                    setValue(result.toDateMidnight());
                } else if (toLocalTime) {
                    setValue(result.toLocalTime());
                } else {
                    setValue(result);
                }

                return;
            } catch (IllegalArgumentException e) {
                // Skip and try next one
            }
        }

        LOG.warn(String.format("Couldn't parse date from '%s'!", text));
        setValue(null);
    }
}
