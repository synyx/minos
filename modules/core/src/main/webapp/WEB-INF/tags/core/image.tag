<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="url" required="true" description="The URL of the image. Will be preprocessed via spring:url."%>
<%@ attribute name="altKey" required="true" description="The key to lookup text for alt attribute via a resource bundle."%>

<img src="<spring:url value="${url}" />" alt="<spring:message code="${altKey}" />" />