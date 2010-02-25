package org.synyx.minos.calendar.web;

import org.joda.time.DateTime;
import org.synyx.minos.core.domain.TimePeriod;



/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public enum CalendarView {

    DAY {

        @Override
        public TimePeriod getPeriod() {

            return TimePeriod.allDay();
        }


        @Override
        public TimePeriod getPeriod(DateTime dateTime) {

            return TimePeriod.allDay(dateTime);
        }

    },

    /**
     * Represents the weekly view. Considers the current week by default.
     */
    WEEK {

        @Override
        public TimePeriod getPeriod() {

            return TimePeriod.allWeek();
        }


        @Override
        public TimePeriod getPeriod(DateTime date) {

            return TimePeriod.allWeek(date);
        }

    },
    MONTH {

        @Override
        public TimePeriod getPeriod() {

            return TimePeriod.allMonth();
        }


        @Override
        public TimePeriod getPeriod(DateTime date) {

            return TimePeriod.allMonth(date);
        }

    };

    /**
     * Returns the time period for the current date and time.
     * 
     * @return
     */
    public abstract TimePeriod getPeriod();


    /**
     * Returns the time period for the given date and time.
     * 
     * @param dateTime
     * @return
     */
    public abstract TimePeriod getPeriod(DateTime dateTime);
}
