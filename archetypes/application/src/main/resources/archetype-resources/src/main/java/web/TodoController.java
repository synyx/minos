#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;

import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.Message;

import ${package}.items.dao.TodoDao;
import ${package}.items.domain.TodoItem;
import ${package}.items.domain.TodoItemValidator;

import java.util.List;


/*
 * While considered bad practice in general to have a transactional controller, we want our example to stay simple.
 */
@Controller
@Transactional
public class TodoController {

    public static final String SINGULAR = "todo";
    public static final String PLURAL = "todos";
    public static final String BASE_URL = "/" + PLURAL;

    public static final String FORM = "form";
    public static final String FORM_URL = BASE_URL + "/" + FORM;

    @Autowired
    private TodoDao todoDao;

    @Autowired
    private TodoItemValidator todoItemValidator;

    @RequestMapping(value = BASE_URL, method = GET)
    public String showItems(Model model) {

        List<TodoItem> items = todoDao.readAll();
        model.addAttribute(PLURAL, items);

        return PLURAL;
    }


    /*
     * Hades, which generates the DAOs from interface specifications, makes it possible
     * to reference entities directly via their primary key from a @PathVariable. This
     * greatly simplifies controller code.
     */
    @RequestMapping(value = BASE_URL + "/{id}", method = GET)
    public String showItem(Model model,
        @PathVariable("id") TodoItem item) {

        model.addAttribute(SINGULAR, item);

        return SINGULAR;
    }


    @RequestMapping(value = FORM_URL, method = GET)
    public String showCreateFormForItem(Model model,
        @ModelAttribute(SINGULAR) TodoItem item) {

        return prepareForm(model, item);
    }


    private String prepareForm(Model model, TodoItem item) {

        model.addAttribute(SINGULAR, item);

        return FORM;
    }


    @RequestMapping(value = BASE_URL, method = POST)
    public String createItem(Model model,
        @ModelAttribute(SINGULAR) TodoItem item, Errors itemErrors) {

        return save(model, item, itemErrors);
    }


    private String save(Model model, TodoItem item, Errors itemErrors) {

        todoItemValidator.validate(item, itemErrors);

        if (itemErrors.hasErrors()) {
            return prepareForm(model, item);
        }

        todoDao.saveAndFlush(item);

        /*
         * The Minos core module and default layout already supports short flash messages
         * to display to the user. The ${package}.core.web.Message class supports
         * several styles, including success messages, errors, notices and also warnings.
         * The content is taken from the configured message properties.
         */
        model.addAttribute(Core.MESSAGE, Message.success("todos.item.save.success"));

        return "redirect:" + BASE_URL + "/" + item.getId();
    }


    @RequestMapping(value = BASE_URL + "/{id}", method = PUT)
    public String updateItem(Model model,
        @ModelAttribute(SINGULAR) TodoItem item, Errors itemErrors,
        @PathVariable("id") Long id) {

        item.setId(id);

        return save(model, item, itemErrors);
    }


    @RequestMapping(value = BASE_URL + "/{id}", method = DELETE)
    public String deleteItem(Model model,
        @PathVariable("id") TodoItem entity) {

        todoDao.delete(entity);
        model.addAttribute(Core.MESSAGE, Message.success("todos.item.delete.success"));

        return "redirect:" + BASE_URL;
    }
}
