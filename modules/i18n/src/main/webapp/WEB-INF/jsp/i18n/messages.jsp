<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<c:set var="showFormURL" scope="page"><spring:url value="/web/i18n/basenames/${basename}/messages/${locale}/"/></c:set>

<script type="text/javascript">

function showMessageForm(formContainer) {

	var key = $(formContainer).siblings(".messagekey").text();
	
	// hide all other message forms and also remove the forms from dom
	$(".messageform").hide();
	$(".messageform").html("");

	// show the desired message form
	var jsonUrl = "${showFormURL}" + key + "/json?reference=${reference}";
	$.getJSON(jsonUrl, function (data) {

		updateMessageForm(formContainer, data);

		// finally, show the form
		$(formContainer).show("fast");	
	});
}

function updateMessageForm(formContainer, data) {

	// get form template
	var template = $('#messageform_template').html();

	// replace template placeholder by json result
	template = fillTemplate(template, data);

	// add filled template content to form node and show form 
	$(formContainer).html(template);

	// set style of textarea according to if the key is defined in current locale
	if (data.resolved == 'false') {
		var style = "background-color: #eeeeff;";
	} 
	var textArea = $(formContainer).find(".messagetextarea");
	textArea.attr("style", textArea.attr("style") + style);

	


	var inheritedSpan = $(formContainer).siblings(".messageheader").children(".messageinheritanced");
	

	if (data.resolved == 'true') {
		inheritedSpan.attr('title', data.locale);
		inheritedSpan.text("[PARENT]");
	} else {
		inheritedSpan.attr('title', "");
		inheritedSpan.text("");
	}
	
	
	// update status display
	var statusSpan = $(formContainer).siblings(".messageheader").children(".messagestatus");
	if (typeof(data.status) !== 'undefined' && data.status != null) {
		statusSpan.text("[" + data.status + "]");
	} else {
		statusSpan.text("");
	}
}

function submitMessageForm(form) {

	var formNode = $(form);
	var formData = formNode.serialize();
	var url = formNode.attr("action");

	$.ajax({
		type: "PUT",
		url: url,
		data: formData,
		contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
		success: function(data, textStatus) {

			var formContainer = $(form).parent();
			updateMessageForm(formContainer, data);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert("ERROR - Status: " + textStatus);
		}
	});
}

function fillTemplate(template, valueMap) {

	for (var name in valueMap) {

		var regExp = new RegExp("%%" + name + "%%", "g");
		var value = valueMap[name];
		template = template.replace(regExp, value);
	}

	return template;
}

$(document).ready ( function() {

	$(".messageheader").click(
			function () { 
				var form = $(this).siblings(".messageform");
				showMessageForm(form);
	 		}
	 );

});


</script>

<h2><spring:message code="i18n.messages.title" arguments="${basename}###${locale}" argumentSeparator="###"/></h2>


<div class="languages">
			

		<div class="clearfix">
			
			<div class="thislanguage" style="width: 49%; float:right;">
				<h2>
					<c:out value="${locale}"/>
				</h2>
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

<c:forEach items="${messages}" var="messageView" varStatus="status">

	<div class="messagecontainer" id="message_container_${status.index}">
		<h3 class="messageheader" id="message_header_${status.index}" style="cursor:pointer;">
			<c:out value="${messageView.message.key}"/> 
			<span style="float:right;" class="messagestatus">
			<c:choose>
				<c:when test="${messageView.newForTranslation}">[NEW]</c:when>
				<c:when test="${messageView.updatedForTranslation}">[UPDATED]</c:when>
			</c:choose>
			</span>
			
			<span style="float:right;">&nbsp;</span>
			
			<c:choose>
				<c:when test="${messageView.messageResolved}">
				<span style="float:right;" class="messageinheritanced" title="${messageView.message.locale}">
				[PARENT]
				</span>
				</c:when>
				<c:otherwise>
					<span style="float:right;" class="messageinheritanced"></span>
				</c:otherwise>
			</c:choose>
			
		</h3>
		<div class="messagekey" style="display:none;"><c:out value="${messageView.message.key}"/></div>

		<div class="clearfix messageform" style="display:none;" id="message_form_${status.index}"></div>
		
	</div>
	
</c:forEach>

<a href="<spring:url value="/web/i18n/basenames/${basename}"/>">&laquo; <c:out value="${basename}"/></a>


















<!-- the content of this invisible div is used as a template to display a single keys form -->
<div id="messageform_template" style="display:none;">

<form action="${showFormURL}%%key%%/json" method="put" onsubmit="submitMessageForm(this); return false;">
	<input type="hidden" name="_method" value="put"/>

	<input type="hidden" name="reference" value="${reference}" />
	
	<input type="hidden" name="id" value="%%id%%" />

	<input type="hidden" name="basename" value="%%basename%%" />
	<input type="hidden" name="locale.language" value="%%language%%" />
	<input type="hidden" name="locale.country" value="%%country%%" />
	<input type="hidden" name="locale.variant" value="%%variant%%" />
	<input type="hidden" name="key" value="%%key%%" />
	
	<div class="thislanguage" style="width: 49%; float:right;">
		<p style="margin-bottom: 0.5em;">
			<small><spring:message code="i18n.messages.definedin"/>: %%locale%% </small>
		</p>

		<textarea class="messagetextarea" "rows="5" name="message" style="width:100%;border: 1px solid lightgray;" >%%message%%</textarea>
		<input type="submit" value="<spring:message code="core.ui.save"/>" /> 
		<input type="checkbox" checked="checked" name="finished" />
		<spring:message code="i18n.messages.finished"/>
	</div>
	
	<div style="float: right; width:2%">&nbsp;</div>
	<div class="otherlanguage" style="float: right; width: 49%;">
		<p style="margin-bottom: 0.5em; width: 100%; cursor:pointer;" onclick="$(this).parent().hide(); $(this).parent().siblings('.availableMessage').show(); return false;">
			<small>
				<spring:message code="i18n.messages.reference" />&nbsp;-&nbsp;
				<spring:message code="i18n.messages.reference.locale" />: %%reference_locale%% &nbsp;-&nbsp;
				<spring:message code="i18n.messages.reference.status" />: %%reference_status%%
			</small>
		</p>
		<textarea disabled="disabled" rows="5" style="width:100%;border: 1px solid lightgray;">%%reference_message%%</textarea>
	</div>
	
	<div class="availableMessage" style="float: right; width: 49%; display:none;">
		<p style="margin-bottom: 0.5em; width: 100%; cursor:pointer;" onclick="$(this).parent().hide(); $(this).parent().siblings('.otherlanguage').show(); return false;">
			<small>
				<spring:message code="i18n.messages.available" />
			</small>
		</p>
		<textarea disabled="disabled" rows="5" style="width:100%;border: 1px solid lightgray;">%%available_message%%</textarea>
	</div>
</form>


 
</div>
