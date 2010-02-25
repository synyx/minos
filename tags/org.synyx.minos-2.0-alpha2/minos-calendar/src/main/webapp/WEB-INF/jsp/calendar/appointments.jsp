<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0" 
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core"
	xmlns:minos="http://www.synyx.com/tags">
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>


<minos:system-message />

<ul>
	<li><a href="?mode=day">daily</a></li>
	<li><a href="?mode=week">weekly</a></li>
	<li><a href="?mode=month">monthly</a></li>
</ul>

<ul>
	<core:forEach items="${appointments}" var="appointment">
		<li>${appointment.description} - <a href="appointment?id=${appointment.id}">Edit</a> - <a href="delete?id=${appointment.id}">Delete</a></li>
	</core:forEach>
</ul>

<ul>
	<li>
		<a href="appointment"><spring:message code="calendar.appointment.new" text="Create new appointment" /></a>
	</li>
</ul>

</jsp:root>