/**
 *
 */
package org.synyx.minos.i18n.service;

import org.synyx.minos.i18n.domain.Message;

import java.io.OutputStream;


/**
 * Service-Interface for transfering {@link Message}s from and to database.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageTransferService {

    void initializeMessageSources();


    void importMessages();


    void exportMessages(OutputStream stream);
}
