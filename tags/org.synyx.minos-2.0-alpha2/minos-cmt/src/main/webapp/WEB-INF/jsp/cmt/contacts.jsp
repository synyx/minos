<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0" 
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:spring-form="http://www.springframework.org/tags/form"
	xmlns:core="http://java.sun.com/jsp/jstl/core"
	xmlns:minos="http://www.synyx.com/tags"
	xmlns:display="http://displaytag.sf.net/el">
	
<jsp:directive.page language="java" contentType="text/html;charset=ISO-8859-1"/>

	<minos:system-message />

	<a href="createPerson"><spring:message code="cmt.person" /></a>
	<display:table name="persons" requestURI="contacts">
		<minos:column property="firstname" href="editContact" paramId="contactId"
			paramProperty="id" />
		<minos:column property="lastname" />
		<minos:column href="editContact" paramId="contactId" paramProperty="id">Edit</minos:column>
		<minos:column href="deleteContact" paramId="id" paramProperty="id">Delete</minos:column>
	</display:table>
	
	<a href="createOrganisation"><spring:message code="cmt.organisation" /></a>
	<display:table name="organisations" requestURI="contacts">
		<minos:column property="name" href="editContact" paramId="contactId"
			paramProperty="id" />
		<minos:column property="type" />
		<minos:column href="editContact" paramId="contactId" paramProperty="id">Edit</minos:column>
		<minos:column href="deleteContact" paramId="id" paramProperty="id">Delete</minos:column>
	</display:table>

</jsp:root>