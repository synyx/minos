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



			<form action="" method="post" enctype="multipart/form-data">
			
			<table>
				<tr>
					<td><label for="importlanguage_lang"><spring:message code="i18n.basename.importlanguage.lang"/></label></td>
					<td>
						<select name="language" id="importlanguage_lang">
						<c:forEach items="${localeInformations}" var="lang">
							<option value="${lang.language.id}">
								${lang.language.locale}
							</option>
						</c:forEach>
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><label for="importlanguage_file"><spring:message code="i18n.basename.importlanguage.file"/></label></td>
					<td><input type="file" name="file" id="importlanguage_file"/></td>
					<td><small><spring:message code="i18n.basename.importlanguage.file.hint"/></small></td>
				</tr>
				<tr>
					<td colspan="3">
						<form:hidden path="basename"/>
						<input type="submit" value="<spring:message code="i18n.basename.importlanguage.button"/>"/>	
					</td>
				</tr>
			</table>
				
			
				
			</form>


<br />
<a href="<spring:url value="/web/i18n/basenames/${basename}"/>">&laquo; <spring:message code="i18n.basename.title"/></a>
</body>
</html>
