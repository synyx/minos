<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="href" required="true" %>
<%@ attribute name="imageUrl" required="true" %>
<%@ attribute name="altKey" required="true" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<a href="${href}" title="<spring:message code="${altKey}" />">
	<core:image url="${imageUrl}" altKey="${altKey}" />
</a>