package org.synyx.minos.core.web;

import org.springframework.beans.BeanUtils;

import org.springframework.core.annotation.AnnotationUtils;

import org.springframework.ui.Model;

import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import org.synyx.hades.domain.Persistable;

import org.synyx.minos.core.Core;

import java.io.Serializable;

import java.util.List;

import javax.servlet.http.HttpSession;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class GenericCrudControllerSupport<BeanType extends Persistable<PK>, PK extends Serializable>
    extends ValidationSupport<BeanType> {

    private static final String SAVE_ERROR_MESSAGE_TEMPLATE = "minos.%s.save.success";
    private static final String SAVE_SUCCESS_MESSAGE_TEMPLATE = "minos.%s.save.error";

    private static final String DELETE_ERROR_MESSAGE_TEMPLATE = "minos.%s.delete.success";
    private static final String DELETE_SUCCESS_MESSAGE_TEMPLATE = "minos.%s.delete.error";

    protected String modelAttribute;
    protected String mappingRoot;
    protected Class<BeanType> beanClass;

    protected GenericCrudControllerSupport() {
    }


    public GenericCrudControllerSupport(Class<BeanType> beanClass) {

        readAnnotations();

        if (modelAttribute == null || mappingRoot == null) {
            throw new RuntimeException("Could not read annotations");
        }
    }

    /**
     * Reads the first value of {@link RequestMapping} and {@link SessionAttributes}
     */
    protected void readAnnotations() {

        RequestMapping requestMapping = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);

        if (requestMapping != null) {
            mappingRoot = ((String[]) AnnotationUtils.getValue(requestMapping))[0];
        }

        SessionAttributes sessionAttributes = this.getClass().getAnnotation(SessionAttributes.class);

        if (sessionAttributes != null) {
            modelAttribute = ((String[]) AnnotationUtils.getValue(sessionAttributes))[0];
        }
    }


    /**
     * Prepares the {@link Model} with an instance of BeanType according to the given id or with an new one
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showForm(@PathVariable("id") BeanType bean, Model model, WebRequest request) {

        return prepareForm(bean, model, request);
    }


    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showBean(Model model, WebRequest request) {

        return prepareForm(createNewBean(beanClass, model, request), model, request);
    }


    /**
     * Hook before showing a bean-form. An implementing subclass may do any preparations here. The bean that is returned
     * from this method will be added to the model.
     *
     * @param bean the bean that should be showed
     * @param model the model
     * @param request the request
     * @return the bean to be added to the model
     */
    protected String prepareForm(BeanType bean, Model model, WebRequest request) {

        model.addAttribute(modelAttribute, bean);

        return mappingRoot + "/form";
    }


    /**
     * Create a new instance of {@link BeanType}. This method is used when preparing the {@link Model} for the create-form.
     *
     * @param beanClass class of the Bean to be created
     * @return a new Instance of BeanType
     */
    protected BeanType createNewBean(Class<BeanType> beanClass, Model model, WebRequest request) {

        return BeanUtils.instantiateClass(beanClass);
    }


    /**
     * Saves an instance of BeanType using its DAO.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String createOrEdit(BeanType bean, Errors errors, Model model, WebRequest request,
        SessionStatus conversation) {

        if (!isValid(bean, errors) && prepareCreateOrEdit(bean, errors, model, request)) {
            model.addAttribute(Core.MESSAGE, Message.error(getMessageKey(SAVE_SUCCESS_MESSAGE_TEMPLATE), bean.getId()));

            return showForm(bean, model, request);
        }

        BeanType newBean = save(bean);

        conversation.setComplete();
        model.addAttribute(modelAttribute, newBean);
        model.addAttribute(Core.MESSAGE,
            Message.success(getMessageKey(SAVE_SUCCESS_MESSAGE_TEMPLATE), newBean.getId()));

        return "redirect:";
    }


    /**
     * Hook before saving a bean (create or edit). An implementing subclass may do any preparations here. If false is
     * returned, the bean is not saved but displayed again (see {@link #showForm(Persistable, Model, WebRequest)}).
     *
     * @param bean the bean that should be deleted
     * @param errors validation/binding result
     * @param model the model
     * @param request the request
     * @return true if the bean should be saved
     */
    protected boolean prepareCreateOrEdit(BeanType bean, Errors errors, Model model, WebRequest request) {

        return true;
    }


    /**
     * Deletes an Instance of BeanType using its Dao
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") BeanType bean, Model model, WebRequest request, HttpSession session) {

        if (prepareDelete(bean, model, request)) {
            delete(bean);

            model.addAttribute(Core.MESSAGE,
                Message.success(getMessageKey(DELETE_SUCCESS_MESSAGE_TEMPLATE), bean.getId()));

            return "redirect:";
        }

        model.addAttribute(Core.MESSAGE, Message.error(getMessageKey(DELETE_ERROR_MESSAGE_TEMPLATE), bean.getId()));

        return list(model, request, session);
    }


    private String getMessageKey(String template) {

        return String.format(template, modelAttribute);
    }


    /**
     * Hook before deleting a bean. An implementing subclass may do any preparations here. If false is returned, the
     * bean is not deleted but displayed again (see {@link #showForm(Persistable, Model, WebRequest)}).
     *
     * @param bean the bean that should be deleted
     * @param model the model
     * @param request the request
     * @return true if the bean should be deleted
     */
    protected boolean prepareDelete(BeanType bean, Model model, WebRequest request) {

        return true;
    }


    /**
     * Lists all existing instances of BeanType
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, WebRequest request, HttpSession session) {

        List<BeanType> beans = readAll();

        beans = prepareList(beans, model, request);
        model.addAttribute(modelAttribute, beans);

        return mappingRoot + "/list";
    }


    /**
     * Hook before listing all beans. An implementing subclass may do any preparations here. The list that is returned
     * from this method will be added to the model.
     *
     * @param beans the list of beans that should be listed
     * @param model the model
     * @param request the request
     * @return the list to be added to the model
     */
    protected List<BeanType> prepareList(List<BeanType> beans, Model model, WebRequest request) {

        return beans;
    }


    /**
     * Save the actual entity.
     *
     * @param bean
     * @return
     */
    protected abstract BeanType save(BeanType bean);


    /**
     * Perform the actual delete operation.
     *
     * @param bean
     */
    protected abstract void delete(BeanType bean);


    protected abstract List<BeanType> readAll();
}
