package org.synyx.minos.core.web;

import java.io.Serializable;

import org.springframework.context.MessageSourceResolvable;


/**
 * Representation of a system message to be displayed in the user interface.
 * Implements {@code MessageSourceResolvable} to be easily localized via Springs
 * {@code MessageSource} means.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class Message implements Serializable, MessageSourceResolvable {

    private static final long serialVersionUID = -8019010828845066017L;

    private static final String ERROR_CSS = "systemMessage errorMessage";
    private static final String SUCCESS_CSS = "systemMessage successMessage";

    private boolean isErrorMessage;

    private String key;
    private Object[] arguments;


    /**
     * Creates a new message. Message with a key and parameters and the css
     * class to be used. Private to enforce instantiation via static methods.
     * 
     * @param isErrorMessage
     * @param key the key to lookup the localized message string
     * @param arguments possibly available arguments to add to the localized
     *            message
     */
    private Message(boolean isErrorMessage, String key, Object... arguments) {

        this.key = key;
        this.isErrorMessage = isErrorMessage;
        this.arguments = arguments;
    }


    /**
     * Creates an error message with the given key.
     * 
     * @param key
     * @return
     */
    public static Message error(String key) {

        return error(key, (Object) null);
    }


    /**
     * Creates an error message with the given key and arguments.
     * 
     * @param key
     * @param arguments
     * @return
     */
    public static Message error(String key, Object... arguments) {

        return new Message(true, key, arguments);
    }


    /**
     * Creates a success message with the given key.
     * 
     * @param key
     * @return
     */
    public static Message success(String key) {

        return success(key, (Object) null);
    }


    /**
     * Creates a success message with the given key and arguments.
     * 
     * @param key
     * @param arguments
     * @return
     */
    public static Message success(String key, Object... arguments) {

        return new Message(false, key, arguments);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceResolvable#getArguments()
     */
    public Object[] getArguments() {

        return arguments;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceResolvable#getCodes()
     */
    public String[] getCodes() {

        return new String[] { key };
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.MessageSourceResolvable#getDefaultMessage()
     */
    public String getDefaultMessage() {

        return null;
    }


    /**
     * The CSS class to be used to render this message.
     * 
     * @return the cssClass
     */
    public String getCssClass() {

        return isErrorMessage ? ERROR_CSS : SUCCESS_CSS;
    }


    /**
     * Returns whether the message is an error message or not.
     * 
     * @return
     */
    public boolean isErrorMessage() {

        return isErrorMessage;
    }

}
