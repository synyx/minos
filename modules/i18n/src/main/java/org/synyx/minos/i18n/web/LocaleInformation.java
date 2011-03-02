/**
 *
 */
package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.util.Assert;


/**
 * View-Bean around {@link AvailableLanguage} that holds more information (how many keys need to be translatet etc).
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class LocaleInformation {

    private AvailableLanguage language;

    private Long countNew;
    private Long countUpdated;
    private Long countTotal;

    /**
     * Creates a new instance
     *
     * @param language the {@link AvailableLanguage}
     * @param countNew the count of new translations
     * @param countUpdated the count of updated translations
     * @param countTotal the total count
     */
    public LocaleInformation(AvailableLanguage language, Long countNew, Long countUpdated, Long countTotal) {

        Assert.notNull(language);

        this.language = language;
        this.countNew = countNew;
        this.countUpdated = countUpdated;
        this.countTotal = countTotal;
    }

    public boolean isDeletable() {

        return !language.getLocale().isDefault();
    }


    public LocaleWrapper getLocale() {

        return language.getLocale();
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

        return language.getBasename();
    }


    public boolean isRequired() {

        return language.isRequired();
    }


    public AvailableLanguage getLanguage() {

        return language;
    }
}
