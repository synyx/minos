<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="matrix" type="org.synyx.minos.skillz.domain.SkillMatrix"%>
<%@ attribute name="levels" type="java.util.Collection"%>
<%@ attribute name="editable" type="java.lang.Boolean" required="false"%>
<%@ attribute name="id" type="java.lang.String"%>

<core:if test="${editable}">
<style type="text/css" media="screen">
<!--
.highlight {
	background: #ddd
}
-->
</style>

<script type="text/javascript">
<!--

	$(function() {
		var columns = '#${id} tr.skillzSkillRow > td';

		$(columns).hover(function() {
			$(this).addClass('highlight');
			$(this).siblings('tr.skillzSkillRow > td').addClass('highlight');
		}, function() {
			$(columns).removeClass('highlight');
		});
	});

//-->
</script>
</core:if>

<core:if test="${not editable}">
<style type="text/css" media="screen">
<!--
.editableRow {
	cursor: pointer;
}
-->
</style>

<script type="text/javascript">
<!--

$(function() {
	var url = '<spring:url value="/web/skillz/resume/matrix/form" />';
	var matrixFooter = 'tr#footer';
	var matrixForm = "form#matrix";

	var clicked = false;

	$('.skillzSkillRow').addClass('editableRow');

	/**
	 * Activate in-place editing on row double click. Only one row
	 * can be edited at a time.
	 */
	$('#${id} tr.skillzSkillRow').click(function() {
		if (!clicked) {
			var rowHtml = $(this).html();
			var matrixFooterHtml = $(matrixFooter).html();
			var clickedRow = "#" + $(this).attr('id');

			clicked = true;

			$('.skillzSkillRow').removeClass('editableRow');

			/**
			 * Load form page, extract clicked row and footer and
			 * replace the corresponding elements in the current page.
			 */
			$.get(url, function(data) {
				$(clickedRow).html($(clickedRow, data).html());
				$(matrixFooter).html($(matrixFooter, data).html());
			});

			/**
			 * Submits the form and display the resulting row with
			 * the old footer.
			 */
			$(matrixForm).submit(function() {
				$.post(url,
					$(matrixForm).serialize(),
					function(data) {
						resetClickedRowAndFooter($(clickedRow, data).html());
					}
				);
				return false;
			});

			/**
			 * Restore the old read-only row and footer.
			 */
			$('a.cancel').live('click', function() {
				resetClickedRowAndFooter(rowHtml);
				return false;
			});

			/**
			 * Resets a previously clicked row and the matrixFooter.
			 * Unbinds submit button and cancel link event handlers.
			 *
			 * @param html The HTML content of the clicked row
			 */
			function resetClickedRowAndFooter(html) {
				$(clickedRow).html(html);
				$(matrixFooter).html(matrixFooterHtml);

				$(matrixForm).unbind('submit');
				$('a.cancel').die('click');

				$('.skillzSkillRow').addClass('editableRow');
				clicked = false;
			}
		}
	});
});

//-->
</script>
</core:if>

<form:form modelAttribute="matrix">
	<table id="${id}" class="form">
		<tr>
			<th></th>
			<core:forEach items="${levels}" var="level">
				<th class="skillzSkillLevel">${level.name}</th>
			</core:forEach>
		</tr>
		<core:forEach items="${matrix.map}" var="entry">
			<tbody>
				<core:set var="category" value="${entry.key}" />
				<core:set var="entries" value="${entry.value}" />

				<tr>
					<th colspan="${fn:length(levels) + 1}" class="skillzCategory">${category.name}</th>
				</tr>

				<core:forEach items="${entries}" var="entry">
					<spring:nestedPath path="entries[${entry.index}]">
						<tr class="skillzSkillRow" id="skillzSkillRow${entry.index}">
							<td class="skillzSkill">${entry.name}</td>
							<core:forEach items="${levels}" var="level">
								<core:choose>
									<core:when test="${editable}">
										<td class="skillzSkillLevel<core:if test="${!entry.acknowledged}"> attention</core:if>">
											<form:radiobutton path="level" id="${level.id}" value="${level}" />
										</td>
									</core:when>
									<core:otherwise>
										<td class="skillzSkillLevel">
											<core:if test="${level eq entry.level}">
												X
												<input type="hidden" name="entries[${entry.index}].level" value="${level.id}" />
											</core:if>
										</td>
									</core:otherwise>
								</core:choose>
							</core:forEach>
						</tr>
					</spring:nestedPath>
				</core:forEach>
			</tbody>
		</core:forEach>
		<tfoot>
			<tr id="footer">
				<core:choose>
					<core:when test="${editable}">
						<td colspan="${fn:length(levels) + 1}">
							<input type="submit" value="<spring:message code="core.ui.ok" />" />
							<a class="cancel" href="/minos/web/skillz/resume#tabs-3"><spring:message code="core.ui.cancel" /></a>
						</td>
					</core:when>
					<core:otherwise>
						<td></td>
						<td colspan="${fn:length(levels)}"><a
							href="resume/matrix/form"><spring:message
							code="skillz.skillMatrix.edit" /></a></td>
					</core:otherwise>
				</core:choose>
			</tr>
		</tfoot>
	</table>
</form:form>