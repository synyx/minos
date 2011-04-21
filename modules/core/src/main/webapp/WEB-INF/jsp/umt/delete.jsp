<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<h2><spring:message code="umt.user.delete.question" arguments="${userForm.username}"/></h2>
<c:set var="actionUrl"><c:url value="/web/umt/users/${userForm.id}"/></c:set>

<table class="form">
<tr><td>
<form:form method="delete" action="${actionUrl}">
	<input type="submit" value="<spring:message code="core.ui.ok" />" />&nbsp
	<a href="<c:url value='/web/umt/users'/>"><spring:message code="core.ui.cancel" /></a>
</form:form>
</td></tr>
</table>
</body>
</html>
