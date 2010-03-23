<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:spring="http://www.springframework.org/tags/form"
	xmlns:spring-core="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core">
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>

<spring:form modelAttribute="addressForm" action="editAddress">
	<table>
		<tr>
			<td colspan="3">
				<input type="hidden" value="${addressForm.id}" name="id" />
			</td>
		</tr>
		<tr>
			<td>Street:</td>
			<td><spring:input path="street" /></td>
			<td><spring:errors path="street" /></td>
		</tr>
		<tr>
			<td>ZipCode:</td>
			<td><spring:input path="zipCode" /></td>
			<td><spring:errors path="zipCode" /></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><spring:input path="city" /></td>
			<td><spring:errors path="city" /></td>
		</tr>
	</table>
	<input type="submit" value="OK" />
</spring:form>

</jsp:root>