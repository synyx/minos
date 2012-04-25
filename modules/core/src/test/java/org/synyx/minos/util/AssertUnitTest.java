package org.synyx.minos.util;

import org.junit.Test;

import static org.mockito.Mockito.mock;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.AbstractPersistable;


/**
 * Unit test for {@link Assert}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
public class AssertUnitTest {

    private UserDao userDao = mock(UserDao.class);
    private User user = mock(User.class);

    @Test
    public void testNotNew() {

        user = new User(1);
        userDao.readByPrimaryKey(1);

        Assert.notNew(user);
    }

    /**
     * Sample generic DAO interface.
     */
    private static interface UserDao extends GenericDao<User, Integer> {
    }

    /**
     * Sample entity.
     */
    @SuppressWarnings("serial")
    private static class User extends AbstractPersistable<Integer> {

        public User(Integer id) {

            setId(id);
        }
    }
}
