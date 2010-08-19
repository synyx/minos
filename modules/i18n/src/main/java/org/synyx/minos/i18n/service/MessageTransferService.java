/**
 * 
 */
package org.synyx.minos.i18n.service;

import java.io.OutputStream;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageTransferService {

    void initializeMessageSources();


    void importMessages();


    void exportMessages(OutputStream stream);

}
