<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:spring="http://www.springframework.org/tags/form"
	xmlns:spring-core="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core">
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>

<spring:form commandName="appointment">
	<table>
		<tr>
			<td>Description:</td>
			<td><spring:input path="description" /></td>
			<td><spring:errors path="description" /></td>
		</tr>
		<tr>
			<td>Start:</td>
			<td><spring:input path="start" /></td>
			<td><spring:errors path="start" /></td>
		</tr>
		<tr>
			<td>End:</td>
			<td><spring:input path="end" /></td>
			<td><spring:errors path="end" /></td>
		</tr>
		<tr>
			<td>Organizer:</td>
			<td>
				<spring:select path="organizer">
					<spring:options items="${users}" itemLabel="username" itemValue="id" />
				</spring:select>
			</td>
		</tr>
		<tr>
			<td>Participants:</td>
			<td>
				<spring:select path="participants" multiple="true" size="5">
					<spring:options items="${users}" itemLabel="username" itemValue="id" />
				</spring:select>
			</td>
		</tr>
	</table>
	<input type="submit" value="OK" />
</spring:form>

<form action="appointments.do" method="get">
	<input type="submit" value="Cancel" />
</form>
</jsp:root>