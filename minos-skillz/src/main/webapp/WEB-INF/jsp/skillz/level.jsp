<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<h2><spring:message code="skillz.level" /></h2>
<form:form modelAttribute="level" action="../levels">
	<table class="form">
		<tr>
			<td class="label"><spring:message code="name" />:</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tfoot>
			<tr>
				<td>
					<input type="hidden" name="level" value="${level.id}" />
					<input type="submit" value="<spring:message code="core.ui.save" />" />
				</td>
			</tr>
		</tfoot>
	</table>
</form:form>