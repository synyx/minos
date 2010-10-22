package org.synyx.minos.core.web;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.Persistable;


/**
 * Generic baseclass that is able to handle CRUD requests in an convention over configuration way This reads the
 * subclasses annotations {@link SessionAttributes} and {@link RequestMapping} and uses them to provide the following
 * mappings:
 * 
 * <pre>
 *   ($requestmapping is the first value of the RequestMapping-Annotation)
 *   ($modelattribute is the first value of the SessionAttributes-Annotation)
 *   $requestmapping/ with method GET  
 *       -&gt; list all beans (beans can be found in $modelattribute as a list)
 *       -&gt; use view $requestmapping/list
 *       
 *   $requestmapping/$id with method GET
 *       -&gt; read bean with $id or create new bean (can be found at $modelattribute)
 *       -&gt; use view $requestmapping/edit
 *       
 *   $requestmapping/$id with method POST
 *      -&gt; save bean and redirect to $requestmapping/
 *   
 *   $requestmapping/$id with method DELETE
 *      -&gt; delete bean and redirect to $requestmapping/
 * </pre>
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @param <BeanType>
 */
@Transactional
public class GenericCRUDController<BeanType extends Persistable<PK>, PK extends Serializable> extends
        GenericCrudControllerSupport<BeanType, PK> {

    private GenericDao<BeanType, PK> dao;


    /**
     * Protected Constructor for CGLib
     */
    protected GenericCRUDController() {

        super();
    }


    /**
     * Creates a new instance of {@link GenericCRUDController}
     * 
     * @param beanClass the Class of Beans to provide CRUD for
     * @param dao the Dao
     */
    public GenericCRUDController(Class<BeanType> beanClass, GenericDao<BeanType, PK> dao) {

        super(beanClass);
        this.dao = dao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.GenericCrudControllerSupport#save(org.synyx. hades.domain.Persistable)
     */
    @Override
    protected BeanType save(BeanType bean) {

        return dao.save(bean);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.GenericCrudControllerSupport#delete(org.synyx .hades.domain.Persistable)
     */
    @Override
    protected void delete(BeanType bean) {

        dao.delete(bean);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.GenericCrudControllerSupport#readAll()
     */
    @Override
    protected List<BeanType> readAll() {

        return dao.readAll();
    }
}
