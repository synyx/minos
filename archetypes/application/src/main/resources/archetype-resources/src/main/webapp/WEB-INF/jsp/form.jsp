#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<spring:url value="/styles" var="stylesheets" />
	<c:choose><c:when test="${symbol_dollar}{item.new}">
		<spring:url value="/web/items" var="formURL" />
		<c:set var="formMethod" value="post" />
	</c:when><c:otherwise>
		<spring:url value="/web/items/${symbol_dollar}{item.id}" var="formURL" />
		<c:set var="formMethod" value="put" />
	</c:otherwise>
</c:choose>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><spring:message code="menu.items.create.title" /></title>
</head>
<body>
<form:form action="${symbol_dollar}{formURL}" method="${symbol_dollar}{formMethod}" modelAttribute="item">
	<fieldset>
		<legend><spring:message code="menu.items.create.title" /></legend>
		<label for="description"><spring:message code="items.singular.description"/></label>
		<form:input id="description" path="description" /><form:errors path="description" /><br />
		<label for="status"><spring:message code="items.singular.status" /></label>
		<form:select path="status" id="status">
			<form:options items="${symbol_dollar}{statusValues}" itemLabel="message" />
		</form:select>
		<form:errors path="status" /><br />
		<button name="submit" value="submit" type="submit">Submit!</button>
	</fieldset>
</form:form>
</body>
