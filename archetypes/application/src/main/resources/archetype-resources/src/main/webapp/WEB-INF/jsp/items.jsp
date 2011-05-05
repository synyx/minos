#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<c:set var="title"><spring:message code="menu.items.list.title" /></c:set>
<c:set var="editMessage"><spring:message code="sample.edit" /></c:set>
<c:set var="deleteMessage"><spring:message code="sample.delete" /></c:set>
<spring:url value="/styles" var="stylesheets" />

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${symbol_dollar}{title}</title>
</head>
<body>
<h1>${symbol_dollar}{title}</h1>
<display:table id="item" name="items" requestURI="" defaultsort="1" pagesize="10" sort="list">
	<minos:column titleKey="items.singular.createdBy" property="createdBy.username" />
	<minos:column titleKey="items.singular.description" property="description" />
	<minos:column titleKey="items.singular.createdDate" property="createdDate" />
	<minos:column titleKey="items.singular.status" property="status.message" />
	<minos:column>
		<a class="editlink" href="items/${symbol_dollar}{item.id}">${symbol_dollar}{editMessage}</a>
		<authz:authorize ifAnyGranted="ROLE_ADMIN">
		<form:form method="delete" action="items/${symbol_dollar}{item.id}">
			<input type="submit" value="${symbol_dollar}{deleteMessage}" />
		</form:form>
		</authz:authorize>
	</minos:column>
</display:table>
</body>
