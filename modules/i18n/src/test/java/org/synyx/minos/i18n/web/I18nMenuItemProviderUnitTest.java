package org.synyx.minos.i18n.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.synyx.tagsupport.tags.menu.MenuItem;

import java.util.List;


public class I18nMenuItemProviderUnitTest {

    private I18nMenuItemProvider i18nMenuItemProvider;

    @Before
    public void setUp() {

        i18nMenuItemProvider = new I18nMenuItemProvider();
    }


    @Test
    public void testInitMenuItems() {

        List<MenuItem> menuItems = i18nMenuItemProvider.initMenuItems();

        Assert.assertEquals(2, menuItems.size());
        Assert.assertEquals("MENU_I18N", menuItems.get(0).getId());
        Assert.assertEquals("MENU_I18N_BASENAMES", menuItems.get(1).getId());
    }
}
