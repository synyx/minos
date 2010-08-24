/**
 * 
 */
package org.synyx.minos.i18n.dao;

import java.util.List;
import java.util.Locale;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;


/**
 * Dao-Interface to manage {@link Message}s.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageDao extends GenericDao<Message, Long> {

    /**
     * Returns all {@link Message}s that match the given parameters.
     * 
     * @param basename the basename
     * @param language the langauge-part of {@link Locale}
     * @param country the country-part of {@link Locale}
     * @param variant the variant-part of {@link Locale}
     * @return all {@link Message}s that match the given parameters.
     */
    @Query("select m from Message m where m.basename = ?1 and m.locale.language = ?2 and m.locale.country = ?3 and m.locale.variant = ?4 order by m.key")
    List<Message> findByBasenameAndLanguageAndCountryAndVariant(String basename, String language, String country,
            String variant);


    /**
     * Returns all {@link Message}s that match the given parameters.
     * 
     * @param basename the basename
     * @param locale the {@link LocaleWrapper}
     * @return all {@link Message}s that match the given parameters.
     */
    List<Message> findByBasenameAndLocale(String basename, LocaleWrapper locale);


    /**
     * Returns all {@link Message}s that match the given parameters. Not that this should actually return only one
     * message but since some databases might return results for string-constraints case-insensitive there might be a
     * key "AA" and one "aA" be returned when queried for a key "aa".
     * 
     * @param basename the basename
     * @param language the langauge-part of {@link Locale}
     * @param country the country-part of {@link Locale}
     * @param variant the variant-part of {@link Locale}
     * @param key the key of the message
     * @return all {@link Message}s that match the given parameters.
     */
    @Query("select m from Message m where m.basename = ?1 and m.locale.language = ?2 and m.locale.country = ?3 and m.locale.variant = ?4 and m.key = ?5")
    List<Message> findByBasenameAndLanguageAndCountryAndVariantAndKey(String basename, String language, String country,
            String variant, String key);


    /**
     * Returns all {@link Message}s that match the given parameters. Not that this should actually return only one
     * message but since some databases might return results for string-constraints case-insensitive there might be a
     * key "AA" and one "aA" be returned when queried for a key "aa".
     * 
     * @param basename the basename
     * @param locale the {@link LocaleWrapper}
     * @param key the key of the message
     * @return all {@link Message}s that match the given parameters.
     */
    List<Message> findByBasenameAndLocaleAndKey(String basename, LocaleWrapper locale, String key);


    /**
     * Return all Keys known for the given basename
     * 
     * @param basename the basename to return keys for
     * @return all keys.
     */
    @Query("select distinct m.key from Message m where m.basename = ?1")
    List<String> getKnownKeys(String basename);


    /**
     * Return all basenames known
     * 
     * @return all basenames
     */
    @Query("select distinct m.basename from Message m")
    List<String> findBasenames();


    /**
     * Returns all langauges existing for the given basename
     * 
     * @param basename the basename
     * @return a {@link List} of {@link LocaleWrapper}
     */
    @Query("select distinct m.locale from Message m where m.basename = ?1")
    List<LocaleWrapper> findLocales(String basename);


    /**
     * Removes a given key
     * 
     * @param basename
     * @param key
     */
    // TODO fix this
    @Modifying
    @Query("delete from Message m where m.basename = ?1 and m.key = ?2")
    void deleteBy(String basename, String key);


    /**
     * Removes all messages of the given basename and locale (language)
     * 
     * @param basename
     * @param locale
     */
    @Modifying
    @Query("delete from Message m where m.basename = ?1 and m.locale = ?2")
    void deleteBy(String basename, LocaleWrapper locale);


    /**
     * Returns the number of {@link Message}s that exist for the given basename and locale
     * 
     * @param basename
     * @param locale
     * @return
     */
    @Query("select count(m) from Message m where m.basename = ?1 and m.locale = ?2")
    Long countByBasenameAndLocale(String basename, LocaleWrapper locale);

}
