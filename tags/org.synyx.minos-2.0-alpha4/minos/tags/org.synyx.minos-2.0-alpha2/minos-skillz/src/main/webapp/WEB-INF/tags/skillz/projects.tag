<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<%@ attribute name="project" type="org.synyx.minos.skillz.domain.Project" %>
<%@ attribute name="privateProject" type="java.lang.Boolean" required="false" %> 

<c:choose>
	<c:when test="${privateProject}">
		<c:set var="prefix" value="" />
		<c:set var="username" value="${username}/" />
	</c:when>
	<c:otherwise>
		<c:set var="prefix" value="projects/" />
		<c:set var="username" value="" />
	</c:otherwise>
</c:choose>

<display:table id="project" name="projects" requestURI="" >
	<minos:column titleKey="name">
		<a href="${prefix}${project.id}"><c:out value="${project.name}" /></a>
	</minos:column>
	<minos:column property="description" />
	<minos:column property="lastModifiedDate" class="date" />
	<minos:column property="lastModifiedBy" />
	<minos:column>
		<core:imageLink href="${prefix}${project.id}" imageUrl="/images/umt/user_edit.png" altKey="skillz.project.edit" />
		<core:deleteLink href="${prefix}${project.id}" imageUrl="/images/umt/user_delete.png" />
	</minos:column>
	<display:footer>
		<tr>
			<td>
				<core:imageLink href="${prefix}${username}form" imageUrl="/images/umt/user_add.png" altKey="skillz.project.new" />
			</td>
		</tr>
	</display:footer>
</display:table>