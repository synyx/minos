<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="view" type="org.synyx.minos.calendar.web.CalendarView" required="true" description="The current view to be rendered" %>
<%@ attribute name="referenceDate" type="org.joda.time.DateMidnight" required="true" description="The reference date default outgoing links shall be rendered for" %>

<div>
	<spring:message message="${view}" />
	
	<ul>
		<li>
			<a href="<spring:url value="/web/calendar/appointments/${referenceDate.year}/${referenceDate.monthOfYear}/${referenceDate.dayOfMonth}"/>">
				<spring:message code="calendar.views.daily" />
			</a>
		</li>
		<li>
			<a href="<spring:url value="/web/calendar/appointments/${referenceDate.year}/${referenceDate.monthOfYear}" />">
				<spring:message code="calendar.views.monthly" />
			</a>
		</li>
	</ul>
	

	<jsp:doBody />

</div>