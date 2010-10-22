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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
import org.synyx.minos.skillz.web.validation.ReferenceValidator;


/**
 * Controller for {@link Activity} web actions.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@Controller
public class ReferencesController {

    private static final String RESUME_REFERENCES = "/skillz/resume#tabs-2";

    private final SkillManagement skillManagement;
    private final ResumeManagement resumeManagement;
    private final ReferenceValidator referenceValidator;


    /**
     * @param resumeManagement
     * @param skillManagement
     */
    @Autowired
    public ReferencesController(ResumeManagement resumeManagement, SkillManagement skillManagement,
            ReferenceValidator referenceValidator) {

        this.skillManagement = skillManagement;
        this.resumeManagement = resumeManagement;
        this.referenceValidator = referenceValidator;
    }


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        DateTimeEditor editor = new DateTimeEditor(locale, "MM/yyyy");
        editor.forDateMidnight();

        binder.registerCustomEditor(DateMidnight.class, editor);
    }


    @RequestMapping(value = "/skillz/resume/references/{id}", method = DELETE)
    public String deleteActivity(@PathVariable("id") Activity reference, Model model) {

        resumeManagement.delete(reference);

        model.addAttribute(Core.MESSAGE, Message.success("skillz.reference.delete.success"));

        return UrlUtils.redirect(RESUME_REFERENCES);
    }


    @RequestMapping(value = "/skillz/resume/references", method = POST)
    public String createActivity(@ModelAttribute("reference") Activity reference, Errors errors, Model model,
            @CurrentUser User user) {

        return saveActivity(reference, errors, model, user);
    }


    @RequestMapping(value = "/skillz/resume/references/{id}", method = PUT)
    public String updateActivity(@ModelAttribute("reference") Activity reference, Errors errors, Model model,
            @CurrentUser User user) {

        return saveActivity(reference, errors, model, user);
    }


    private String saveActivity(Activity reference, Errors errors, Model model, User user) {

        referenceValidator.validate(reference, errors);

        if (errors.hasErrors()) {
            return prepareActivtyForm(reference, model, user);
        }

        Activity result = resumeManagement.save(reference);

        model.addAttribute(Core.MESSAGE, Message
                .success("skillz.reference.save.success", result.getProject().getName()));

        return UrlUtils.redirect(RESUME_REFERENCES);
    }


    @RequestMapping(value = { "/skillz/resume/references/form" }, method = GET)
    public String showActivityForm(Model model, @CurrentUser User user) {

        return prepareActivtyForm(BeanUtils.instantiateClass(Activity.class), model, user);
    }


    @RequestMapping(value = { "/skillz/resume/references/{id}" }, method = GET)
    public String showActivityForm(@PathVariable(value = "id") Activity reference, Model model, @CurrentUser User user) {

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
        model.addAttribute("responsibilities", skillManagement.getResponsibilities());

        return "skillz/resume/reference";
    }

}
