/**
 *
 */
package org.synyx.minos.i18n.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;

import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.MessageStatus;
import org.synyx.minos.i18n.domain.MessageTranslation;

import java.util.List;


/**
 * Dao interface for {@link MessageTranslation}
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageTranslationDao extends GenericDao<MessageTranslation, Long> {

    @Modifying
    @Query("delete from MessageTranslation t WHERE t.availableMessage = ?1")
    void deleteBy(AvailableMessage availableMessage);


    @Modifying
    @Query("delete from MessageTranslation t WHERE t.availableLanguage = ?1")
    void deleteByAvailableLanguage(AvailableLanguage language);


    @Modifying
    @Query("delete from MessageTranslation t WHERE t.availableMessage = ?1 and t.availableLanguage = ?2")
    void deleteBy(AvailableMessage availableMessage, AvailableLanguage availableLanguage);


    @Query(
        "select count(t) from MessageTranslation t join t.availableLanguage l join t.availableMessage m where m.basename = ?1 and l.locale = ?2 and t.messageStatus = ?3"
    )
    Long countByStatus(String basename, LocaleWrapper locale, MessageStatus status);


    MessageTranslation findByAvailableMessageAndAvailableLanguage(AvailableMessage availableMessage,
        AvailableLanguage lang);


    List<MessageTranslation> findByAvailableLanguage(AvailableLanguage language);
}
