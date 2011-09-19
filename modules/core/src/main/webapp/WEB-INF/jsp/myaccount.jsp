<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="umt" tagdir="/WEB-INF/tags/umt" %>

<html>
<head>
	<title><spring:message code="umt.myaccount.title" /></title>
</head>
<body>
    <h2><spring:message code="umt.myaccount.title" /></h2>
    <umt:userForm myaccount="true" />
</body>
</html>
