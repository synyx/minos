package org.synyx.minos.core.web.tags.table;

import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.DefaultDecoratorFactory;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.TableDecorator;

import org.displaytag.exception.DecoratorInstantiationException;

import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;


/**
 * Custom {@link DecoratorFactory} to lookup decorators from the Spring {@link ApplicationContext}. This allows you to
 * define Spring bean names in {@code column} tags.
 *
 * @see org.synyx.minos.core.web.tags.table.ColumnTag
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SpringDecoratorFactory extends DefaultDecoratorFactory {

    /*
     * (non-Javadoc)
     *
     * @see org.displaytag.decorator.DefaultDecoratorFactory#loadColumnDecorator( javax.servlet.jsp.PageContext,
     * java.lang.String)
     */
    @Override
    public DisplaytagColumnDecorator loadColumnDecorator(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException {

        ApplicationContext context = getApplicationContext(pageContext);

        for (String name : context.getBeanNamesForType(DisplaytagColumnDecorator.class)) {
            if (name.equals(decoratorName)) {
                return (DisplaytagColumnDecorator) context.getBean(decoratorName);
            }
        }

        return super.loadColumnDecorator(pageContext, decoratorName);
    }


    /*
     * (non-Javadoc)
     *
     * @see org.displaytag.decorator.DefaultDecoratorFactory#loadTableDecorator(javax .servlet.jsp.PageContext,
     * java.lang.String)
     */
    @Override
    public TableDecorator loadTableDecorator(PageContext pageContext, String decoratorName)
        throws DecoratorInstantiationException {

        ApplicationContext context = getApplicationContext(pageContext);

        for (String name : context.getBeanNamesForType(TableDecorator.class)) {
            if (name.equals(decoratorName)) {
                return (TableDecorator) context.getBean(decoratorName);
            }
        }

        return super.loadTableDecorator(pageContext, decoratorName);
    }


    /**
     * Returns the {@link ApplicationContext} tied to the given {@link PageContext}.
     *
     * @param pageContext
     * @return
     */
    private ApplicationContext getApplicationContext(PageContext pageContext) {

        return WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    }
}
