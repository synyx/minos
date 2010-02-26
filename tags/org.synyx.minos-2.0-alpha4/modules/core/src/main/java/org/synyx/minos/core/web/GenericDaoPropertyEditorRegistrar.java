package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.util.ClassUtils;


/**
 * Simple helper class to use Hades DAOs to provide {@link PropertyEditor}s for
 * domain classes. Use a {@link CustomEditorConfigurer} and provide a reference
 * to an instance of this class for automatic registration of a
 * {@link PropertyEditor} for each {@link GenericDao}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@SuppressWarnings("unchecked")
public class GenericDaoPropertyEditorRegistrar implements
        PropertyEditorRegistrar, ApplicationContextAware {

	private Map<Class<?>, GenericDao> daoMap;


    /**
     * Derives the domain class from the given DAO class.
     * 
     * @param dao
     * @return
     */
    private Class<?> getDomainClass(GenericDao<?, ?> dao) {

        return ClassUtils.getDomainClass(dao.getClass());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.PropertyEditorRegistrar#registerCustomEditors
     * (org.springframework.beans.PropertyEditorRegistry)
     */
	public void registerCustomEditors(PropertyEditorRegistry registry) {

        for (Entry<Class<?>, GenericDao> entry : daoMap.entrySet()) {

            registry.registerCustomEditor(entry.getKey(), EntityPropertyEditor
                    .create((GenericDao<?, Long>) entry.getValue()));
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        Collection<GenericDao> daos =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(
                        applicationContext, GenericDao.class).values();

        this.daoMap = new HashMap<Class<?>, GenericDao>(daos.size());

        for (GenericDao<?, ?> dao : daos) {

            this.daoMap.put(getDomainClass(dao), dao);
        }
    }
}
