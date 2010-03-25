<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<%@ attribute name="myaccount" type="java.lang.Boolean" required="false" %>

<minos:form modelAttribute="userForm" action="/web/umt/users" 
	resourceAction="${myaccount ? 'myaccount' : '' }">
	<table class="form">
		<tr>
			<th><label for="userform_username"><spring:message code="umt.user.username" />:</label></th>
			<core:if test="${myaccount}">
			<td>
				${userForm.username}
				<form:hidden path="username" />
			</td>
			</core:if>
			<core:if test="${!myaccount}">
			<td>
				<core:if test="${!userForm.new}">
					${userForm.username}
					<form:hidden path="username" />
				</core:if>
				<core:if test="${userForm.new}">
					<form:input path="username" id="userform_username"/>
				</core:if>
			</td>
			</core:if>
			<td><form:errors path="username" /></td>
		</tr>
		<tr>
			<th><label for="userform_firstname"><spring:message code="umt.user.firstname" />:</label></th>
			<td><form:input path="firstname" id="userform_firstname"/></td>
			<td><form:errors path="firstname" /></td>
		</tr>
		<tr>
			<th><label for="userform_lastname"><spring:message code="umt.user.lastname" />:</label></th>
			<td><form:input path="lastname" id="userform_lastname"/></td>
			<td><form:errors path="lastname" /></td>
		</tr>
		<tr>
			<th><label for="userform_emailaddress"><spring:message code="umt.user.emailAddress" />:</label></th>
			<td><form:input path="emailAddress" id="userform_emailaddress"/></td>
			<td><form:errors path="emailAddress" /></td>
		</tr>
		<core:if test="${!myaccount}">
		<tr>
			<th><label for="userform_roles"><spring:message code="umt.user.roles" />:</label></th>
			<td>
				<form:checkboxes id="userform_roles" path="roles" items="${roles}" itemLabel="name" itemValue="id" />
			</td>
			<td><form:errors path="roles" /></td>
		</tr>
		</core:if>
		<tr>
			<th><label for="userform_newpassword"><spring:message code="umt.user.newPassword" />:</label></th>
			<td><form:password path="newPassword" id="userform_newpassword"/></td>
			<td><form:errors path="newPassword" /></td>
		</tr>
		<tr>
			<th><label for="userform_repeatedpassword"><spring:message code="umt.user.repeatPassword" />:</label></th>
			<td><form:password path="repeatedPassword" id="userform_repeatedpassword"/></td>
			<td><form:errors path="repeatedPassword" /></td>
		</tr>
		<tfoot>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="<spring:message code="core.ui.ok" />" />
					<core:if test="${!myaccount}">
					<a href="../users"><spring:message code="core.ui.cancel" /></a>
					</core:if>
				</td>
				<td></td>
			</tr>
		</tfoot>
	</table>
</minos:form>