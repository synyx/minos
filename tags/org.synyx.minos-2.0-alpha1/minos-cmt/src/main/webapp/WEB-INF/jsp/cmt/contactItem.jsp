<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:spring="http://www.springframework.org/tags/form"
	xmlns:spring-core="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core">
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>

<spring:form modelAttribute="contactItemForm" action="editContactItem">
	<table>
		<tr>
			<td colspan="3">
				<input type="hidden" value="${contactItemForm.id}" name="id" />
			</td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><spring:input path="description" /></td>
			<td><spring:errors path="description" /></td>
		</tr>
		<tr>
			<td>Value:</td>
			<td><spring:input path="value" /></td>
			<td><spring:errors path="value" /></td>
		</tr>
		<tr>
			<td>Visibility:</td>
			<td><spring:radiobutton path="isVisible" value="true" /> public </td>
			<td><spring:radiobutton path="isVisible" value="false"/> private</td>
		</tr>
	</table>
	<input type="submit" value="OK" />
</spring:form>

</jsp:root>