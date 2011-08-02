<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<html>
<body>
<display:table id="role" name="roles" requestURI="roles">
	<minos:column sortProperty="name" titleKey="name">
		<a href="roles/${role.id}">${role.name}</a>
	</minos:column>
	<minos:column property="descriptionShortVersion" sortProperty="description" titleKey="description"/>
	<minos:column sortable="false" class="actions" titleKey="core.ui.actions">
	
		<!-- Edit role -->
		<core:imageLink href="roles/${role.id}" imageUrl="/images/umt/role_edit.png" altKey="umt.role.edit" />
		
		<!-- Delete role -->
		<c:if test="${!role.systemRole}">
			<core:deleteLink href="roles/${role.id}" imageUrl="/images/umt/role_delete.png" />
		</c:if>
		
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="2">
				<core:imageLink href="roles/form" imageUrl="/images/umt/role_add.png" altKey="umt.role.new" />		
			</td>
		</tr>
	</display:footer>
</display:table>
</body>
</html>
