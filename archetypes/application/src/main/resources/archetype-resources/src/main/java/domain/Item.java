#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import org.synyx.minos.core.domain.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/*
 * This entity is a very simple example with already lot of functionality built in. It makes use of Minos builtin
 * auditing support by extending AbstractAuditable. This enables the framework to automatically  add the current user
 * and date of change to modified entities on save.
 */
@Entity
public class Item extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -8172200297371920477L;

    private String description;

    /*
     * Enums in entities should always be annotated this way. The default is to save the enum in an integer column in
     * the database. A string based column is more robust against changing enums and the definition order of their
     * allowed values.
     */
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    /*
     * JPA/Hibernate needs a default constructor to be present. To hide it from client code, the constructor can be
     * declared protected, which leaves it yet accessible for JPA providers.
     */
    protected Item() {
    }


    public Item(String description) {

        this.description = description;
    }

    public String getDescription() {

        return description;
    }


    public void setDescription(String description) {

        this.description = description;
    }


    public void setStatus(Status status) {

        this.status = status;
    }


    public Status getStatus() {

        return status;
    }
}
