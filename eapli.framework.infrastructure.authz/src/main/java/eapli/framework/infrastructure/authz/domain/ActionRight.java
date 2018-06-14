/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import java.util.Collection;

/**
 * @FIXME this enum is tightly coupled to the eCafeteria project. as such it is
 *        not a reusable concept for this generic infrastructure component
 * @author Paulo Gandra Sousa
 *
 */
public enum ActionRight {
    ADMINISTER, SELECT_MEAL, MANAGE_KITCHEN, MANAGE_MENUS, SALE, MANAGE_DELIVERY,;

    /**
     * checks if this action right can be performed by a user with the specified
     * role types
     *
     * @param roles
     * @return
     */
    public boolean canBePerformedBy(final Collection<RoleType> roles) {
        if (this == ADMINISTER && roles.contains(RoleType.ADMIN)) {
            return true;
        }
        if (this == SELECT_MEAL && roles.contains(RoleType.CAFETERIA_USER)) {
            return true;
        }
        if (this == MANAGE_KITCHEN && roles.contains(RoleType.KITCHEN_MANAGER)) {
            return true;
        }
        if (this == MANAGE_MENUS && roles.contains(RoleType.MENU_MANAGER)) {
            return true;
        }
        if (this == SALE && roles.contains(RoleType.CASHIER)) {
            return true;
        }
        if (this == MANAGE_DELIVERY && roles.contains(RoleType.CASHIER)) {
            return true;
        }
        return false;
    }
}
