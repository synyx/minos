<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="href" required="true" %>
<%@ attribute name="imageUrl" required="false" %>
<%@ attribute name="altKey" required="false" %>


<c:set var="labelKey" value="${empty altKey ? 'core.ui.delete' : altkey}" />
<c:set var="href"><spring:url value="${href}" /></c:set>

<c:choose>
    <c:when test="${empty imageUrl}">
        <a href="${href}"><spring:message code="${labelKey}"/></a>
    </c:when>
    <c:otherwise>
        <a href="${href}"><img src="<c:url value="${imageUrl}" />" alt="<spring:message code="${labelKey}" />"/></a>
    </c:otherwise>
</c:choose>