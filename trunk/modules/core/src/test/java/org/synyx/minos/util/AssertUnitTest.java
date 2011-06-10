package org.synyx.minos.util;

import org.junit.Test;

import static org.mockito.Mockito.mock;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.domain.AbstractPersistable;

import java.util.Collection;
import java.util.HashSet;


/**
 * Unit test for {@link Assert}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
public class AssertUnitTest {

    private static final String ASSERT_TXT = "Hallo Synyx!";
    private static final String ASSERT_MSG = "Hallo Kunde!";
    private static final String EMPTY_STRING = "";
    private Collection<User> justCollection = new HashSet<User>();

    private UserDao userDao = mock(UserDao.class);
    private User user = mock(User.class);

    @Test
    public void testIsTrue() {

        Assert.isTrue(ASSERT_TXT.equals("Hallo Synyx!"), "test 1");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIsTrueThrowsException() {

        Assert.isTrue(ASSERT_TXT.equals(ASSERT_MSG), "test 2");
    }


    @Test
    public void testIsFalse() {

        Assert.isFalse(ASSERT_TXT.equals(ASSERT_MSG));
    }


    @Test
    public void testHasText() {

        Assert.hasText(ASSERT_TXT, "test 4");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testHasTextThrowsException() {

        Assert.hasText(EMPTY_STRING, "test 5");
    }


    @Test
    public void testNotBlank() {

        Assert.notBlank(ASSERT_TXT);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNotBlankThrowsException() {

        Assert.notBlank(EMPTY_STRING);
    }


    @Test
    public void testNotEmpty() {

        User user1 = new User(22);
        User user2 = new User(33);

        justCollection.add(user1);
        justCollection.add(user2);

        Assert.notEmpty(justCollection);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyThrowsException() {

        Assert.notEmpty(justCollection);
    }


    @Test
    public void testNotNull() {

        Assert.notNull(ASSERT_MSG);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNotNullThrowsException() {

        Assert.notNull(null);
    }


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
