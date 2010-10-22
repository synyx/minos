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
public class MenuItems implements Iterable<Menu> {

    public static final MenuItems EMPTY = new MenuItems(new ArrayList<Menu>());

    private final List<Menu> items;


    /**
     * Creates a new {@link Menu} for the given {@link List} of {@link Menu}s.
     * 
     * @param menuItems
     */
    public MenuItems(List<Menu> menuItems) {

        this.items = menuItems;
    }


    /**
     * Creates a new {@link Menu} for the given array of {@link Menu}s.
     * 
     * @param menuItems
     */
    public MenuItems(Menu... items) {

        this(asList(items));
    }


    /**
     * Returns a new {@link Menu} instance with deep copies of all {@link Menu}s that satisfy all of the given
     * {@link Predicate}s.
     * 
     * @param filter
     * @return
     */
    public MenuItems filter(Iterable<? extends Predicate<Menu>> filter) {

        List<Menu> result = new ArrayList<Menu>();
        Predicate<Menu> allFilters = Predicates.and(filter);

        for (Menu item : this) {
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

        return items.isEmpty();
    }


    /**
     * Returns the number of {@link Menu}s contained.
     * 
     * @return
     */
    public int size() {

        return items.size();
    }


    /**
     * Returns whether the {@link Menu} contain the given {@link Menu}.
     * 
     * @param menuItem
     * @return
     */
    public boolean contains(Menu menuItem) {

        for (Menu item : items) {

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
    public Iterator<Menu> iterator() {

        return items.iterator();
    }
}
