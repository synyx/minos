/**
 *
 */
package org.synyx.minos.core.web.menu.filter;

import org.synyx.tagsupport.tags.menu.Menu;
import org.synyx.tagsupport.tags.menu.MenuItemFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link MenuItemFilter} that filters items that match a given {@link List} of Strings representing the Items ID-Paths.
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 */
public class PathMenuItemFilter implements MenuItemFilter {

    private List<String> pathsToFilter = new ArrayList<String>();

    public PathMenuItemFilter(List<String> pathsToFilter) {

        this.pathsToFilter.addAll(pathsToFilter);
    }


    public PathMenuItemFilter(String pathToFilter) {

        this.pathsToFilter.add(pathToFilter);
    }

    @Override
    public boolean apply(Menu input) {

        String itemPath = input.getPath();

        for (String filtered : pathsToFilter) {
            if (filtered.equals(itemPath)) {
                return false;
            }
        }

        return true;
    }
}
