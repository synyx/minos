package org.synyx.minos.skillz.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.synyx.minos.skillz.domain.MatrixTemplate;
import org.synyx.minos.test.AbstractDaoIntegrationTest;


/**
 * Integration test for {@link MatrixTemplateDao}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MatrixTemplateDaoIntegrationTest extends
        AbstractDaoIntegrationTest {

    @Autowired
    private MatrixTemplateDao templateDao;


    @Before
    public void setUp() {

        for (int i = 0; i < 10; i++) {

            MatrixTemplate template = new MatrixTemplate("Template" + i);

            if (i == 0) {
                template.setDefault(true);
            }

            templateDao.saveAndFlush(template);
        }
    }


    @Test
    public void undefaultsTemplatesCorrectly() throws Exception {

        List<MatrixTemplate> templates =
                templateDao.readByExample(new MatrixTemplate("Template8"));

        MatrixTemplate template = templates.get(0);
        template.setDefault(true);

        templateDao.undefaultAllBut(template);
        templateDao.save(template);

        assertTrue(templateDao.readByPrimaryKey(template.getId()).isDefault());
    }
}
