<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="core.security.accessdenied" />!</h1>

<a href="<spring:url value='/login.jsp' />"><spring:message code="core.security.gotologin" /></a>