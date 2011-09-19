<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>
<%@ taglib prefix="umt" tagdir="/WEB-INF/tags/umt" %>

<html>
<head>
        <title><spring:message code="umt.menu.roles.title" /></title>
</head>
<body>

<h2><spring:message code="umt.role" /></h2>

<minos:form modelAttribute="role" action="/web/umt/roles">
<fieldset>
<legend><spring:message code="umt.role" /></legend>
<div class="pair">
	<label for="roleform_name"><spring:message code="umt.role.name" /></label>
	<c:choose><c:when test="${role.systemRole}">
		<span>${role.name}</span>
		<form:hidden path="name" />
	</c:when><c:otherwise>
		<form:input path="name" id="roleform_name"/>
	</c:otherwise></c:choose>
		<form:errors path="name" />
</div>
<div class="pair">
	<label for="roleform_description"><spring:message code="umt.role.description" /></label>
	<form:textarea path="description"/>
</div>

</fieldset>
<fieldset class="checkboxes">
<legend><spring:message code="umt.role.permissions" /></legend>
<c:forEach items="${permissions}" var="permission" varStatus="status">
	<c:choose>
		<c:when test="${permission.checked}"><c:set var="checked" value="checked='checked'"/></c:when>
		<c:otherwise><c:set var="checked" value=""/></c:otherwise>
	</c:choose>
	<c:set var="permissionnamenotresolved" value="umt.permission.${permission.name}"/>
	<c:set var="permissionname"><spring:message code="umt.permission.${permission.name}"/></c:set>
	<c:if test="${permissionname eq permissionnamenotresolved}">
		<c:set var="permissionname" value="${permission.name}"/>
	</c:if>
	<c:set var="permissiondescnotresolved" value="umt.permission.description.${permission.name}"/>
	<c:set var="permissiondesc"><spring:message code="umt.permission.description.${permission.name}"/></c:set>
	<c:if test="${permissiondesc eq permissiondescnotresolved}">
		<c:set var="permissiondesc" value=""/>
	</c:if>
	<p title="${permissiondesc}">
		<input ${checked} type="checkbox" value="${permission.name}" name="permissions" id="rolefor_permissions${status.index+1}">
		<label for="rolefor_permissions${status.index+1}">${permissionname}</label>
	</p>
</c:forEach>
<input type="hidden" value="on" name="_permissions"/>
<form:errors path="permissions" />
</fieldset>
<fieldset>
<div class="buttonrow">
	<input type="submit" value="<spring:message code="core.ui.ok" />" />
	<a href="<spring:url value="/web/umt/roles"/>"><spring:message code="core.ui.cancel" /></a>
</div>
</fieldset>

<c:if test="${not role.new }">
<fieldset>

	<legend>
		<spring:message code="umt.role.usersinrole" />
	</legend>	
	
	<spring:message code="umt.role.usersinrole.following" arguments="${role.name}" />
	
	<div style="margin:20px 20px;">

		<ul class="list">
			<c:forEach var="user" items="${users}">
				<li><umt:userLink user="${user}"/></li>
			</c:forEach>
		</ul>
	</div>
</fieldset>
</c:if>

</minos:form>

</body>
</html>
