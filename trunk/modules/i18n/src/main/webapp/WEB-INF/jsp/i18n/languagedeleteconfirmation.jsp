<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<script type="text/javascript" src="<c:url value="/js/jquery-1.4.2.min.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.4.custom.min.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/js/jquery.simpleautogrow-0.1.1.js" />"> </script>
</head>
<body>
<h1><spring:message code="i18n.basename.title"/>: <c:out value="${basename}"/></h1>
<hr /> <br />



<p>
  <spring:message code="i18n.basename.deleteLanguage.question" arguments="${locale}" />
</p>

<form action="." method="post">
	<input type="hidden" name="_method" value="delete"/>
	<input type="submit" value="<spring:message code="core.ui.delete"/>"/>
</form>


<br />
<a href="<spring:url value="/web/i18n/basenames/${basename}"/>">&laquo; <spring:message code="i18n.basename.title"/></a>

</body>
</html>