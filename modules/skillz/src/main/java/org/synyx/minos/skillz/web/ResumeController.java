package org.synyx.minos.skillz.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.Image;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.PageWrapper;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.validation.MultipartFileValidator;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.SkillMatrix;
import org.synyx.minos.skillz.domain.resume.ResumeAttributeFilter;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;
import org.synyx.minos.skillz.service.DocbookCreationException;
import org.synyx.minos.skillz.service.PdfDocbookCreator;
import org.synyx.minos.skillz.service.ResumeAdminstration;
import org.synyx.minos.skillz.service.ResumeManagement;
import org.synyx.minos.skillz.service.ResumeZipCreator;
import org.synyx.minos.skillz.service.SkillManagement;
import org.synyx.minos.skillz.service.ZipCreationException;


/**
 * Controller for web actions against {@link ResumeManagement}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Markus Knittig - knittig@synyx.de
 */
@Controller
@SessionAttributes(types = { SkillMatrix.class, Resume.class })
public class ResumeController {

    private static final String RESUME = "/skillz/resume";
    private static final String RESUMES = "/skillz/resumes";

    private static final int THUMBNAIL_WIDTH = 200;

    private final SkillManagement skillManagement;
    private final ResumeManagement resumeManagement;
    private final ResumeAdminstration resumeAdminstration;
    private final PdfDocbookCreator pdfDocbookCreator;
    private final ResumeZipCreator resumeZipCreator;

    private MultipartFileValidator multipartValidator =
            new MultipartFileValidator();


    /**
     * Creates a new {@link ResumeController}.
     * 
     * @param resumeManagement
     * @param skillManagement
     * @param resumeAdminstration
     * @param pdfDocbookCreator
     * @param resumeZipCreator
     */
    @Autowired
    public ResumeController(ResumeManagement resumeManagement,
            SkillManagement skillManagement,
            ResumeAdminstration resumeAdminstration,
            PdfDocbookCreator pdfDocbookCreator,
            ResumeZipCreator resumeZipCreator) {

        this.skillManagement = skillManagement;
        this.resumeManagement = resumeManagement;
        this.resumeAdminstration = resumeAdminstration;
        this.pdfDocbookCreator = pdfDocbookCreator;
        this.resumeZipCreator = resumeZipCreator;
    }


    /**
     * Configure a custom {@link MultipartFileValidator}. By default the
     * controller will use a {@link MultipartFileValidator}.
     * 
     * @param multipartValidator the multipartValidator to set
     */
    public void setMultipartValidator(MultipartFileValidator multipartValidator) {

        this.multipartValidator = multipartValidator;
    }


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        DateTimeEditor editor = new DateTimeEditor(locale, "dd.MM.yyyy");
        editor.withAdditionalParsersFor("MM/dd/yyyy", "dd.MM.yyyy");
        editor.forDateMidnight();

