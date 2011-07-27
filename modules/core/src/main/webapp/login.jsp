<%@ taglib prefix='core' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<html>
<head>
	<style type="text/css">
		#login { width: 600px; margin: 0 auto; }
	</style>
	<title>Minos 2 - Login</title>
</head>
<body>

<core:if test="${not empty param.login_error}">
	<font color="red"> Your login attempt was not successful, try again.<br /><br />
	Reason: <core:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
	</font>
</core:if>

<form id="login" method="post" action="<core:url value='web/j_spring_security_check'/>">
<fieldset>
	<legend><spring:message code="core.ui.login" /></legend>
	<div class="pair">
		<label for="loginform_username"><spring:message code="umt.user.username" /></label>
		<input type="text" name="j_username" id="loginform_username" <core:if test="${not empty param.login_error}">value="<%= session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</core:if> />
	</div>
	<div class="pair">
		<label for="loginform_password"><spring:message code="umt.user.password" /></label>
		<input type="password" name="j_password" id="loginform_password"/>
	</div>
	<div class="pair">
		<label>&nbsp;</label>
		<input type="checkbox" name="_spring_security_remember_me" /><spring:message code="umt.login.rememberme" />
	</div>
</fieldset>
<div class="buttonrow">
	<input type="submit" value="<spring:message code="core.ui.login" />" />
</div>

</form>
<script type="text/javascript">
  document.forms.login.loginform_username.focus();
</script>
</body>

</html>
