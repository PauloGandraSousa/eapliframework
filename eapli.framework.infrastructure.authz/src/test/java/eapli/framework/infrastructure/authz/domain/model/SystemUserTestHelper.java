package eapli.framework.infrastructure.authz.domain.model;

/**
 * @author Paulo Gandra de Sousa
 */
public final class SystemUserTestHelper {
    private static SystemUserBuilder userBuilder;

    public static SystemUser dummyUser(final String username, final Role... roles) {
        return userBuilder.withData(username, "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(roles).build();
    }
}
