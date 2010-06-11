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


    public MenuItem removeMenuItem(String id) {

        return removeMenuItem(id, items);
    }


    public static MenuItem removeMenuItem(String id, List<MenuItem> currentItems) {

        if (currentItems == null) {
            return null;
        }

        Iterator<MenuItem> itemIter = currentItems.iterator();
        while (itemIter.hasNext()) {
            MenuItem item = itemIter.next();
            if (id.equals(item.getId())) {
                itemIter.remove();
                return item;
            }

            if (item.hasSubMenues()) {
                MenuItem subItem = removeMenuItem(id, item.getSubMenues());
                if (subItem != null) {
                    return subItem;
                }

            }
        }

        return null;
    }

}
