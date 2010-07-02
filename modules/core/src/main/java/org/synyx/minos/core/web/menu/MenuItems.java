package org.synyx.minos.core.web.menu;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;


/**
 * Immutable wrapper for a list of {@link MenuItem}s. Allows accessing a filtered view of {@link MenuItems} by invoking
 * {@link #filter(Iterable)}.
 * 
 * @author Oliver Gierke
 */
public class MenuItems implements Iterable<MenuItem> {

    private final List<MenuItem> menuItems;


    /**
     * Creates a new {@link MenuItems} for the given {@link List} of {@link MenuItem}s.
     * 
     * @param menuItems
     */
    public MenuItems(List<MenuItem> menuItems) {

        this.menuItems = menuItems;
    }


    /**
     * Creates a new {@link MenuItems} for the given array of {@link MenuItem}s.
     * 
     * @param menuItems
     */
    public MenuItems(MenuItem... menuItems) {

        this(asList(menuItems));
    }


    /**
     * Returns a new {@link MenuItems} instance with deep copies of all {@link MenuItem}s that satisfy all of the given
     * {@link Predicate}s.
     * 
     * @param filter
     * @return
     */
    public MenuItems filter(Iterable<? extends Predicate<MenuItem>> filter) {

        List<MenuItem> result = new ArrayList<MenuItem>();
        Predicate<MenuItem> allFilters = Predicates.and(filter);

        for (MenuItem item : this) {
            if (allFilters.apply(item)) {
                result.add(item.deepCopy(allFilters));
            }
        }

        return new MenuItems(result);
    }


    /**
     * Returns whether the {@link MenuItems} instance is empty.
     * 
     * @return
     */
    public boolean isEmpty() {

        return menuItems.isEmpty();
    }


    /**
     * Returns the number of {@link MenuItem}s contained.
     * 
     * @return
     */
    public int size() {

        return menuItems.size();
    }


    /**
     * Returns whether the {@link MenuItems} contain the given {@link MenuItem}.
     * 
     * @param menuItem
     * @return
     */
    public boolean contains(MenuItem menuItem) {

        for (MenuItem item : menuItems) {

            if (item.equals(menuItem) || item.hasSubMenuItem(menuItem)) {
                return true;
            }
        }

        return false;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<MenuItem> iterator() {

        return menuItems.iterator();
    }
}
