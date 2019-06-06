/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.infrastructure.authz.application;

import org.springframework.security.crypto.password.PasswordEncoder;

import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.util.Singleton;
import eapli.framework.validations.Invariants;

/**
 * Registry of all authz service objects. Helper class for scenarios without
 * spring Dependency Injection.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class AuthzRegistry implements Singleton {

    private static AuthorizationService authorizationSvc;
    private static AuthenticationService authenticationService;
    private static UserManagementService userService;

    private AuthzRegistry() {
        // ensure utility
    }

    /**
     * Helper method to initialize the registry in case you are not using Spring
     * Dependency Injection. This method should be called <strong>only
     * once</strong> on application startup.
     *
     * @param userRepo
     * @param policy
     * @param encoder
     */
    public static void configure(final UserRepository userRepo, final PasswordPolicy policy,
            final PasswordEncoder encoder) {
        authorizationSvc = new AuthorizationService();
        authenticationService = new AuthenticationService(userRepo, authorizationSvc, policy, encoder);
        userService = new UserManagementService(userRepo, policy, encoder);
    }

    /**
     *
     * @return the {@link UserManagementService}
     */
    public static UserManagementService userService() {
        Invariants.nonNull(userService);
        return userService;
    }

    /**
     *
     * @return the {@link AuthenticationService}
     */
    public static AuthenticationService authenticationService() {
        Invariants.nonNull(authenticationService);
        return authenticationService;
    }

    /**
     *
     * @return the {@link AuthorizationService}
     */
    public static AuthorizationService authorizationService() {
        Invariants.nonNull(authorizationSvc);
        return authorizationSvc;
    }
}
