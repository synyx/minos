package org.synyx.minos.skillz.domain.resume;

/**
 * Class for describing {@link ResumeFilter} parameters.
 * 
 * @author Markus Knittig - knittig@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ResumeFilterParameter {

    private final String name;
    private final String messageKey;
    private final Class<?> type;


    /**
     * Creates a {@link ResumeFilterParameter} with the given name and type. The name will also be used as resource
     * bundle key for i18n lookup.
     * 
     * @param name
     * @param type
     */
    public ResumeFilterParameter(String name, Class<?> type) {

        this(name, type, name);
    }


    /**
     * Creates a new {@link ResumeFilterParameter} with the given name, type and an resource bundle key to lookup it's
     * name for i18n.
     * 
     * @param name
     * @param type
     * @param messageKey
     */
    public ResumeFilterParameter(String name, Class<?> type, String messageKey) {

        this.name = name;
        this.type = type;
        this.messageKey = messageKey;
    }


    /**
     * @return the name
     */
    public String getName() {

        return name;
    }


    /**
     * @return the type
     */
    public Class<?> getType() {

        return type;
    }


    /**
     * @return the messageKey
     */
    public String getMessageKey() {

        return messageKey;
    }

}
