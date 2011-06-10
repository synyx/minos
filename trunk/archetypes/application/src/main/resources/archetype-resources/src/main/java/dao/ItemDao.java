#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import java.util.List;
import org.synyx.hades.dao.GenericDao;

import ${package}.domain.Item;
import ${package}.domain.Status;


/*
 * This is our example DAO. It uses Hades for automatic generation of an implementation during context spring creation.
 */
public interface ItemDao extends GenericDao<Item, Long> { 
    
    /**
     * Returns all existing {@link Item}s by {@link Status}
     *
     * @param status the {@link Status} to return items for
     * @return all existing {@link Item}s of given {@link Status}
     */
    List<Item> findByStatus(Status status);
    
}
