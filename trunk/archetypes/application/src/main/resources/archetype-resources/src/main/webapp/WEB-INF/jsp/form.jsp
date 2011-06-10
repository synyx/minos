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
		<spring:message code="menu.items.create.title" var="formTitle"/>
	</c:when><c:otherwise>
		<spring:url value="/web/items/${symbol_dollar}{item.id}" var="formURL" />
		<c:set var="formMethod" value="put" />
		<spring:message code="menu.items.edit.title" var="formTitle"/>
	</c:otherwise>
</c:choose>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${symbol_dollar}{formTitle}</title>
</head>
<body>
<form:form action="${symbol_dollar}{formURL}" method="${symbol_dollar}{formMethod}" modelAttribute="item">
<form:errors path="*" />
<form:hidden path="id" />
<form:hidden path="createdDate" />
<form:hidden path="createdBy" />
<fieldset>
<legend>${symbol_dollar}{formTitle}</legend>
<div class="pair">
	<label for="description"><spring:message code="items.singular.description"/></label>
	<form:input id="description" path="description" /><form:errors class="error" path="description" /><br />
</div>
<div class="pair">
	<label for="status"><spring:message code="items.singular.status" /></label>
	<form:select path="status" id="status">
		<form:options items="${symbol_dollar}{statusValues}" itemLabel="message" />
	</form:select>
	<form:errors  class="error" path="status" /><br />
</div>
</fieldset>
<fieldset>
	<div class="buttonrow">
		<button name="submit" value="submit" type="submit">Submit!</button>
	</div>
</fieldset>
</form:form>
</body>
