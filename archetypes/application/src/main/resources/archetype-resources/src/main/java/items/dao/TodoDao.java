#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.items.dao;

import org.synyx.hades.dao.GenericDao;

import ${package}.items.domain.TodoItem;


public interface TodoDao extends GenericDao<TodoItem, Long> { }
