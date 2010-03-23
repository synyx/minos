<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<minos:system-message />

<display:table id="project" name="projects" requestURI="${projectsUrl}" >
	<minos:column property="name" href="projects/form" paramId="id" paramProperty="id" />
	<minos:column property="description" />
	<minos:column property="lastModifiedDate" class="date" />
	<minos:column property="lastModifiedBy" />
	<minos:column>
		<a href="private/form?id=${project.id}"><img src="<c:url value="/images/umt/user_edit.png" />" alt="Edit" /></a>
		<a href="delete?id=${project.id}"><img src="<c:url value="/images/umt/user_delete.png" />" alt="Delete" /></a>
	</minos:column>
	<display:footer>
		<tr>
			<td>
				<core:imageLink href="${username}/form" imageUrl="/images/umt/user_add.png" altKey="skillz.project.new" />
			</td>
		</tr>
	</display:footer>
</display:table>
