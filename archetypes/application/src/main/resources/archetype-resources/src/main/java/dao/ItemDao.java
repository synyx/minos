#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import org.synyx.hades.dao.GenericDao;

import ${package}.domain.Item;


/*
 * This is our example DAO. It uses Hades for automatic generation of an implementation during context spring creation.
 */
public interface ItemDao extends GenericDao<Item, Long> { }
