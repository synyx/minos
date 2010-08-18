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

    List<AvailableLanguage> findByBasename(String basename);


    AvailableLanguage findByBasenameAndLocale(String basename, LocaleWrapper localeWrapper);

}
