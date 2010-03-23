<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<h2><spring:message code="umt.role" /></h2>

<form:form modelAttribute="role">
	<table class="form">
		<tr>
			<th><label for="roleform_name"><spring:message code="umt.role.name" />:</label></th>
			<td>
				<core:if test="${role.systemRole}">
					${role.name}
					<form:hidden path="name" />
				</core:if>
				<core:if test="${!role.systemRole}">
					<form:input path="name" id="roleform_name"/>
				</core:if>
			</td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<th><label for="roleform_permissions"><spring:message code="umt.role.permissions" />:</label></th>
			<td>
				<form:checkboxes id="roleform_permissions" path="permissions" items="${permissions}" />
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
	
</form:form>