<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<script type="text/javascript">


function toggleSize(fieldid) {
	var node = $('#' + fieldid);
	if (node.is('textarea')) {
		toInput(node);
	} else {
		toTextArea(node);
	}
}

function toTextArea(node) {
	
	var id = node.attr('id');
	var text = node.val();
	var style = node.attr('style');
	var onblur = "toggleSize('"+id+"'); return false;";
	var name = node.attr('name');
	var textarea = '<textarea onblur="'+onblur+'" rows="5" id="'+id+'" name="'+name+'" style="'+style+'" ></textarea>';

	node.replaceWith(textarea);
	$('#'+id).text(text);
	$('#'+id).focus();
	
}

function toInput(node) {

	var id = node.attr('id');
	var text = node.val();
	
	if (text.match('\n')) {
		return;
	}
	var style = node.attr('style');
	var onfocus = "toggleSize('"+id+"'); return false;";
	var name = node.attr('name');
	var textarea = '<input  onfocus="'+onfocus+'"  type="text" id="'+id+'" name="'+name+'" style="'+style+'" value="" />';

	node = node.replaceWith(textarea);
	
	$('#' + id).attr('value', text);

}

</script>

<h2><spring:message code="i18n.messages.title" arguments="${basename}###${locale}" argumentSeparator="###"/></h2>


<div class="languages">
			

		<div class="clearfix">
			
			<div class="thislanguage" style="width: 49%; float:right;">
				<h2>
					<c:out value="${locale}"/>
				</h2>
				
				<%--
				<div>
				<spring:message code="i18n.messages.changeorder"/>: 
				<form style="display:inline;" method="get">
					<input type="hidden" name="reference" value="${reference}"/>
					
					<select name="order">
						<option value="keys">keys</option>
						<option value="keys">keys but undefined first</option>
						<option value="keys">keys but defined first</option>
					</select>
					<input type="submit" value="change"/>
					</form>
				</div>
				 --%>
							
			</div>
			<div style="float: right; width:2%">&nbsp;</div>
			<div class="otherlanguage" style="float: right; width: 49%; ">
				<h2>
					<c:out value="${reference}"/>
				</h2>
				<div>
				<spring:message code="i18n.messages.changereference"/>: 
				<form style="display:inline;" method="get">
					<select name="reference">
					<c:forEach var="refloc" items="${locales}">
						<c:choose>
						<c:when test="${refloc eq reference}">
							<c:set var="sel" value="selected='selected'"/>
						</c:when>
						<c:otherwise>
							<c:set var="sel" value=""/>
						</c:otherwise>
						</c:choose>
						<option ${sel} value="${refloc}">${refloc}</option>
						
					</c:forEach>
					</select>
					<input type="submit" value="change"/>
					</form>
				</div>
			</div>
		</div>
		
	</div>

<p style="margin-top: 2em;">&nbsp;</p>
<form action="" method="post">



<input type="hidden" name="_method" value="put"/>

<c:forEach items="${messages}" var="messageView" varStatus="status">



	<div class="messagecontainer" id="messages_${status.index}_container">
		<c:if test="${messageView.definedInCurrent}"> <%-- add id only for existing keys --%>
			<input type="hidden" name="messages[${status.index}].id" value="<c:out value="${messageView.message.id}"/>"/>
		</c:if>
		<input type="hidden" name="messages[${status.index}].basename" value="<c:out value="${messageView.message.basename}"/>"/>
		<input type="hidden" name="messages[${status.index}].locale.language" value="<c:out value="${messageView.currentLocale.language}"/>"/>
		<input type="hidden" name="messages[${status.index}].locale.country" value="<c:out value="${messageView.currentLocale.country}"/>"/>
		<input type="hidden" name="messages[${status.index}].locale.variant" value="<c:out value="${messageView.currentLocale.variant}"/>"/>
		<input type="hidden" name="messages[${status.index}].key" value="<c:out value="${messageView.message.key}"/>"/>
		<h3>
			<c:out value="${messageView.message.key}"/> 
			
			<c:choose>
				<c:when test="${messageView.newForTranslation}">[new]</c:when>
				<c:when test="${messageView.updatedForTranslation}">[updated]</c:when>
			</c:choose>
			
		</h3>

		<div class="clearfix">
			
			<div class="thislanguage" style="width: 49%; float:right;">
				<p style="margin-bottom: 0.5em;">
					<small><spring:message code="i18n.messages.definedin" arguments="${messageView.message.locale}"/></small>
				</p>
				
				<c:choose>
				<c:when test="${!messageView.definedInCurrent}">
					<c:set var="style" value="background-color: #eeeeff;"/>
				</c:when>
				<c:otherwise>
					<c:set var="style" value=""/>
				</c:otherwise>
				</c:choose>
				<c:out value="" ></c:out>

				<c:set var="newline" value="\n"/>
				
				<%
					pageContext.setAttribute("newline", "\n");
				%>		
				<c:choose>
				

				<c:when test='${fn:contains(messageView.message.message,newline)}'>
					<textarea rows="5" onfocus="toggleSize('messages_${status.index}_message'); return false;" id="messages_${status.index}_message" name="messages[${status.index}].message" style="width:100%;border: 1px solid lightgray; ${style}" ><c:out value="${messageView.message.message}"/></textarea>
				</c:when>
				<c:otherwise>
					<input onfocus="toggleSize('messages_${status.index}_message'); return false;" type="text" id="messages_${status.index}_message" name="messages[${status.index}].message" style="width:100%;border: 1px solid lightgray; ${style}" value="<c:out value="${messageView.message.message}"/>"/>
				
				</c:otherwise>
				</c:choose>
				
			</div>
			<div style="float: right; width:2%">&nbsp;</div>
			<div class="otherlanguage" style="float: right; width: 49%; ">
				<p style="margin-bottom: 0.5em;">
					<small><spring:message code="i18n.messages.definedin" arguments="${messageView.reference.message.locale}"/></small>
				</p>
				<div style="font-family: monospace; background-color: #fff; width:100%" >
					<c:out value="${messageView.reference.message.message}"/>
				</div>
				
				
			</div>
		</div>
		
	</div>

</c:forEach>

<div class="buttons" style="margin-top: 15px; margin-bottom: 10px;">
<input type="submit" value="<spring:message code="core.ui.save"/>" />
</div>

</form>
<a href="<spring:url value="/web/i18n/basenames/${basename}"/>">&laquo; <c:out value="${basename}"/></a>
