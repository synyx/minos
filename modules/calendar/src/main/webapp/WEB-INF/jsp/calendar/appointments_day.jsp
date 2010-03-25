<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>
<%@ taglib prefix="calendar" tagdir="/WEB-INF/tags/calendar" %>

<calendar:appointments view="${view}" referenceDate="${referenceDate}">

	<display:table id="appointment" name="appointments">
		<minos:column property="start" />
		<minos:column property="description" />
		<minos:column class="actions">
			<core:imageLink href="/web/calendar/appointments/${appointment.id}" imageUrl="images/icons/edit.png" altKey="core.ui.edit" />
			<core:deleteLink href="/web/calendar/appointments/${appointment.id}" />
		</minos:column>
		<display:footer>
			<tr>
				<td colspan="3">
					<a href="<spring:url value="/web/calendar/appointments/form" />">
						<spring:message code="calendar.appointment.new" />
					</a>
				</td>
			</tr>
		</display:footer>
	</display:table>
	
</calendar:appointments>