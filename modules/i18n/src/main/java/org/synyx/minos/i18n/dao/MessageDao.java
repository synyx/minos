/**
 * 
 */
package org.synyx.minos.i18n.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;


/**
 * Dao-Interface to manage {@link Message}s.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageDao extends GenericDao<Message, Long> {

    @Query("select m from Message m where m.basename = ?1 and m.locale.language = ?2 and m.locale.country = ?3 and m.locale.variant = ?4 order by m.key")
    List<Message> findByBasenameAndLanguageAndCountryAndVariant(String basename, String language, String country,
            String variant);


    @Query("select m from Message m where m.basename = ?1 and m.locale.language = ?2 and m.locale.country = ?3 and m.locale.variant = ?4 and m.key = ?5")
    List<Message> findByBasenameAndLanguageAndCountryAndVariantAndKey(String basename, String language, String country,
            String variant, String key);


    List<Message> findByBasenameAndLocale(String basename, LocaleWrapper locale);


    @Query("select distinct m.key from Message m where m.basename = ?1")
    List<String> getKnownKeys(String basename);


    @Query("select distinct m.basename from Message m")
    List<String> findBasenames();


    @Query("select distinct m.locale from Message m where m.basename = ?1")
    List<LocaleWrapper> findLocales(String basename);
}
