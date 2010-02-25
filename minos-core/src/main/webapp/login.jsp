<%@ taglib prefix='core' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>

<script type="text/javascript">
$(document).ready(function() {
	$("#loginform_username").focus();
});
</script>

<core:if test="${not empty param.login_error}">
	<font color="red"> Your login attempt was not successful, try again.<br /><br />
	Reason: <core:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
	</font>
	
</core:if>

<form id="login" method="post" action="<core:url value='web/j_spring_security_check'/>">
	<table class="form">
		<tr>
			<th><label for="loginform_username"><spring:message code="umt.user.username" />:</label></th>
			<td>
				<input type="text" name="j_username" id="loginform_username" <core:if test="${not empty param.login_error}">value="<%= session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</core:if> /></td>
		</tr>
		<tr>
            <th><label for="loginform_password"><spring:message code="umt.user.password" />:</label></th>
			<td><input type="password" name="j_password" id="loginform_password"/></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="checkbox" name="_spring_security_remember_me" /><spring:message code="umt.login.rememberme" /></td>
		</tr>
		<tfoot>
			<tr>
				<td></td>
				<td><input type="submit" value="<spring:message code="core.ui.login" />" /></td>
			</tr>
		</tfoot>
	</table>
</form>
