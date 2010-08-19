/**
 * 
 */
package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.LocaleWrapper;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class LocaleInformation {

    private String basename;

    private LocaleWrapper locale;

    private Long countNew;
    private Long countUpdated;
    private Long countTotal;


    public LocaleInformation(String basename, LocaleWrapper locale, Long countNew, Long countUpdated, Long countTotal) {

        super();
        this.locale = locale;
        this.countNew = countNew;
        this.countUpdated = countUpdated;
        this.countTotal = countTotal;
        this.basename = basename;
    }


    public LocaleWrapper getLocale() {

        return locale;
    }


    public Long getCountNew() {

        return countNew;
    }


    public Long getCountUpdated() {

        return countUpdated;
    }


    public Long getCountTotal() {

        return countTotal;
    }


    public Long getCountUnchanged() {

        return Math.max(0, countTotal - countUpdated - countNew);
    }


    public String getBasename() {

        return basename;
    }

}
