package eapli.framework.infrastructure.authz.domain;

/**
 * @FIXME this enum is tightly coupled to the eCafeteria project. as such it is
 *        not a reusable concept for this generic infrastructure component
 */
public enum RoleType {
    /**
     * poweruser
     */
    POWER_USER,
    /**
     * Utente
     */
    CAFETERIA_USER,
    /**
     * Cafeteria Administrator
     */
    ADMIN,
    /**
     *
     */
    KITCHEN_MANAGER,
    /**
     *
     */
    MENU_MANAGER,
    /**
     *
     */
    CASHIER;

    /**
     * get available role types for user adding
     *
     * @return
     */
    public static RoleType[] nonUserValues() {
        return new RoleType[] { ADMIN, KITCHEN_MANAGER, MENU_MANAGER, CASHIER };
    }

    public boolean isCollaborator() {
        return this != CAFETERIA_USER;
    }
}
