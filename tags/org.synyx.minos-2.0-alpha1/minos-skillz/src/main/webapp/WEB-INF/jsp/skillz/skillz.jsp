<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<minos:system-message />

<h2 class="iconized categories"><spring:message code="skillz.categories" /></h2>
<display:table id="category" name="categories" requestURI="" >
	<minos:column titleKey="name">
		<a href="categories/${category.id}" title="${category.description}"><c:out value="${category.name}" /></a>
	</minos:column>
	<minos:column titleKey="skillz.skills">
		<c:forEach items="${category.skillz}" var="skill">
			${skill.name},&nbsp;
		</c:forEach>
	</minos:column>
	<minos:column property="lastModifiedDate" class="date meta" />
	<minos:column property="lastModifiedBy" class="meta" />
	<minos:column class="actions">
		<core:imageLink href="categories/${category.id}" imageUrl="/images/skillz/category_edit.png" altKey="core.ui.edit" />
		<core:deleteLink href="categories/${category.id}" imageUrl="/images/skillz/category_delete.png" altKey="core.ui.delete" />
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="5">
				<core:imageLink href="categories/form" imageUrl="/images/skillz/category_add.png" altKey="skillz.category.new" />				
			</td>
		</tr>
	</display:footer>
</display:table>


<h2 class="iconized templates"><spring:message code="skillz.templates" /></h2>
<display:table id="template" name="templates" requestURI="" >
	<minos:column property="name" href="templates/form" paramId="id" paramProperty="id" />
	<minos:column titleKey="skillz.skills">
		<c:forEach items="${template.categories}" var="category">
			${category.name},&nbsp;
		</c:forEach>
	</minos:column>
	<minos:column property="lastModifiedDate" class="date meta" />
	<minos:column property="lastModifiedBy" class="meta" />
	<minos:column class="actions">
		<core:imageLink href="templates/form?id=${template.id}" imageUrl="/images/skillz/template_edit.png" altKey="core.ui.edit" />
		<core:imageLink href="templates/delete?id=${template.id}" imageUrl="/images/skillz/template_delete.png" altKey="core.ui.delete" />
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="5">
				<core:imageLink href="templates/form" imageUrl="/images/skillz/template_add.png" altKey="skillz.template.new" />			
			</td>
		</tr>
	</display:footer>
</display:table>

<h2 class="iconized projects"><spring:message code="skillz.projects" /></h2>
<display:table id="project" name="projects" requestURI="" >
	<minos:column property="name" href="projects/form" paramId="id" paramProperty="id" style="vertical-align: top;" />
	<minos:column property="abstract" titleKey="description" escapeXml="true" />
	<minos:column property="lastModifiedDate" class="date meta"/>
	<minos:column property="lastModifiedBy" class="meta" />
	<minos:column class="actions">
		<core:imageLink href="projects/form?id=${project.id}" imageUrl="/images/skillz/project_edit.png" altKey="core.ui.edit" />
		<core:imageLink href="projects/delete?id=${project.id}" imageUrl="/images/skillz/project_delete.png" altKey="core.ui.delete" />
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="5">
				<core:imageLink href="projects/form" imageUrl="/images/skillz/project_add.png" altKey="skillz.project.new" />
			</td>
		</tr>
	</display:footer>
</display:table>

<h2 class="iconized levels"><spring:message code="skillz.levels" /></h2>
<display:table id="level" name="levels" requestURI="" >
	<minos:column property="name" href="level/form" paramId="id" paramProperty="id" />
	<minos:column property="lastModifiedDate" class="date meta" />
	<minos:column property="lastModifiedBy" class="meta" />
	<minos:column class="actions">
		<core:imageLink href="levels/form?id=${level.id}" imageUrl="/images/skillz/level_edit.png" altKey="core.ui.edit" />
		<core:imageLink href="levels/delete?id=${level.id}" imageUrl="/images/skillz/level_delete.png" altKey="core.ui.delete" />
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="4">
				<core:imageLink href="levels/form" imageUrl="/images/skillz/level_add.png" altKey="skillz.level.new" />			
			</td>
		</tr>
	</display:footer>
</display:table>
