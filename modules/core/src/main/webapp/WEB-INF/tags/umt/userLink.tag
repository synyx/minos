<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>


<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<%@ attribute name="user" type="org.synyx.minos.core.domain.User" required="true" %>


${user.username} (${user.firstname} ${user.lastname}) <core:imageLink href="/web/umt/users/${user.id}" imageUrl="/images/umt/user_edit.png" altKey="core.ui.edit" />