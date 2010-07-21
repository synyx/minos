package org.synyx.minos.core.web.menu;

import com.google.common.base.Predicate;


/**
 * Simple typing interface for a {@link Predicate} over {@link MenuItem}s.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public interface MenuItemFilter extends Predicate<Menu> {

}
