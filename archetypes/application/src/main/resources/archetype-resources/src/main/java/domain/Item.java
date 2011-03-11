#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import org.synyx.minos.core.domain.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity
public class Item extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -8172200297371920477L;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

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
