#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.items.domain;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import org.synyx.minos.core.domain.User;

import javax.persistence.Entity;


@Entity
public class TodoItem extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -8172200297371920477L;

    private String description;
    private boolean done;

    public String getDescription() {

        return description;
    }


    public void setDescription(String description) {

        this.description = description;
    }


    public void setDone(boolean done) {

        this.done = done;
    }


    public boolean isDone() {

        return done;
    }
}
