<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<h2><spring:message code="umt.role" /></h2>

<minos:form modelAttribute="role" action="/web/umt/roles">

	<table class="form" id="role_form">
		<tr>
			<td><label for="roleform_name"><spring:message code="umt.role.name" />:</label></td>
			<td>
				<c:if test="${role.systemRole}">
					${role.name}
					<form:hidden path="name" />
				</c:if>
				<c:if test="${!role.systemRole}">
					<form:input path="name" id="roleform_name"/>
				</c:if>
			</td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td><label for="roleform_permissions"><spring:message code="umt.role.permissions" />:</label></td>
			<td>
				
			
				
				<c:forEach items="${permissions}" var="permission" varStatus="status">
					
					
					
					<c:choose>
					<c:when test="${fn:contains(role.permissions, permission)}"><c:set var="checked" value="checked='checked'"/></c:when>
					<c:otherwise><c:set var="checked" value=""/></c:otherwise>
					</c:choose>
					
					<c:set var="permissionnamenotresolved" value="umt.permission.${permission}"/>
					<c:set var="permissionname"><spring:message code="umt.permission.${permission}"/></c:set>
					<c:if test="${permissionname eq permissionnamenotresolved}">
						<c:set var="permissionname" value="${permission}"/>
					</c:if>
					
					<c:set var="permissiondescnotresolved" value="umt.permission.description.${permission}"/>
					<c:set var="permissiondesc"><spring:message code="umt.permission.description.${permission}"/></c:set>
					<c:if test="${permissiondesc eq permissiondescnotresolved}">
						<c:set var="permissiondesc" value=""/>
					</c:if>
					
					
					<span title="${permissiondesc}"><input ${checked} type="checkbox" value="${permission}" name="permissions" id="rolefor_permissions${status.index+1}"><label for="rolefor_permissions${status.index+1}">${permissionname}</label><br /></span>
					
					
					
					
					
					

					
					</li>
				</c:forEach>
				</ul>
				<input type="hidden" value="on" name="_permissions"/>
			</td>
			<td><form:errors path="permissions" /></td>
		</tr>
		<tfoot>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="<spring:message code="core.ui.ok" />" />
					<a href="../roles"><spring:message code="core.ui.cancel" /></a>
				</td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	
</minos:form>