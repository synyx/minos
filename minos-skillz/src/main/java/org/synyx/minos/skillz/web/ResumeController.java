package org.synyx.minos.skillz.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.Minos;
import org.synyx.minos.core.web.PageWrapper;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Project;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.SkillManagement;


/**
 * Controller for web actions against {@link ResumeManagement}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes(types = { SkillMatrix.class, Resume.class })
public class ResumeController {

    private SkillManagement skillManagement;
    private ResumeManagement resumeManagement;


    /**
     * @param resumeManagement
     * @param skillManagement
     */
    @Autowired
    public ResumeController(ResumeManagement resumeManagement,
            SkillManagement skillManagement) {

        this.skillManagement = skillManagement;
        this.resumeManagement = resumeManagement;
    }


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        DateTimeEditor editor = new DateTimeEditor(locale, "MM/yyyy");
        editor.withAdditionalParsersFor("dd.MM.yyyy", "MM.yyyy");
        editor.forDateMidnight();

        binder.registerCustomEditor(DateMidnight.class, editor);
    }


    @RequestMapping("/skillz/matrix/form")
    public String matrix(Model model) {

        SkillMatrix matrix = resumeManagement.getResume().getSkillz();

        model.addAttribute("matrix", matrix);
        model.addAttribute("map", matrix.getMap());
        model.addAttribute("entry", matrix.getEntries().get(0));
        model.addAttribute("levels", skillManagement.getLevels());

        return "skillz/matrix";
    }


    @RequestMapping(value = "/skillz/matrix/form", method = POST)
    public String saveMatrix(@ModelAttribute("matrix") SkillMatrix matrix,
            SessionStatus session) {

        resumeManagement.save(matrix);
        session.setComplete();

        return UrlUtils.redirect("../resume");
    }


    @RequestMapping(value = "/skillz/reference/delete")
    public String deleteActivity(@RequestParam("id") Activity reference,
            Model model) {

        model.addAttribute(Minos.MESSAGE, Message
                .success("reference.delete.success"));

        return UrlUtils.redirect("../resume");
    }


    @RequestMapping(value = "/skillz/reference/form", method = RequestMethod.POST)
    public String saveActivity(@ModelAttribute("reference") Activity reference,
            Model model, SessionStatus session) {

        Activity result = resumeManagement.save(reference);
        session.setComplete();

        model.addAttribute(Minos.MESSAGE, Message.success(
                "activity.save.success", result.getProject().getName()));

        return UrlUtils.redirect("../resume");
    }


    @RequestMapping(value = "/skillz/reference/form")
    public String showActivityForm(
            @ModelAttribute("reference") Activity reference,
            @RequestParam(value = "id", required = false) Long id, Model model,
            @CurrentUser User user) {

        if (null != id) {
            reference = resumeManagement.getReference(id);
        }

        List<Project> projects = skillManagement.getPrivateProjectsFor(user);
        projects.addAll(skillManagement.getPublicProjects());

        model.addAttribute("reference", reference);
        model.addAttribute("projects", projects);
        model.addAttribute("responsibilities", resumeManagement
                .getResponsibilities());

        return "skillz/reference";
    }


    @RequestMapping(value = "/skillz/resume", method = GET)
    public String resume(Model model) {

        return showResume(resumeManagement.getResume(), model);
    }


    // Skillz administration

    @RequestMapping(value = "/skillz/resumes/{id}", method = GET)
    public String resume(@PathVariable("id") Long id, Model model) {

        return showResume(resumeManagement.getResume(id), model);
    }


    @RequestMapping(value = "/skillz/resumes", method = RequestMethod.POST)
    public String saveResume(@ModelAttribute("resume") Resume resume,
            Model model, SessionStatus conversation) {

        resumeManagement.save(resume);

        model.addAttribute(Minos.MESSAGE, Message
                .success("skillz.resume.save.success"));
        conversation.setComplete();

        return UrlUtils.redirect("resume");
    }


    @RequestMapping( { "/skillz/resumes" })
    public String resumes(Model model, Pageable pageable) {

        Page<Resume> resumes = resumeManagement.getResumes(pageable);
        model.addAttribute("resumes", PageWrapper.wrap(resumes));
        model.addAttribute("templates", skillManagement.getTemplates());

        return "skillz/resumes";
    }


    @RequestMapping(value = "/skillz/resumes", method = POST, params = "resumes")
    public String assignResumesToTemplate(
            @RequestParam("resumes") List<Resume> resumes,
            @RequestParam("template") MatrixTemplate template) {

        for (Resume resume : resumes) {
            resumeManagement.save(resume, template);
        }

        return UrlUtils.redirect("./resumes");
    }


    // Helper methods

    private String showResume(Resume resume, Model model) {

        if (null == resume) {
            model.addAttribute(Minos.MESSAGE, Message.error("resume.notfound"));
            return null;
        }

        model.addAttribute("resume", resume);
        model.addAttribute("levels", skillManagement.getLevels());

        return "skillz/resume";
    }
}
