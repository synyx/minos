/**
 * 
 */
package org.synyx.minos.i18n.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.LocaleWrapper;


/**
 * Dao interface for {@link AvailableLanguage}
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface AvailableLanguageDao extends GenericDao<AvailableLanguage, Long> {

    /**
     * Returns all {@link AvailableLanguage} existing for the given Basename (meaning: All langauges that exist for the
     * given basename).
     * 
     * @param basename the basename to return langauges for.
     * @return a {@link List} of {@link AvailableLanguage}s existing for the given basename
     */
    List<AvailableLanguage> findByBasename(String basename);


    /**
     * Returns a {@link AvailableLanguage} by its basename and {@link LocaleWrapper}
     * 
     * @param basename the basename to receive {@link AvailableLanguage} for
     * @param localeWrapper the {@link LocaleWrapper} to reveive {@link AvailableLanguage} for
     * @return the {@link AvailableLanguage} matching given basename and {@link LocaleWrapper} or null
     */
    AvailableLanguage findByBasenameAndLocale(String basename, LocaleWrapper localeWrapper);

}
