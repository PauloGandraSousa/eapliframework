package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.domain.Password;
import eapli.framework.infrastructure.authz.domain.Role;
import eapli.framework.infrastructure.authz.domain.Username;

/**
 * Created by nuno on 21/03/16.
 */
@Component
public class LoginController implements Controller {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * This method allows a user to perform login and creates the session.
     *
     * @param userName
     * @param password
     */
    public boolean login(final String userName, final String password, final Role... onlyWithThis) {
        try {
            final Optional<UserSession> newSession = authenticationService.authenticate(Username.valueOf(userName),
                    Password.valueOf(password), onlyWithThis);
            AuthorizationService.setSession(newSession);
            return newSession.isPresent();
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }
}
