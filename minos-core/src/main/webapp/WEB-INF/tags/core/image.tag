<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="url" required="true" description="The URL of the image. Will be preprocessed via c:url."%>
<%@ attribute name="altKey" required="true" description="The key to lookup text for alt attribute via a resource bundle."%>

<img src="<c:url value="${url}" />" alt="<spring:message code="${altKey}" />" />