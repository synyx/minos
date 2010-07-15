<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="core" tagdir="/WEB-INF/tags/core" %>
<%@ taglib prefix="skillz" tagdir="/WEB-INF/tags/skillz" %>

<script type="text/javascript">
    <!--

        window.onload = function() {

        $("#tabs").tabs();
    }
    -->
</script>

<div id="tabs">

    <ul>
        <li><a href="#tabs-1" class="iconized categories"><spring:message code="skillz.categories" /></a></li>
        <li><a href="#tabs-2" class="iconized templates"><spring:message code="skillz.templates" /></a></li>
        <li><a href="#tabs-3" class="iconized projects"><spring:message code="skillz.projects" /></a></li>
        <li><a href="#tabs-4" class="iconized levels"><spring:message code="skillz.levels" /></a></li>
    </ul>

    <div class="tab" id="tabs-1">
        <display:table id="category" name="categories" requestURI="#tabs-1" >
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
                <core:deleteLink href="categories/${category.id}" imageUrl="/images/skillz/category_delete.png" />
            </minos:column>
            <display:footer>
                <tr>
                    <td colspan="5">
                <core:imageLink href="categories/form" imageUrl="/images/skillz/category_add.png" altKey="skillz.category.new" />
                </td>
                </tr>
            </display:footer>
        </display:table>
    </div>

    <div class="tab" id="tabs-2">
        <display:table id="template" name="templates" requestURI="#tabs-2" >
            <minos:column titleKey="name">
                <a href="templates/${template.id}"><c:out value="${template.name}" /></a>
            </minos:column>
            <minos:column titleKey="skillz.skills">
                <c:forEach items="${template.categories}" var="category">
                    ${category.name},&nbsp;
                </c:forEach>
            </minos:column>
            <minos:column property="lastModifiedDate" class="date meta" />
            <minos:column property="lastModifiedBy" class="meta" />
            <minos:column class="actions">
                <core:imageLink href="templates/${template.id}" imageUrl="/images/skillz/template_edit.png" altKey="core.ui.edit" />
                <core:deleteLink href="templates/${template.id}" imageUrl="/images/skillz/template_delete.png" />
            </minos:column>
            <display:footer>
                <tr>
                    <td colspan="5">
                <core:imageLink href="templates/form" imageUrl="/images/skillz/template_add.png" altKey="skillz.template.new" />
                </td>
                </tr>
            </display:footer>
        </display:table>
    </div>

    <div class="tab" id="tabs-3">
        <skillz:projects project="${project}" />
    </div>

    <div class="tab" id="tabs-4">
        <display:table id="level" name="levels" requestURI="#tabs-4">
            <minos:column titleKey="name">
                <a href="levels/${level.id}"><c:out value="${level.name}" /></a>
            </minos:column>
            <minos:column property="lastModifiedDate" class="date meta" />
            <minos:column property="lastModifiedBy" class="meta" />
            <minos:column class="actions" titleKey="skillz.level" sortable="false">
                <c:if test="${level_rowNum > 1}">
                    <skillz:levelMoveLink href="levels/${level.id}/up" imageUrl="/images/skillz/arrow_up.png" altKey="skillz.level.up" />
                </c:if>
                <c:if test="${level_rowNum < levelsSize}">
                    <skillz:levelMoveLink href="levels/${level.id}/down" imageUrl="/images/skillz/arrow_down.png" altKey="skillz.level.down" />
                </c:if>
            </minos:column>
            <minos:column class="actions">
                <core:imageLink href="levels/${level.id}" imageUrl="/images/skillz/level_edit.png" altKey="core.ui.edit" />
                <core:deleteLink href="levels/${level.id}" imageUrl="/images/skillz/level_delete.png" />
            </minos:column>
            <display:footer>
                <tr>
                    <td colspan="4">
                <core:imageLink href="levels/form" imageUrl="/images/skillz/level_add.png" altKey="skillz.level.new" />
                </td>
                </tr>
            </display:footer>
        </display:table>
    </div>

</div>
