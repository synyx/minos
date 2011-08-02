<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="umt" tagdir="/WEB-INF/tags/umt" %>

<html>
<body>
<h2><spring:message code="umt.role.delete.question" arguments="${role.name}"/></h2>
<c:set var="actionUrl"><c:url value="/web/umt/roles/${role.id}"/></c:set>


<c:if test="${not deletable }">

		<spring:message code="umt.role.delete.undeletable"/> 
		<div style="margin:20px 20px;">
		
		<ul class="list">
			<c:forEach var="user" items="${users}">
				<li><umt:userLink user="${user}"/></li>
			</c:forEach>
		</ul>
		</div>

</c:if>

<table class="form">
<tr><td>
<form:form method="delete" action="${actionUrl}">
	<c:if test="${deletable}">
		<input type="submit" value="<spring:message code="core.ui.ok" />" />&nbsp
	</c:if>
	
	<a href="<c:url value='/web/umt/roles'/>"><spring:message code="core.ui.cancel" /></a>
</form:form>
</td></tr>
</table>
</body>
</html>
