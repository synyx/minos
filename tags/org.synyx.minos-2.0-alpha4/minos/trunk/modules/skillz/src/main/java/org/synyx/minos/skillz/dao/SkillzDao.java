package org.synyx.minos.skillz.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.minos.skillz.domain.Category;
import org.synyx.minos.skillz.domain.Skill;



/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface SkillzDao extends GenericDao<Skill, Long> {

    List<Skill> findByCategory(Category category);
}
