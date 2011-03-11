#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.core.annotation.Order;

import org.springframework.util.Assert;

import ${package}.dao.ItemDao;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;


@Order(0)
public class SampleLifecycle extends SimpleNoOpLifecycle {

    private ItemDao itemDao;

    public SampleLifecycle(ItemDao itemDao) {

        this.itemDao = itemDao;
    }

    @Override
    public void install() throws ModuleLifecycleException {

        Assert.notNull(itemDao, "We need an item dao to work");
    }
}
