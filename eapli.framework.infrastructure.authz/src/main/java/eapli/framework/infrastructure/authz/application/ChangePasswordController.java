/**
 *
 */
package eapli.framework.infrastructure.authz.application;

import org.springframework.beans.factory.annotation.Autowired;

import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Password;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class ChangePasswordController implements Controller {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * default constructor for Spring dependency injection
     */
    protected ChangePasswordController() {
        // empty
    }

    /**
     * in scenarios where there is no dependency injector, this constructor allows
     * the controller to be be built and manually inject the dependency
     *
     * @param authenticationService
     */
    public ChangePasswordController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public boolean changePassword(final String oldPassword, final String newPassword)
            throws ConcurrencyException, IntegrityViolationException {
        return authenticationService.changePassword(authenticationService.session().authenticatedUser(),
                Password.valueOf(oldPassword), Password.valueOf(newPassword));
    }
}
