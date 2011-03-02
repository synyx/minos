//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2008.12.18 at 05:32:54 PM CET
//

package org.synyx.minos.umt.remoting.rest.dto;

import org.synyx.minos.core.remoting.rest.dto.AbstractEntityDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.synyx.de/minos/core/rest}abstract-entity&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;firstname&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;lastname&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;emailAddress&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *         &lt;element name=&quot;username&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "firstname", "lastname", "emailAddress", "username" })
@XmlRootElement(name = "user")
public class UserDto extends AbstractEntityDto {

    protected String firstname;
    protected String lastname;
    @XmlElement(required = true)
    protected String emailAddress;
    @XmlElement(required = true)
    protected String username;

    /**
     * Gets the value of the firstname property.
     *
     * @return possible object is {@link String }
     */
    public String getFirstname() {

        return firstname;
    }


    /**
     * Sets the value of the firstname property.
     *
     * @param value allowed object is {@link String }
     */
    public void setFirstname(String value) {

        this.firstname = value;
    }


    /**
     * Gets the value of the lastname property.
     *
     * @return possible object is {@link String }
     */
    public String getLastname() {

        return lastname;
    }


    /**
     * Sets the value of the lastname property.
     *
     * @param value allowed object is {@link String }
     */
    public void setLastname(String value) {

        this.lastname = value;
    }


    /**
     * Gets the value of the emailAddress property.
     *
     * @return possible object is {@link String }
     */
    public String getEmailAddress() {

        return emailAddress;
    }


    /**
     * Sets the value of the emailAddress property.
     *
     * @param value allowed object is {@link String }
     */
    public void setEmailAddress(String value) {

        this.emailAddress = value;
    }


    /**
     * Gets the value of the username property.
     *
     * @return possible object is {@link String }
     */
    public String getUsername() {

        return username;
    }


    /**
     * Sets the value of the username property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUsername(String value) {

        this.username = value;
    }
}
