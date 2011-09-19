<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="umt" tagdir="/WEB-INF/tags/umt" %>

<html>
<head>
        <title><spring:message code="umt.menu.users.title" /></title>
</head>

<body>
	<umt:userForm />
</body>
</html>
