/**
 * 
 */
package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.LocaleWrapper;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class LocaleInformation {

    private LocaleWrapper locale;

    private Long countNew;
    private Long countUpdated;
    private Long countTotal;


    public LocaleInformation(LocaleWrapper locale, Long countNew, Long countUpdated, Long countTotal) {

        super();
        this.locale = locale;
        this.countNew = countNew;
        this.countUpdated = countUpdated;
        this.countTotal = countTotal;
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

        return countTotal - countUpdated - countNew;
    }

}
