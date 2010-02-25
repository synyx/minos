package org.synyx.minos.skillz.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.SkillManagement;


/**
 * Controller for {@link Activity} web actions.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@Controller
@SessionAttributes(types = { Activity.class })
public class ReferencesController {

    private final SkillManagement skillManagement;
    private final ResumeManagement resumeManagement;


    /**
     * @param resumeManagement
     * @param skillManagement
     */
    @Autowired
    public ReferencesController(ResumeManagement resumeManagement,
            SkillManagement skillManagement) {

        this.skillManagement = skillManagement;
        this.resumeManagement = resumeManagement;
    }


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        DateTimeEditor editor = new DateTimeEditor(locale, "MM/yyyy");
        editor.forDateMidnight();

        binder.registerCustomEditor(DateMidnight.class, editor);
    }


    @RequestMapping(value = "/skillz/resume/references/{id}", method = DELETE)
    public String deleteActivity(@PathVariable("id") Activity reference,
            Model model) {

        resumeManagement.delete(reference);

        model.addAttribute(Core.MESSAGE, Message
                .success("skillz.reference.delete.success"));

        return UrlUtils.redirect("../../resume#tabs-2");
    }


    @RequestMapping(value = "/skillz/resume/references", method = POST)
    public String createActivity(
            @ModelAttribute("reference") Activity reference, Model model,
            SessionStatus session) {

        saveActivity(reference, model, session);

        return UrlUtils.redirect("../resume#tabs-2");
    }


    @RequestMapping(value = "/skillz/resume/references/{id}", method = PUT)
    public String updateActivity(
            @ModelAttribute("reference") Activity reference, Model model,
            SessionStatus session) {

        saveActivity(reference, model, session);

        return UrlUtils.redirect("../../resume#tabs-2");
    }


    private void saveActivity(Activity reference, Model model,
            SessionStatus session) {

        Activity result = resumeManagement.save(reference);
        session.setComplete();

        model
                .addAttribute(Core.MESSAGE, Message.success(
                        "skillz.reference.save.success", result.getProject()
                                .getName()));
    }


    @RequestMapping(value = { "/skillz/resume/references/form" }, method = GET)
    public String showActivityForm(Model model, @CurrentUser User user) {

        return prepareActivtyForm(BeanUtils.instantiateClass(Activity.class),
                model, user);
    }


    @RequestMapping(value = { "/skillz/resume/references/{id}" }, method = GET)
    public String showActivityForm(
            @PathVariable(value = "id") Activity reference, Model model,
            @CurrentUser User user) {

        return prepareActivtyForm(reference, model, user);
    }


    /**
     * Prepares the references form.
     * 
     * @param reference
     * @param model
     * @param user
     * @return
     */
    private String prepareActivtyForm(Activity reference, Model model, User user) {

        List<Project> projects = skillManagement.getPrivateProjectsFor(user);
        projects.addAll(skillManagement.getPublicProjects());

        model.addAttribute("reference", reference);
        model.addAttribute("projects", projects);
        model.addAttribute("responsibilities", skillManagement
                .getResponsibilities());

        return "skillz/resume/reference";
    }

}
