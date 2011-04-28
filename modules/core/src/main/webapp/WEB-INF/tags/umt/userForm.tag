<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<%@ attribute name="myaccount" type="java.lang.Boolean" required="false" %>

<minos:form modelAttribute="userForm" action="/web/umt/users" resourceAction="${myaccount ? 'myaccount' : '' }">
<fieldset>
<legend><spring:message code="umt.user" /></legend>
<div>
	<label for="userform_username"><spring:message code="umt.user.username" /></label>
	<c:choose><c:when test="${myaccount || !userForm.new}">
		${userForm.username}
		<form:hidden path="username" />
	</c:when><c:otherwise>
		<form:input path="username" id="userform_username"/>
	</c:otherwise></c:choose>
	<form:errors path="username" />
</div>
<c:choose><c:when test="${!myaccount}">
	<div>
		<label for="userform_active"><spring:message code="umt.user.active" /></label>
		<form:checkbox path="active" id="userform_active"/>
		<form:errors path="active" />
	</div>
</c:when><c:otherwise>
	<form:hidden path="active"/>
</c:otherwise></c:choose>
<div>
	<label for="userform_firstname"><spring:message code="umt.user.firstname" /></label>
	<form:input path="firstname" id="userform_firstname"/>
	<form:errors path="firstname" />
</div>
<div>
	<label for="userform_lastname"><spring:message code="umt.user.lastname" /></label>
	<form:input path="lastname" id="userform_lastname"/>
	<form:errors path="lastname" />
</div>
<div>
	<label for="userform_emailaddress"><spring:message code="umt.user.emailAddress" /></label>
	<form:input path="emailAddress" id="userform_emailaddress"/>
	<form:errors path="emailAddress" />
</div>
<c:if test="${!myaccount}">
<div>
	<label for="userform_roles"><spring:message code="umt.user.roles" /></label>
	<div class="controlset">
		<form:checkboxes id="userform_roles" path="roles" items="${roles}" itemLabel="name" itemValue="id" />
		<form:errors path="roles" /></td>
	</div>
</div>
</c:if>
<div>
	<label for="userform_newpassword"><spring:message code="umt.user.newPassword" /></label>
	<form:password path="newPassword" id="userform_newpassword"/>
	<form:errors path="newPassword" />
</div>
<div>
	<label for="userform_repeatedpassword"><spring:message code="umt.user.repeatPassword" /></label>
	<form:password path="repeatedPassword" id="userform_repeatedpassword"/>
	<form:errors path="repeatedPassword" />
</div>
</fieldset>
<div class="buttonrow">
	<input type="submit" value="<spring:message code="core.ui.ok" />" />
	<a href="../users"><spring:message code="core.ui.cancel" /></a>
</div>
</minos:form>
