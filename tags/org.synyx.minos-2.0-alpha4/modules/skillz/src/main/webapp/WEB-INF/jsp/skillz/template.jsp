<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags" %>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<h2><spring:message code="skillz.template" /></h2>

<minos:form modelAttribute="template" action="/web/skillz/templates">
	<table class="form">
		<tr>
			<td class="label"><spring:message code="skillz.template.name" />:</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.template.default" />:</td>
			<td><form:checkbox path="default" /></td>
			<td><form:errors path="default" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.template.categories" />:</td>
			<td>
				<ul>
					<form:checkboxes items="${categories}" path="categories" element="li" />
				</ul>
			</td>
			<td><form:errors path="categories" /></td>
		</tr>
		<tr>
			<td colspan="3">
				<form:hidden path="id" />
				<input type="submit" value="<spring:message code="core.ui.save" />" />
			</td>
		</tr>
	</table>
</minos:form>