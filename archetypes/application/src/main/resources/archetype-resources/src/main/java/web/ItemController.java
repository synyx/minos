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

import ${package}.dao.ItemDao;
import ${package}.domain.Item;
import ${package}.domain.ItemValidator;
import ${package}.domain.Status;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.Message;

import java.util.List;

import javax.annotation.security.RolesAllowed;


/*
 * While considered bad practice in general to have a transactional controller, we want our example to stay simple.
 */
@Controller
@Transactional
public class ItemController {

    public static final String SINGULAR = "item";
    public static final String PLURAL = "items";
    public static final String BASE_URL = "/" + PLURAL;

    public static final String FORM = "form";
    public static final String FORM_URL = BASE_URL + "/" + FORM;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemValidator itemValidator;

    @RequestMapping(value = BASE_URL, method = GET)
    public String showItems(Model model) {

        List<Item> items = itemDao.readAll();
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
        @PathVariable("id") Item item) {

        if (item == null) {
            return "redirect:" + BASE_URL;
        }

        model.addAttribute(SINGULAR, item);
        model.addAttribute("statusValues", Status.values());

        return FORM;
    }


    @RequestMapping(value = FORM_URL, method = GET)
    public String showCreateFormForItem(Model model,
        @ModelAttribute(SINGULAR) Item item) {

        return prepareForm(model, item);
    }


    private String prepareForm(Model model, Item item) {

        model.addAttribute(SINGULAR, item);
        model.addAttribute("statusValues", Status.values());

        return FORM;
    }


    @RequestMapping(value = BASE_URL, method = POST)
    public String createItem(Model model,
        @ModelAttribute(SINGULAR) Item item, Errors itemErrors) {

        return save(model, item, itemErrors);
    }


    private String save(Model model, Item item, Errors itemErrors) {

        itemValidator.validate(item, itemErrors);

        if (itemErrors.hasErrors()) {
            return prepareForm(model, item);
        }

        itemDao.saveAndFlush(item);

        /*
         * The Minos core module and default layout already supports short flash messages
         * to display to the user. The ${package}.core.web.Message class supports
         * several styles, including success messages, errors, notices and also warnings.
         * The content is taken from the configured message properties.
         */
        model.addAttribute(Core.MESSAGE, Message.success("items.save.success"));

        return "redirect:" + BASE_URL + "/" + item.getId();
    }


    @RequestMapping(value = BASE_URL + "/{id}", method = PUT)
    public String updateItem(Model model,
        @ModelAttribute(SINGULAR) Item item, Errors itemErrors,
        @PathVariable("id") Long id) {

        // set id to merge model with database
        item.setId(id);

        return save(model, item, itemErrors);
    }


    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = BASE_URL + "/{id}", method = DELETE)
    public String deleteItem(Model model,
        @PathVariable("id") Item entity) {

        itemDao.delete(entity);
        model.addAttribute(Core.MESSAGE, Message.success("items.delete.success"));

        return "redirect:" + BASE_URL;
    }
}
