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
    <style type="text/css">
    ${symbol_pound}item { font: 13px/20px sans-serif; }
    ${symbol_pound}item fieldset { background: ${symbol_pound}eee; padding: 1em; }
    ${symbol_pound}item legend {
    	font-weight: bold;
    	padding: 0.2em 0.5em;
    	border: 1px solid black;
    	background: ${symbol_pound}ddd;
    }
    ${symbol_pound}item label { width: 10em; display: block; float: left; clear: left; }
    ${symbol_pound}item input, ${symbol_pound}item submit { display: block; float: left; }
    ${symbol_pound}item button[name="submit"] {
    	display: block;
    	clear: left;
    	float: left;
    	margin-top: 2em;
    	font-weight: bold;
    }
    ${symbol_pound}item .error { color: red; }
    </style>
</head>
<body>
<form:form action="${symbol_dollar}{formURL}" method="${symbol_dollar}{formMethod}" modelAttribute="item">
	<fieldset>
		<legend>${symbol_dollar}{formTitle}</legend>
		<form:hidden path="id" />
		<form:hidden path="createdDate" />
		<form:hidden path="createdBy" />
		<label for="description"><spring:message code="items.singular.description"/></label>
		<form:input id="description" path="description" /><form:errors class="error" path="description" /><br />
		<label for="status"><spring:message code="items.singular.status" /></label>
		<form:select path="status" id="status">
			<form:options items="${symbol_dollar}{statusValues}" itemLabel="message" />
		</form:select>
		<form:errors  class="error" path="status" /><br />
		<button name="submit" value="submit" type="submit">Submit!</button>
	</fieldset>
</form:form>
</body>
