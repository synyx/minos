#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.items.dao;

import org.synyx.hades.dao.GenericDao;

import ${package}.items.domain.TodoItem;


/*
 * This is our example DAO. It uses Hades for automatic generation of an implementation during context spring creation.
 */
public interface TodoDao extends GenericDao<TodoItem, Long> { }
