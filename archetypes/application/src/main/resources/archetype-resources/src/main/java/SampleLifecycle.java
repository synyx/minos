#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.core.annotation.Order;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.umt.dao.UserDao;

import ${package}.items.dao.TodoDao;
import ${package}.items.domain.TodoItem;


@Order(0)
public class SampleLifecycle extends SimpleNoOpLifecycle {

    private UserDao userDao;
    private TodoDao todoDao;

    public SampleLifecycle(UserDao userDao, TodoDao todoDao) {

        this.userDao = userDao;
        this.todoDao = todoDao;
    }

    @Override
    public void install() throws ModuleLifecycleException {

        User user = createExampleUser();
        createExampleEntries(user);
    }


    private User createExampleUser() {

        User user = new User("sampleuser", "user@example.com");
        userDao.save(user);

        return user;
    }


    private void createExampleEntries(User user) {

        for (int i = 0; i < 20; i++) {
            TodoItem item = new TodoItem();
            item.setDescription("Example todo item " + i);

            if (i % 7 == 0) {
                item.setDone(true);
            }

            todoDao.saveAndFlush(item);
            item.setCreatedBy(user);
            todoDao.saveAndFlush(item);
        }
    }
}
