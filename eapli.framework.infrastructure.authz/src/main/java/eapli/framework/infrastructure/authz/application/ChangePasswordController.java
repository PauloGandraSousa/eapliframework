/**
 *
 */
package eapli.framework.infrastructure.authz.application;

import org.springframework.beans.factory.annotation.Autowired;

import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.domain.Password;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class ChangePasswordController implements Controller {
    @Autowired
    private AuthenticationService authenticationService;

    public boolean changePassword(final String oldPassword, final String newPassword)
            throws DataConcurrencyException, DataIntegrityViolationException {
        return authenticationService.changePassword(authenticationService.session().authenticatedUser(),
                Password.valueOf(oldPassword), Password.valueOf(newPassword));
    }
}
