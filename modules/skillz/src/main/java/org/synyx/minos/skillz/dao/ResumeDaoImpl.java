package org.synyx.minos.skillz.dao;

import org.apache.commons.lang.StringUtils;

import static org.synyx.hades.dao.query.QueryUtils.READ_ALL_QUERY;
import static org.synyx.hades.dao.query.QueryUtils.applySorting;
import static org.synyx.hades.dao.query.QueryUtils.getQueryString;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.skillz.domain.Resume;
import org.synyx.minos.skillz.domain.resume.ResumeFilter;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * DAO implementation for {@link ResumeDaoCustom}.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class ResumeDaoImpl implements ResumeDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.skillz.dao.ResumeDaoCustom#findByFilter(org.synyx.hades .domain.Pageable,
     * org.synyx.minos.skillz.domain.resume.ResumeFilter, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Resume> findByFilter(Pageable pageable, ResumeFilter resumeFilter, Map<String, Object> parameters) {

        String queryString = createQueryString(pageable, resumeFilter);
        Query jpaQuery = createJpaQuery(pageable, resumeFilter, parameters, queryString);

        return jpaQuery.getResultList();
    }


    private Query createJpaQuery(Pageable pageable, ResumeFilter resumeFilter, Map<String, Object> parameters,
        String queryString) {

        Query jpaQuery = entityManager.createQuery(queryString);

        if (resumeFilter.getQueryPartString() != null) {
            resumeFilter.bindParameters(jpaQuery, parameters);
        }

        return jpaQuery;
    }


    private String createQueryString(Pageable pageable, ResumeFilter resumeQuery) {

        return applySorting(getQueryString(READ_ALL_QUERY, Resume.class) + " "
                + StringUtils.defaultString(resumeQuery.getQueryPartString()), pageable.getSort());
    }
}
