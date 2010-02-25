<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>

<display:table id="user" name="users" requestURI="users" >
	<minos:column titleKey="username" sortProperty="username">
		<a href="users/${user.id}">${user.username}</a>
	</minos:column>
	<minos:column property="firstname" />
	<minos:column property="lastname" />
	<minos:column property="emailAddress" />
	<minos:column property="roles" sortable="false" titleKey="umt.user.roles" />
	<minos:column property="lastModifiedDate" class="date" />
	<minos:column property="lastModifiedBy" />
	<minos:column class="actions" titleKey="core.ui.actions" sortable="false">
	
		<core:imageLink href="users/${user.id}" imageUrl="/images/umt/user_edit.png" altKey="core.ui.edit" />
		
		<%-- Prevent current user from deleting himself --%>
		<c:if test="${user != currentUser}">
			<core:deleteLink href="users/${user.id}" imageUrl="/images/umt/user_delete.png" />
		</c:if>
		
	</minos:column>
	<display:footer>
		<tr>
			<td colspan="8">
				<core:imageLink href="users/form" imageUrl="/images/umt/user_add.png" altKey="umt.user.new" />
			</td>
		</tr>
	</display:footer>
</display:table>
