#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="todos" tagdir="/WEB-INF/tags/todos"%>

<c:set var="editMessage"><spring:message code="sample.edit" /></c:set>
<c:set var="deleteMessage"><spring:message code="sample.delete" /></c:set>
<spring:url value="/styles" var="stylesheets" />

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><spring:message code="todos.list.title" /></title>
    <link type="text/css" href="${symbol_dollar}{stylesheets}/style.css" rel="stylesheet" title="default" />
</head>
<body>
<h1><spring:message code="todos.list.title" /></h1>
<display:table id="todo" name="todos" requestURI="" defaultsort="1" pagesize="10" sort="list">
	<minos:column titleKey="todos.item.createdBy" property="createdBy.username" />
	<minos:column titleKey="todos.item.description" property="description" />
	<minos:column titleKey="todos.item.createdDate" property="createdDate" />
	<minos:column titleKey="todos.item.done">
		<todos:isDone value="${symbol_dollar}{todo.done}" />
	</minos:column>
	<minos:column>
		<a class="editlink" href="todos/${symbol_dollar}{todo.id}">${symbol_dollar}{editMessage}</a>
		<form:form method="delete" action="todos/${symbol_dollar}{todo.id}">
			<input type="submit" value="${symbol_dollar}{deleteMessage}" />
		</form:form>
	</minos:column>
</display:table>
</body>
