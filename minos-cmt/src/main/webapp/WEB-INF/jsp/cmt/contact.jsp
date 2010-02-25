<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:spring="http://www.springframework.org/tags/form"
	xmlns:spring-core="http://www.springframework.org/tags"
	xmlns:core="http://java.sun.com/jsp/jstl/core"
	xmlns:minos="http://www.synyx.com/tags"
	xmlns:display="http://displaytag.sf.net/el">
	<jsp:directive.page language="java"
		contentType="text/html;charset=ISO-8859-1" />
	<minos:system-message />
	
	<core:if test="${personForm != null}">
	<spring-core:message code="cmt.person.new" text="Create new person" />
		<spring:form modelAttribute="personForm"  action="savePerson">
			<table>
				<tr>
					<td colspan="3"><input type="hidden" name="id" value="${personForm.id}"/></td>
				</tr>
				<tr>
					<td>Firstname:</td>
					<td><spring:input path="firstname" /></td>
					<td><spring:errors path="firstname" /></td>
				</tr>
				<tr>
					<td>Lastname:</td>
					<td><spring:input path="lastname" /></td>
					<td><spring:errors path="lastname" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="OK" /></td>
					<td></td>
					<td></td>
					</tr>
			</table>
	
		</spring:form>
	</core:if>

	<core:if test="${organisationForm != null}">
		<spring-core:message code="cmt.organisation.new" text="Create new organsation" />
		<spring:form modelAttribute="organisationForm" action="saveOrganisation">
			<table>
				<tr>
					<td colspan="3"><input type="hidden" name="id" value="${organisationForm.id}"/></td>
				</tr>
				<tr>
					<td>Organsiation name:</td>
					<td><spring:input path="name" /></td>
					<td><spring:errors path="name" /></td>
				</tr>
				<tr>
					<td>Organsiation kind:</td>
					<td><spring:input path="type" /></td>
					<td><spring:errors path="type" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="OK" /></td>
					<td></td>
					<td></td>
					</tr>
			</table>
		</spring:form>
	</core:if>

	<table>
		<tr>
			<td><display:table name="addressesForContact"
				requestURI="editContact">
				<minos:column property="street" href="editAddress" paramId="id"
					paramProperty="id" />
				<minos:column property="zipCode" />
				<minos:column property="city" />
				<minos:column href="editAddress" paramId="id" paramProperty="id">Edit</minos:column>
				<minos:column href="deleteAddress" paramId="id" paramProperty="id">Delete</minos:column>
			</display:table></td>

			<td><display:table name="contactItemsForContact"
				requestURI="editContact">
				<minos:column property="value" href="editContactItem" paramId="id"
					paramProperty="id" />
				<minos:column property="description" />
				<minos:column property="visibility" />
				<minos:column href="editContactItem" paramId="id" paramProperty="id">Edit</minos:column>
				<minos:column href="deleteContactItem" paramId="id"
					paramProperty="id">Delete</minos:column>
			</display:table></td>

		</tr>
		<tr>
			<td><spring:form method="get" action="editAddress">
				<input type="submit" value="Create new Address" />
			</spring:form></td>
			<td><spring:form method="get" action="editContactItem">
				<input type="submit" value="Create new ContactItem" />
			</spring:form></td>
		</tr>
	</table>

	<spring:form action="contacts" method="get">
		<input type="submit" value="Back" />
	</spring:form>

</jsp:root>