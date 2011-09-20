package org.synyx.minos.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.joda.time.DateTime;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.util.StringUtils;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Exception-Handler that generates an Error-ID and writes a corresponding file containing detailled information about
 * the exception before forwarding to the error-view.
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 */
public class ErrorReportExceptionHandler extends SimpleMappingExceptionResolver implements HandlerExceptionResolver,
    InitializingBean {

    private AuthenticationService authenticationService;

    private Log LOG = LogFactory.getLog(ErrorReportExceptionHandler.class);
    private File reportBasePath;

    private Random random = new Random();

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception e) {

        // generate unique id
        DateTime now = new DateTime();
        String id = String.format("%s-%4d", now.toString("yyyy-MM-dd_HH-mm-ss"), random.nextInt(9999));

        request.setAttribute("exception_id", id);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);

        e.printStackTrace(new PrintStream(bos));
        request.setAttribute("exception_trace", bos.toString());

        File file = new File(reportBasePath, id + "-report.txt");

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.append("id:           " + id + "\n");
            writer.append("date:         " + now.toString("dd.MM.yyyy HH:mm:ss") + "\n");
            writer.append("message:      " + e.getMessage() + "\n\n");

            if (authenticationService != null) {
                User currentUser = authenticationService.getCurrentUser();

                if (currentUser != null) {
                    writer.append("user:         " + currentUser.getUsername() + " (email"
                        + currentUser.getEmailAddress().toString() + ")\n");
                }
            }

            writer.append("locale:       " + request.getLocale() + "\n");
            writer.append("remoteaddr:   " + request.getRemoteAddr() + "\n\n");

            writer.append("requesturl:   " + request.getRequestURL().toString() + "\n");
            appendParameters(request, writer);

            writer.append("handler:      " + handler.getClass().getName() + "\n");

            writer.append("\nstacktrace:\n");
            e.printStackTrace(writer);
            writer.append("\n\n");

            writer.close();
        } catch (FileNotFoundException e1) {
            LOG.info("Could not store report to " + file.getAbsolutePath() + ": " + e.getMessage(), e);
        }

        LOG.error(e);

        return super.doResolveException(request, response, handler, e);
    }


    private void appendParameters(HttpServletRequest request, PrintWriter writer) {

        @SuppressWarnings("unchecked")
        Enumeration<String> names = request.getParameterNames();

        writer.append("parameters:   ");

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            writer.append(String.format("%s=%s, ", name, request.getParameter(name)));
        }

        writer.append("\n");
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        if (reportBasePath == null) {
            reportBasePath = File.createTempFile("exceptions_", "");
            reportBasePath.delete();
            LOG.warn("No basepath given. Writing reports to temporary dir: " + reportBasePath.getAbsolutePath());
        }

        if (reportBasePath.exists() && !reportBasePath.isDirectory()) {
            throw new IllegalStateException("Directory " + reportBasePath.getAbsolutePath()
                + " exists and is a file (needed a directory)");
        } else if (!reportBasePath.exists()) {
            reportBasePath.mkdirs();
        }

        LOG.info("Writing error-reports to " + reportBasePath.getAbsolutePath());
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }


    public void setReportBasePath(String reportBasePath) {

        if (StringUtils.hasLength(reportBasePath)) {
            this.reportBasePath = new File(reportBasePath);
        }
    }
}
