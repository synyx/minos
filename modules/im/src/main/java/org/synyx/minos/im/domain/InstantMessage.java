package org.synyx.minos.im.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;



/**
 * Domain class for instant messages. This is a message a {@link User} can send
 * another {@link User} that does not leave the system at all.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class InstantMessage extends AbstractAuditable<User, Long> implements
        Comparable<InstantMessage> {

    private static final long serialVersionUID = -3539274426976828698L;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receipient;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @Column(name = "isread")
    private Boolean read = false;
    private Boolean inInbox = true;
    private Boolean inOutbox = true;


    /**
     * Empty constructor required by JPA.
     */
    public InstantMessage() {

        super();
    }


    /**
     * Creates a new {@link InstantMessage} with the least possible data.
     * 
     * @param receipient
     * @param text
     */
    public InstantMessage(User receipient, String text) {

        this.receipient = receipient;
        this.text = text;
    }


    /**
     * Returns the sender of the message.
     * 
     * @return the sender
     */
    public User getSender() {

        return sender;
    }


    /**
     * Sets the sender of the message.
     * 
     * @param sender the sender to set
     */
    public void setSender(User sender) {

        this.sender = sender;
    }


    /**
     * Returns the receipient of the message.
     * 
     * @return the receipient
     */
    public User getReceipient() {

        return receipient;
    }


    /**
     * @param receipient the receipient to set
     */
    public void setReceipient(User receipient) {

        Assert.notNull(receipient, "Receipient must not be null!");
        this.receipient = receipient;
    }


    /**
     * @return the message
     */
    public String getText() {

        return text;
    }


    /**
     * @param text the text to set
     */
    public void setText(String text) {

        Assert.notNull(text, "Text must not be null!");
        this.text = text;
    }


    /**
     * Returns the date, the message was sent.
     * 
     * @return the sendDate
     */
    public DateTime getSendDate() {

        return null == sendDate ? null : new DateTime(sendDate);
    }


    /**
     * Sets the date the message was send.
     * 
     * @param sendDate the sendDate to set
     */
    public void setSendDate(DateTime sendDate) {

        this.sendDate = null == sendDate ? null : sendDate.toDate();
    }


    /**
     * Returns whether the message has
     * 
     * @return the read
     */
    public Boolean isRead() {

        return read;
    }


    /**
     * @param read the read to set
     */
    public void isRead(Boolean read) {

        this.read = read;
    }


    /**
     * @return the inInbox
     */
    public Boolean isInInbox() {

        return inInbox;
    }


    /**
     * @param inInbox the inInbox to set
     */
    public void setInInbox(Boolean inInbox) {

        this.inInbox = inInbox;
    }


    /**
     * @return the inOutbox
     */
    public Boolean isInOutbox() {

        return inOutbox;
    }


    /**
     * @param inOutbox the inOutbox to set
     */
    public void setInOutbox(Boolean inOutbox) {

        this.inOutbox = inOutbox;
    }


    public void send() {

        this.sendDate = new Date();
        this.inInbox = true;
        this.inOutbox = true;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(InstantMessage that) {

        if (null == this.getSendDate() && null == that.getSendDate()) {
            return 0;
        }

        if (null == this.getSendDate()) {
            return -1;
        }

        if (null == that.getSendDate()) {
            return 1;
        }

        return this.getSendDate().compareTo(that.getSendDate());
    }
}
