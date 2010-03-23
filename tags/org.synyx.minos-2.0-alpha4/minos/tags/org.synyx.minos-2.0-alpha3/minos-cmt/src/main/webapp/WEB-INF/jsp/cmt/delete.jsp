<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:spring="http://www.springframework.org/tags/form"
	xmlns:spring-core="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core"
    xmlns:minos="http://www.synyx.com/tags">
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>

<minos:system-message />

<spring:form modelAttribute="personForm">
	<table>
		<tr>
			<td><minos:system-message /></td>
		</tr>
		<tr>
			<td><spring:form path=""/></td>
		</tr>
	</table>
	<input type="submit" value="OK" />
</spring:form>

<form action="contacts" method="get">
	<input type="submit" value="Cancel" />
</form>
</jsp:root>