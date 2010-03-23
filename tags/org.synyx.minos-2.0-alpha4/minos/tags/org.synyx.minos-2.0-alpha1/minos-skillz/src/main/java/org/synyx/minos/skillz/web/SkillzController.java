package org.synyx.minos.skillz.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Locale;

import org.joda.time.DateMidnight;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.Minos;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Skill;
import org.synyx.minos.skillz.service.SkillManagement;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Controller to expose {@link SkillManagement} functionality to the web.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes(types = { Project.class, Category.class })
public class SkillzController {

    private SkillManagement skillManagement;
    private UserManagement userManagement;


    /**
     * Creates a new {@link SkillzController} instance.
     */
    @Autowired
    public SkillzController(SkillManagement skillManagement,
            UserManagement userManagement) {

        this.skillManagement = skillManagement;
        this.userManagement = userManagement;
    }


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        binder.registerCustomEditor(DateMidnight.class, new DateTimeEditor(
                locale, "MM/yyyy").forDateMidnight());
    }


    @RequestMapping("/skillz")
    public String indexRedirect() {

        return UrlUtils.redirect("/skillz/");
    }


    @RequestMapping("/skillz/")
    public String index(Model model) {

        model.addAttribute("levels", skillManagement.getLevels());
        model.addAttribute("projects", skillManagement.getPublicProjects());
        model.addAttribute("categories", skillManagement.getCategories());
        model.addAttribute("templates", skillManagement.getTemplates());

        return "skillz/skillz";
    }


    @RequestMapping(value = { "/skillz/categories/{id}",
            "/skillz/categories/form{id}" }, method = GET)
    public String category(@PathVariable("id") Category category, Model model) {

        model.addAttribute("category", null == category ? BeanUtils
                .instantiateClass(Category.class) : category);
        model.addAttribute("categories", skillManagement.getCategories());

        return "skillz/category";
    }


    @RequestMapping(value = "/skillz/categories", method = POST)
    public String saveCategory(@ModelAttribute("category") Category category,
            Model model) {

        skillManagement.save(category);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.category.save.success", category.getName()));

        return UrlUtils.redirect("../skillz");
    }


    @RequestMapping(value = "/skillz/categories/{id}", method = DELETE)
    public String deleteCategory(@PathVariable("id") Category category,
            Model model) {

        if (null == category) {
            model.addAttribute(Minos.MESSAGE, Message
                    .error("skillz.category.delete.error"));
            return UrlUtils.redirect("../../");
        }

        skillManagement.delete(category);
        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.category.delete.success", category.getName()));
        return UrlUtils.redirect("../");
    }


    // Manage Skillz

    /**
     * Moves the skill to the given {@link Category}.
     * 
     * @param skill the {@link Skill} to be moved
     * @param category the targed {@link Category} to move the {@link Skill} to
     * @param model
     * @return
     */
    @RequestMapping(value = "/skillz/skill", method = POST)
    public String moveSkill(@RequestParam Skill skill,
            @RequestParam Category category, Model model) {

        Category currentCategory = skill.getCategory();

        skillManagement.moveSkill(skill, category);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.skill.save.success", skill.getName()));

        return UrlUtils.redirect("categories/form?id="
                + currentCategory.getId());
    }


    @RequestMapping(value = { "/skillz", "/skillz/" }, method = POST)
    public String saveSkill(@ModelAttribute Skill skill, Model model) {

        Skill savedSkill = skillManagement.save(skill);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.skill.save.success", skill.getName()));

        return UrlUtils.redirect("categories/"
                + savedSkill.getCategory().getId());
    }


    @RequestMapping(value = "/skillz/delete")
    public String deleteSkill(@RequestParam("id") Skill skill, Model model) {

        skillManagement.delete(skill);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.skill.delete.success", skill.getName()));

        return UrlUtils.redirect("categories/" + skill.getCategory().getId());
    }


    // Manage projects

    @RequestMapping("/skillz/projects/{username}")
    public String showUserProjects(@PathVariable("username") String username,
            Model model) {

        User user = userManagement.getUser(username);
        model.addAttribute("projects", skillManagement
                .getPrivateProjectsFor(user));
        model.addAttribute("projectsUrl", "/skillz/projects/" + username);
        model.addAttribute("username", username);

        return "skillz/projects";
    }


    @RequestMapping("/skillz/projects/form")
    public String showProjectForm(
            @RequestParam(value = "id", required = false) Project project,
            Model model) {

        if (null == project) {
            project = BeanUtils.instantiateClass(Project.class);
        }

        model.addAttribute("project", project);

        return "skillz/project";
    }


    @RequestMapping("/skillz/projects/{username}/form")
    public String privateProjectForm(
            @RequestParam(value = "id", required = false) Project project,
            Model model, @CurrentUser User user) {

        if (null == project) {

            project = BeanUtils.instantiateClass(Project.class);

        } else {

            if (!project.belongsTo(user)) {
                model.addAttribute(Minos.MESSAGE, "skillz.project.id.invalid");
                return UrlUtils.redirect("/skillz/projects/private");
            }
        }

        model.addAttribute("project", project);
        model.addAttribute("owner", user);

        return "skillz/project";
    }


    @RequestMapping(value = { "/skillz/projects/form" }, method = POST)
    public String savePublicProject(@ModelAttribute("project") Project project,
            Model model, SessionStatus conversation) {

        saveProject(project, model, conversation);

        return UrlUtils.redirect("../");
    }


    @RequestMapping(value = { "/skillz/projects/{username}/form" }, method = POST)
    public String save(@ModelAttribute("project") Project project,
            @PathVariable("username") String username, Model model,
            SessionStatus conversation) {

        saveProject(project, model, conversation);

        return UrlUtils.redirect("./");
    }


    /**
     * Invokes the service layer to save the given project.
     * 
     * @param project
     * @param model
     * @param conversation
     */
    private void saveProject(Project project, Model model,
            SessionStatus conversation) {

        project = skillManagement.save(project);
        conversation.setComplete();

        model.addAttribute(Minos.MESSAGE, Message.success(
                "skillz.project.save.success", project.getName()));
    }


    @RequestMapping("/skillz/projects/delete")
    public String deleteProject(@RequestParam("id") Project project, Model model) {

        skillManagement.delete(project);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "project.delete.success", project.getName()));

        return UrlUtils.redirect("../");
    }


    /**
     * Shows the form for a {@link MatrixTemplate}.
     * 
     * @param template
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/skillz/templates/form")
    public String showTemplate(
            @ModelAttribute("template") MatrixTemplate template,
            @RequestParam(value = "id", required = false) Long id, Model model) {

        if (null != id) {
            model.addAttribute("template", skillManagement.getTemplate(id));
        }

        model.addAttribute("categories", skillManagement.getCategories());

        return "skillz/template";
    }


    /**
     * Saves a {@link MatrixTemplate}.
     * 
     * @param template
     * @param model
     * @return
     */
    @RequestMapping(value = "/skillz/templates/form", method = POST)
    public String saveTemplate(
            @ModelAttribute("template") MatrixTemplate template, Model model) {

        MatrixTemplate result = skillManagement.save(template);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "template.save.success", result.getName()));

        return UrlUtils.redirect("../");
    }


    @RequestMapping(value = "/skillz/templates/delete")
    public String deleteTemplate(@RequestParam("id") MatrixTemplate template,
            Model model) {

        skillManagement.delete(template);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "template.delete.success", template.getName()));

        return UrlUtils.redirect("../");
    }
}
