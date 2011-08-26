<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="href" required="true" %>
<%@ attribute name="imageUrl" required="false" %>
<%@ attribute name="altKey" required="false" %>
<%@ attribute name="onClick" required="false" %>

<c:set var="labelKey" value="${(empty altKey) ? 'core.ui.delete' : altKey}" />
<c:set var="href"><spring:url value="${href}" /></c:set>
<form:form method="delete" action="${href}" cssStyle="display: inline;">
	<c:choose>
		<c:when test="${empty imageUrl}">
			<input type="submit" value="<spring:message code='${labelKey}' />" onClick="${onClick}"/>
		</c:when>
		<c:otherwise>
			<input type="image" src="<c:url value='${imageUrl}' />" alt="<spring:message code='${labelKey}' />" onClick="${onClick}"/>
		</c:otherwise>
	</c:choose>
</form:form>
