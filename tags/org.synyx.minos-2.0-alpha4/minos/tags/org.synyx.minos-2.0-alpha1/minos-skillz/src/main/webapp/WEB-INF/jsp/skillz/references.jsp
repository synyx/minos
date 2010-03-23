<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<minos:system-message />

<display:table id="resume" name="resumes" requestURI="resumes" >
	<minos:column href="resumes/form" paramId="id" paramProperty="id">
		${resume.subject.lastname}, ${resume.subject.firstname}
	</minos:column>
	<minos:column property="lastModifiedDate" class="date" />
	<minos:column property="lastModifiedBy" />
	<minos:column href="description" paramId="id" paramProperty="id">
		<a href="resumes/form?id=${resume.id}"><img src="<c:url value="/images/umt/user_edit.png" />" alt="Edit" /></a>
		<a href="resume?id=${description.id}"><img src="<c:url value="/images/umt/user_delete.png" />" alt="Delete" /></a>
	</minos:column>
</display:table>

<a href="resumes/form"><img src="<c:url value="/images/umt/user_add.png" />" alt="Create new resume" /></a>
