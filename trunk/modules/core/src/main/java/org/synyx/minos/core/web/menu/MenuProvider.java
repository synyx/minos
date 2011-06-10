/**
 *
 */
package org.synyx.minos.core.web.menu;

/**
 * Interface for Instances that are able to generate a {@link MenuItems}
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MenuProvider {

    MenuItems getMenu(String id);
}
