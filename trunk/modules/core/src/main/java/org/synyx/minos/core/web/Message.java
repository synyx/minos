package org.synyx.minos.core.web;

import java.io.Serializable;

import org.springframework.context.MessageSourceResolvable;


/**
 * Representation of a system message to be displayed in the user interface. Implements {@code MessageSourceResolvable}
 * to be easily localized via Springs {@code MessageSource} means.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class Message implements Serializable, MessageSourceResolvable {

    private static final long serialVersionUID = -8019010828845066017L;

    private MessageType messageType;

    private String key;
    private Object[] arguments;


    /**
     * Creates a new message. Message with a key and parameters and the css class to be used. Private to enforce
     * instantiation via static methods.
     * 
     * @param messageType the type of this
     * @param key the key to lookup the localized message string
     * @param arguments possibly available arguments to add to the localized message
     */
    private Message(MessageType messageType, String key, Object... arguments) {

        this.key = key;
        this.arguments = arguments;
        this.messageType = messageType;
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

        return new Message(MessageType.ERROR, key, arguments);
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

        return new Message(MessageType.SUCCESS, key, arguments);
    }


    /**
     * Creates a notice message with the given key.
     * 
     * @param key
     * @return
     */
    public static Message notice(String key) {

        return notice(key, (Object) null);
    }


    /**
     * Creates a notice message with the given key and arguments.
     * 
     * @param key
     * @param arguments
     * @return
     */
    public static Message notice(String key, Object... arguments) {

        return new Message(MessageType.NOTICE, key, arguments);
    }


    /**
     * Creates a warning message with the given key.
     * 
     * @param key
     * @return
     */
    public static Message warning(String key) {

        return warning(key, (Object) null);
    }


    /**
     * Creates a warning message with the given key and arguments.
     * 
     * @param key
     * @param arguments
     * @return
     */
    public static Message warning(String key, Object... arguments) {

        return new Message(MessageType.WARNING, key, arguments);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceResolvable#getArguments()
     */
    public Object[] getArguments() {

        return arguments;
    }


    /**
     * Returns true if this is an error-message
     * 
     * @return
     */
    public boolean isErrorMessage() {

        return messageType.isErrorMessage();
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
     * @see org.springframework.context.MessageSourceResolvable#getDefaultMessage()
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

        return messageType.getCss();
    }

    /**
     * Enum that represents the differend {@link MessageType}s
     * 
     * @author Marc Kannegiesser - kannegiesser@synyx.de
     */
    private enum MessageType {

        ERROR("systemMessage errorMessage", true), SUCCESS("systemMessage successMessage"), WARNING(
                "systemMessage warningMessage"), NOTICE("systemMessage noticeMessage");

        private String css;

        private boolean isError;


        private MessageType(String css) {

            this(css, false);
        }


        private MessageType(String css, boolean isError) {

            this.css = css;
            this.isError = isError;
        }


        /**
         * @return
         */
        public boolean isErrorMessage() {

            return isError;
        }


        /**
         * Returns the cssClass content that should be applied to the corresponding {@link MessageType}
         * 
         * @return String with cssClass content
         */
        public String getCss() {

            return css;
        }

    }

}