        binder.registerCustomEditor(DateMidnight.class, editor);
    }


    @RequestMapping(value = "/skillz/resume/matrix/form", method = GET)
    public String matrix(Model model, @CurrentUser User user) {

        SkillMatrix matrix = resumeManagement.getResume(user).getSkillz();

        model.addAttribute("matrix", matrix);
        model.addAttribute("map", matrix.getMap());
        model.addAttribute("entry", matrix.getEntries().get(0));
        model.addAttribute("levels", skillManagement.getLevels());

        return "skillz/matrix";
    }


    @RequestMapping(value = "/skillz/resume/matrix/{id}", method = PUT)
    public String saveExistingMatrix(
            @ModelAttribute("matrix") SkillMatrix matrix, SessionStatus session) {

        resumeManagement.save(matrix);
        session.setComplete();

        return UrlUtils.redirect("/skillz/resume#tabs-3");
    }


    /**
     * Show the current {@link User}'s {@link Resume}.
     * 
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = RESUME, method = GET)
    public String resume(Model model, @CurrentUser User user) {

        model.addAttribute("owner", user);
        return showResume(resumeManagement.getResume(user), model);
    }


    /**
     * Creates the current {@link User}'s {@link Resume} as a PDF file in a
     * temporary directory and redirects to it if the creation was successful,
     * show an error message otherwise.
     * 
     * @param user
     * @param response
     * @param webRequest
     * @param outputStream
     * @return
     */
    @RequestMapping(value = RESUME, method = POST, params = "pdfexport")
    public String resumePdf(@CurrentUser User user, Model model,
            HttpSession session, WebRequest webRequest) {

        File file = null;
        try {
            file =
                    pdfDocbookCreator
                            .createTempPdfFile(
                                    getServletTempDirectory(session
                                            .getServletContext()),
                                    resumeManagement
                                            .getFilteredResume(
                                                    user,
                                                    getResumeAttributeFilters(webRequest)),
                                    skillManagement.getLevels());
        } catch (DocbookCreationException e) {
            model.addAttribute(Core.MESSAGE, Message
                    .error("skillz.resume.export.pdf.failed"));
            return resume(model, user);
        }

        return UrlUtils.redirect("/skillz/resume/pdf/" + file.getName());

    }


    /**
     * Stream's the {@link Resume} with the given file name as PDF.
     * 
     * @param filename
     * @param response
     * @param session
     * @param outputStream
     * @throws IOException
     */
    @RequestMapping(value = "/skillz/resume/pdf/{filename}", method = GET)
    public void streamResumePdf(@PathVariable("filename") String filename,
            HttpServletResponse response, HttpSession session,
            OutputStream outputStream) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition",
                "attachment; filename=resume.pdf");

        streamFile(session.getServletContext(), filename, outputStream);

    }


    /**
     * Creates the current {@link User}'s {@link Resume} as as a ZIP file in a
     * temporary directory and redirects to it if the creation was successful,
     * show an error message otherwise.
     * 
     * @param user
     * @param response
     * @param webRequest
     * @param outputStream
     * @return
     */
    @RequestMapping(value = RESUME, method = POST, params = "zipexport")
    public String resumeZip(@CurrentUser User user, Model model,
            HttpSession session, WebRequest webRequest) {

        File file = null;
        try {
            file =
                    resumeZipCreator
                            .createTempZipFile(
                                    getServletTempDirectory(session
                                            .getServletContext()),
                                    resumeManagement
                                            .getFilteredResume(
                                                    user,
                                                    getResumeAttributeFilters(webRequest)),
                                    skillManagement.getLevels());
        } catch (ZipCreationException e) {
            model.addAttribute(Core.MESSAGE, Message
                    .error("skillz.resume.export.zip.failed"));
            return resume(model, user);
        }

        return UrlUtils.redirect("/skillz/resume/zip/" + file.getName());
    }


    /**
     * Stream's the {@link Resume} with the given file name as ZIP.
     * 
     * @param filename
     * @param session
     * @param response
     * @param outputStream
     * @throws IOException
     */
    @RequestMapping(value = "/skillz/resume/zip/{filename}", method = GET)
    public void streamResumeZip(@PathVariable("filename") String filename,
            HttpSession session, HttpServletResponse response,
            OutputStream outputStream) throws IOException {

        response.setContentType("application/zip");
        response.setHeader("content-disposition",
                "attachment; filename=resume.zip");

        streamFile(session.getServletContext(), filename, outputStream);

    }


    /**
     * Streams a {@link File} from the temporary directory of the servlet
     * container and deletes it afterwards.
     * 
     * @param servletContext
     * @param filename
     * @param outputStream
     * @throws IOException
     */
    private void streamFile(ServletContext servletContext, String filename,
            OutputStream outputStream) throws IOException {

        File file = new File(getServletTempDirectory(servletContext), filename);
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(file);
            IOUtils.copy(inputStream, outputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
            FileUtils.deleteQuietly(file);
        }
    }


    /**
     * Gets the temporary directory of the servlet container from a
     * {@link ServletContext}.
     * 
     * @param servletContext
     * @return
     */
    private File getServletTempDirectory(ServletContext servletContext) {

        return (File) servletContext
                .getAttribute("javax.servlet.context.tempdir");
    }


    /**
     * Returns all {@link ResumeAttributeFilter}s which are requested in the
     * {@link WebRequest}.
     * 
     * @param webRequest
     * @return
     */
    private List<ResumeAttributeFilter> getResumeAttributeFilters(
            WebRequest webRequest) {

        List<ResumeAttributeFilter> filters =
                new ArrayList<ResumeAttributeFilter>();

        for (ResumeAttributeFilter filter : resumeManagement
                .getResumeAttributeFilters()) {
            String parameterValue =
                    webRequest.getParameter(filter.getMessageKey());
            if ("1".equals(parameterValue)) {
                filters.add(filter);
            }
        }

        return filters;
    }


    // Skillz administration

    @RequestMapping(value = "/skillz/resumes/{id}", method = GET)
    public String resume(@PathVariable("id") Long id, Model model) {

        return showResume(resumeManagement.getResume(id), model);
    }


    /**
     * Saves a resume instance.
     * 
     * @param resume
     * @param model
     * @param conversation
     * @return
     */
    @RequestMapping(value = "/skillz/resumes/{id}", method = PUT)
    public String saveResume(@ModelAttribute("resume") Resume resume,
            Model model, SessionStatus conversation, @CurrentUser User user) {

        resumeManagement.save(resume);

        model.addAttribute(Core.MESSAGE, Message
                .success("skillz.resume.save.success"));
        conversation.setComplete();

        if (resume.getSubject().equals(user)) {
            return UrlUtils.redirect(RESUME);
        } else {
            return UrlUtils.redirect(RESUMES);
        }
    }


    @RequestMapping(value = "/skillz/resumes/{id}/photo", method = GET)
    public void showResumePhoto(@PathVariable("id") Long id,
            OutputStream outputStream) throws IOException {

        Resume resume = resumeManagement.getResume(id);
        if (resume.getPhoto() != null) {
            FileCopyUtils.copy(resume.getPhoto().getThumbnail(), outputStream);
        }
    }


    // TODO: Use only PUT request. Doesn't work currently with multipart
    // requests. See https://jira.springsource.org/browse/SPR-6594
    @RequestMapping(value = "/skillz/resumes/{id}/photo", method = { POST, PUT })
    public String saveResumePhoto(@ModelAttribute("resume") Resume resume,
            Errors errors, Model model,
            @RequestParam("photoBinary") MultipartFile image)
            throws IOException {

        multipartValidator.validate(image, errors);

        if (errors.hasErrors()) {
            model.addAttribute(Core.MESSAGE, Message.error(errors
                    .getGlobalError().getCode()));
        } else {
            String fileExtension =
                    StringUtils.getFilenameExtension(image
                            .getOriginalFilename());
            resume.setPhoto(new Image(image.getBytes(), THUMBNAIL_WIDTH,
                    fileExtension));
            resumeManagement.save(resume);

            model.addAttribute(Core.MESSAGE, Message
                    .success("skillz.resume.photo.save.success"));
        }

        return UrlUtils.redirect(RESUME);
    }


    @RequestMapping(value = "/skillz/resumes/{id}/photo", method = DELETE)
    public String deleteResumePhoto(@PathVariable("id") Resume resume,
            Model model) {

        resume.setPhoto(null);
        resumeManagement.save(resume);

        model.addAttribute(Core.MESSAGE, Message
                .success("skillz.resume.photo.delete.success"));

        return UrlUtils.redirect(RESUME);
    }


    @RequestMapping(value = RESUMES, method = GET)
    public String resumes(Model model, Pageable pageable) {

        Page<Resume> resumes = resumeAdminstration.getResumes(pageable);
        model.addAttribute("resumes", PageWrapper.wrap(resumes));
        model.addAttribute("templates", skillManagement.getTemplates());
        if (!model.containsAttribute("resumeFilter")
                && !resumeAdminstration.getResumeFilters().isEmpty()) {
            ResumeFilter resumeFilter =
                    resumeAdminstration.getResumeFilters().get(0);
            model.addAttribute("resumeFilter", resumeFilter);
        }
        model.addAttribute("resumeFilters", resumeAdminstration
                .getResumeFilters());

        return "skillz/resumes";
    }


    @RequestMapping(value = RESUMES, method = GET, params = "selectFilter")
    public String resumes(Model model, Pageable pageable,
            @RequestParam String selectFilter) {

        ResumeFilter resumeFilter =
                resumeAdminstration.getResumeFilter(selectFilter);
        model.addAttribute("resumeFilter", resumeFilter);
        return resumes(model, pageable);
    }


    @RequestMapping(value = RESUMES, method = GET, params = "filterName")
    public String resumes(Model model, Pageable pageable,
            @RequestParam String filterName, WebRequest webRequest) {

        ResumeFilter resumeFilter =
                resumeAdminstration.getResumeFilter(filterName);
        Page<Resume> resumes =
                resumeAdminstration.getResumesByFilter(pageable, resumeFilter,
                        requestParametersAsMap(webRequest));
        model.addAttribute("resumeFilter", resumeFilter);
        model.addAttribute("resumeFilters", resumeAdminstration
                .getResumeFilters());
        model.addAttribute("resumes", PageWrapper.wrap(resumes));
        model.addAttribute("templates", skillManagement.getTemplates());

        return "skillz/resumes";
    }


    private Map<String, String[]> requestParametersAsMap(WebRequest webRequest) {

        Map<String, String[]> parameters = new HashMap<String, String[]>();
        Iterator<String> iter = webRequest.getParameterNames();
        while (iter.hasNext()) {
            String parameterName = iter.next();
            parameters.put(parameterName, webRequest
                    .getParameterValues(parameterName));

        }
        return parameters;
    }


    @RequestMapping(value = RESUMES, method = POST, params = "resumes")
    public String assignResumesToTemplate(
            @RequestParam("resumes") List<Resume> resumes,
            @RequestParam("template") MatrixTemplate template) {

        for (Resume resume : resumes) {
            resumeManagement.save(resume, template);
        }

        return UrlUtils.redirect(RESUMES);
    }


    // Helper methods

    private String showResume(Resume resume, Model model) {

        if (null == resume) {
            model.addAttribute(Core.MESSAGE, Message.error("resume.notfound"));
            return null;
        }

        model.addAttribute("filters", resumeManagement
                .getResumeAttributeFilters());
        model.addAttribute("resume", resume);
        model.addAttribute("levels", skillManagement.getLevels());

        return "skillz/resume";
    }
}
