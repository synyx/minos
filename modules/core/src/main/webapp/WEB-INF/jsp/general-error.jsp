<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isErrorPage="true" import="org.apache.log4j.Logger"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring-helper-tagsupport" uri="http://www.synyx.org/spring-helper-tagsupport" %>

<html>
<head>
	<title><spring:message code="core.ui.error" /></title>
</head>
<body>
	<security:authorize ifAllGranted="ROLE_ADMIN">
		<spring-helper-tagsupport:general-error showStackTrace="TRUE"/>
	</security:authorize>
	
	<security:authorize ifNotGranted="ROLE_ADMIN">
		<spring-helper-tagsupport:general-error/>
	</security:authorize>
</body>
</html>
