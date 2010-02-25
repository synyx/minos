<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="href" required="true" %>
<%@ attribute name="imageUrl" required="true" %>
<%@ attribute name="altKey" required="false" %>

<form:form method="delete" action="${href}">
	<input type="image" src="<c:url value="${imageUrl}" />" alt="<spring:message code="${empty altKey ? 'core.ui.delete' : altkey}" />" />
</form:form>