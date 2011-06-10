#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.ResourceBundle;


public enum Status {

    NEW("status.new"),
    IN_PROGRESS("status.inProgress"),
    DONE("status.done");

    private final String messageKey;
    private String message;

    private Status(String message) {

        this.messageKey = message;
    }

    public String getMessageKey() {

        return messageKey;
    }


    public String getMessage() {

        if (message == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("META-INF/minos/items/messages");
            message = resourceBundle.getObject(messageKey).toString();
        }

        return message;
    }
}
