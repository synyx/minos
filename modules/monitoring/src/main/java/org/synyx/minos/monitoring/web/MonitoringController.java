package org.synyx.minos.monitoring.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import org.synyx.minos.monitoring.service.MonitoringService;
import org.synyx.minos.monitoring.service.MonitoringTest;
import org.synyx.minos.monitoring.service.MonitoringTestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


@Controller
public class MonitoringController implements ApplicationContextAware {

    @Autowired
    private MonitoringService monitoringService;
    private ApplicationContext ctx;

    @PostConstruct
    public void init() {

        Map<String, AnnotationMethodHandlerAdapter> beans = ctx.getBeansOfType(AnnotationMethodHandlerAdapter.class);

        if (beans.size() > 0) {
            AnnotationMethodHandlerAdapter a = beans.values().iterator().next();
            HttpMessageConverter<?>[] messageConverters = a.getMessageConverters();
            List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
            list.add(new MappingJacksonHttpMessageConverter());

            for (HttpMessageConverter<?> c : messageConverters) {
                list.add(c);
            }

            a.setMessageConverters(list.toArray(new HttpMessageConverter[] {}));
        }
    }


    @RequestMapping(value = "/monitoring", method = GET)
    @ResponseBody
    public List<MonitoringTest> getMonitoringTests() {

        return monitoringService.getMonitoringTests();
    }


    @RequestMapping(value = "/monitoring/{name}", method = { GET, POST })
    @ResponseBody
    public MonitoringTestResult executeTest(@PathVariable("name") String name) {

        return monitoringService.executeTest(name);
    }


    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {

        this.ctx = ctx;
    }
}
