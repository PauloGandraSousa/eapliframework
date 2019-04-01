/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * Created by nuno on 21/03/16.
 */
@Component
public class LoginController implements Controller {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * default constructor for Spring dependency injection
     */
    protected LoginController() {
        // empty
    }

    /**
     * in scenarios where there is no dependency injector, this constructor allows
     * the controller to be be built and manually inject the dependency
     *
     * @param authenticationService
     */
    public LoginController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * This method allows a user to perform login and creates the session.
     *
     * @param userName
     * @param password
     * @throws IllegalArgumentException
     *             if the username or password cannot be constructed from the passed
     *             string arguments
     */
    public boolean login(final String userName, final String password, final Role... onlyWithThis) {
        final Optional<UserSession> newSession = authenticationService.authenticate(Username.valueOf(userName),
                Password.valueOf(password), onlyWithThis);
        return newSession.isPresent();
    }
}
