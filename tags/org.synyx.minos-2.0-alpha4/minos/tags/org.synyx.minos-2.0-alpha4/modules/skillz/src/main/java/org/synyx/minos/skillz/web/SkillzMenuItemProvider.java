package org.synyx.minos.skillz.web;

import static org.synyx.minos.skillz.SkillzPermissions.*;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;



/**
 * {@link org.synyx.minos.core.web.menu.MenuItemProvider} for Skillz module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SkillzMenuItemProvider extends AbstractMenuItemProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem item =
                new MenuItem("skillz.menu", 20, "/skillz").add(SKILLZ_USER);

        item.withSub(new MenuItem("skillz.menu.manageResumes",
                10, "/skillz/resumes").add(SKILLZ_ADMINISTRATION));
        item.withSub(new MenuItem("skillz.menu.skillz", 20, "/skillz")
                .add(SKILLZ_ADMINISTRATION));

        item.withSub(new MenuItem("skillz.menu.resume", 40, "/skillz/resume")
                .add(SKILLZ_USER));
        item.withSub(new MenuItem("skillz.menu.projects.private", 50, String.format("/skillz/projects/%s",
                        MenuItem.USER_PLACEHOLDER))
                .add(SKILLZ_USER));

        return Arrays.asList(item);
    }
}
