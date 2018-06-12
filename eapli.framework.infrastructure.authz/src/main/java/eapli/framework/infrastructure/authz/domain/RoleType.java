package eapli.framework.infrastructure.authz.domain;

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
