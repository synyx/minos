/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class Menu {

    private List<MenuItem> items;


    public Menu(MenuItem... items) {

        this.items = new ArrayList<MenuItem>();
        for (MenuItem item : items) {
            this.items.add(item);
        }
    }


    public Menu(List<MenuItem> items) {

        super();
        this.items = items;
    }


    /**
     * @param items the items to set
     */
    public void setItems(List<MenuItem> items) {

        this.items = items;
    }


    /**
     * @return the items
     */
    public List<MenuItem> getItems() {

        return items;
    }


    public boolean hasMenuItem(String id) {

        return getMenuItem(id, items) != null;
    }


    public MenuItem getMenuItem(String id) {

        return getMenuItem(id, items);
    }


    public static MenuItem getMenuItem(String id, List<MenuItem> currentItems) {

        if (currentItems == null) {
            return null;
        }

        for (MenuItem item : currentItems) {
            if (id.equals(item.getId())) {
                return item;
            }

            if (item.hasSubMenues()) {
                MenuItem subItem = getMenuItem(id, item.getSubMenues());
                if (subItem != null) {
                    return subItem;
                }

            }
        }

        return null;
    }
}
