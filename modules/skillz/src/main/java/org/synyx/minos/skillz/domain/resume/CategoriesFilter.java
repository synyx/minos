package org.synyx.minos.skillz.domain.resume;

import org.synyx.minos.skillz.dao.CategoryDao;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.resume.ResumeFilterParameters.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;


/**
 * A implementation of {@link ResumeFilter} which returns resumes by there name and level.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class CategoriesFilter extends ResumeFilterSupport {

    private final CategoryDao categoryDao;

    public CategoriesFilter(CategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilter#getMessageKey()
     */
    @Override
    public String getMessageKey() {

        return "skillz.categories";
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilter#getParameters()
     */
    @Override
    public ResumeFilterParameters getParameters() {

        return new Builder().addMultipleChoice("categories", Category.class, new ReferenceDataContainer(categoryDao),
                "skillz.categories").build();
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilterSupport#getQueryPartString ()
     */
    @Override
    public String getQueryPartString() {

        return
            "JOIN x.skillz.template.categories categories WHERE EXISTS(SELECT c FROM categories c WHERE c.name IN (:categories))";
    }


    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.domain.resume.ResumeFilterSupport#manualBindParameters (javax.persistence.Query,
     * java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void bindParameters(Query query, Map<String, Object> parameters) {

        List<String> categoryNames = new ArrayList<String>();

        for (Category category : (List<Category>) parameters.get("categories")) {
            categoryNames.add(category.getName());
        }

        // Prevent JPQL exception when list is empty
        if (categoryNames.isEmpty()) {
            categoryNames.add("");
        }

        query.setParameter("categories", categoryNames);
    }
}
