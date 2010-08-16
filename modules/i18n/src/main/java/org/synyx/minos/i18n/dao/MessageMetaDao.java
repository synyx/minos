package org.synyx.minos.i18n.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.minos.i18n.domain.MessageMeta;


/**
 * @author Alexander Menz - menz@synyx.de
 */
public interface MessageMetaDao extends GenericDao<MessageMeta, Long> {

    List<MessageMeta> findByBasename(String basename);


    MessageMeta findByBasenameAndKey(String basename, String key);

}
