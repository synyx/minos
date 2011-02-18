#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<spring:url value="/styles" var="stylesheets" />
<spring:url value="/web/todos" var="formURL" />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><spring:message code="todos.create.title" /></title>
    <link type="text/css" href="${symbol_dollar}{stylesheets}/style.css" rel="stylesheet" title="default" />
</head>
<body>
<form:form action="${symbol_dollar}{formURL}" method="post" modelAttribute="todo">
	<fieldset>
		<legend><spring:message code="todos.create.title" /></legend>
		<label for="description"><spring:message code="todos.item.description"/></label><br />
		<form:input id="description" path="description" /><form:errors path="description" />
		<button name="submit" value="submit" type="submit">Submit!</button>
	</fieldset>
</form:form>
</body>
