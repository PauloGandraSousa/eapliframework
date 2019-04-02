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
    private AuthorizationService authorizationService;
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
    public ChangePasswordController(final AuthorizationService authorizationService,
            final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    public boolean changePassword(final String oldPassword, final String newPassword)
            throws ConcurrencyException, IntegrityViolationException {
        // authenticationService.session().map(s ->
        // authenticationService.changePassword(s.authenticatedUser(),
        // Password.valueOf(oldPassword), Password.valueOf(newPassword))).orElse(other);

        return authenticationService.changePassword(authorizationService.session().get().authenticatedUser(),
                Password.valueOf(oldPassword), Password.valueOf(newPassword));
    }
}
