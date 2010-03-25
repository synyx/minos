<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<core:form modelAttribute="appointment" action="/web/calendar/appointments">
	<table>
		<tr>
			<td><spring:message code="calendar.appointments.description" />:</td>
			<td><form:input path="description" /></td>
			<td><form:errors path="description" /></td>
		</tr>
		<tr>
			<td><spring:message code="calendar.appointments.start" />:</td>
			<td><form:input path="start" /></td>
			<td><form:errors path="start" /></td>
		</tr>
		<tr>
			<td><spring:message code="calendar.appointments.end" />:</td>
			<td><form:input path="end" /></td>
			<td><form:errors path="end" /></td>
		</tr>
		<tr>
			<td><spring:message code="calendar.appointments.organizer" />:</td>
			<td>
				<form:select path="organizer">
					<form:options items="${users}" itemLabel="username" itemValue="id" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td><spring:message code="calendar.appointments.participants" />:</td>
			<td>
				<form:select path="participants" multiple="true" size="5">
					<form:options items="${users}" itemLabel="username" itemValue="id" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td><spring:message code="calendar.appointments.isAllDay" />:</td>
			<td>
				<form:checkbox path="allDay" />
			</td>
		</tr>
	</table>
	<input type="submit" value="<spring:message code="core.ui.save" />" />
</core:form>

<a href="<spring:url value="/web/calendar/appointments" />"><spring:message code="core.ui.cancel" /></a>