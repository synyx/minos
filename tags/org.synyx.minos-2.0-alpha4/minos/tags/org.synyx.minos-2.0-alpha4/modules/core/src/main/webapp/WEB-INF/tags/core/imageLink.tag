<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="href" required="true" %>
<%@ attribute name="imageUrl" required="true" %>
<%@ attribute name="altKey" required="true" %>
<%@ attribute name="onclick" required="false" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<c:if test="${not empty onclick}">
	<c:set var="onClickString">
		onclick="${onclick}"
	</c:set>
</c:if>

<a href="<spring:url value='${href}' />" title="<spring:message code='${altKey}' />" ${onClickString}>
	<core:image url="${imageUrl}" altKey="${altKey}" />
</a>