/*
 * Copyright (c) 2013-2020 the original author or authors.
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
/**
 * <h1>Authentication and Authorizations</h1>
 *
 * <h2>General description</h2>
 *
 * A system user is (typically) a person who has been granted access to the
 * system. The user logins using her username and password; if the username and
 * password match the stored credentials, a user session is established for that
 * user: the user is authenticated. Each user can be assigned to one or more
 * roles which an application can use for separating access to different
 * features, enabling role-based authorizations. Sometimes the system
 * administrator can deactivate a user disabling her ability to login to the
 * system. A user can afterward be activated again. Each user can be unassigned
 * from a role but their assignment is kept for historic purposes. Checking the
 * roles of a user for authorization purposes only takes into account the
 * currently assigned roles, not the history. A role is simply a non empty label
 * with meaning to the client application (and possibly to a human user). An
 * assignment has a starting date and, if unassigned, an end date.
 *
 * <h2>Use cases</h2>
 *
 * <ul>
 * <li>Create user account
 * <li>Login
 * <li>Change password
 * <li>Reset the password of a user
 * <li>Activate/deactivate a user
 * <li>Check if a user has any of certain roles
 * <li>Get the currently authenticated user
 * <li>Check if the authenticated user has any of certain roles
 * </ul>
 *
 * <h2>Additional requirements</h2>
 *
 * <ul>
 * <li>An email, first name, last name must be kept for each system user
 * <li>Usernames must be unique in the system
 * <li>A password must be at least 6 characters, must contain at least one digit
 * and at least one capital letter
 * <li>Names must not be empty
 * <li>Email addresses must be syntactically valid
 * </ul>
 *
 * <h2>Main Artifacts</h2>
 *
 * <h3>Domain</h3>
 *
 * The aggregate root is
 * {@link eapli.framework.infrastructure.authz.domain.model.SystemUser
 * SystemUser} with a couple of value objects, namely
 * {@link eapli.framework.infrastructure.authz.domain.model.Username Username},
 * {@link eapli.framework.infrastructure.authz.domain.model.Password Password}
 * and {@link eapli.framework.infrastructure.authz.domain.model.Role Role}.
 * Passwords are validated according to
 * {@link eapli.framework.infrastructure.authz.application.PasswordPolicy
 * PasswordPolicy} and are encrypted according to a
 * {@link org.springframework.security.crypto.password.PasswordEncoder
 * PasswordEncoder}.
 *
 * <p>
 * There is a corresponding
 * {@link eapli.framework.infrastructure.authz.domain.repositories.UserRepository
 * UserRepository}.
 *
 * <h3>Application layer</h3>
 * <ul>
 * <li>{@link eapli.framework.infrastructure.authz.application.AuthenticationService
 * AuthenticationService} - allows to authenticate and change password
 * <li>{@link eapli.framework.infrastructure.authz.application.AuthorizationService
 * AuthorizationService} - holds the
 * {@link eapli.framework.infrastructure.authz.application.UserSession
 * UserSession} of the currently authenticated user or an empty Optional if
 * there is no session.
 * <li>{@link eapli.framework.infrastructure.authz.application.UserManagementService
 * UserManagementService} - manages users, e.g., create user, deactivate user,
 * etc.
 * </ul>
 *
 * If using the framework with Spring, just autowire the necessary dependencies.
 * In case you are not using Spring, the helper class
 * {@link eapli.framework.infrastructure.authz.application.AuthzRegistry
 * AuthzRegistry} will provide a singleton instance of each service. In order to
 * properly use the registry it is necessary to call its
 * {@link eapli.framework.infrastructure.authz.application.AuthzRegistry#configure(eapli.framework.infrastructure.authz.domain.repositories.UserRepository, eapli.framework.infrastructure.authz.application.PasswordPolicy, org.springframework.security.crypto.password.PasswordEncoder)
 * configure}
 * method in the start of the application to inject the proper
 * {@link eapli.framework.infrastructure.authz.domain.repositories.UserRepository
 * UserRepository} implementation.
 *
 * <p>
 * For instance,
 *
 * <pre>
 * <code>
 *    public static void main(final String[] args) {
 *       AuthzRegistry.configure(PersistenceContext.repositories().users(),
 *               new CafeteriaPasswordPolicy(), new PlainTextEncoder());
 *
 *       new ECafeteriaBackoffice().run(args);
 *   }
 * </code>
 * </pre>
 *
 * Afterwards, in order to use these objects, you just need to grab the
 * singleton from the registry, e.g.
 *
 * <pre>
 * <code>
 * public class RegisterDishTypeController implements Controller {
 *
 *   private final AuthorizationService authz = AuthzRegistry.authorizationService();
 *   private final DishTypeRepository repository = PersistenceContext.repositories().dishTypes();
 *
 *   public DishType registerDishType(final String acronym, final String description) {
 *       authz.ensureAuthenticatedUserHasAnyOf(CafeteriaRoles.POWER_USER,
 *               CafeteriaRoles.MENU_MANAGER);
 *
 *       final DishType newDishType = new DishType(acronym, description);
 *       return this.repository.save(newDishType);
 *   }
 * }
 *
 * </code>
 * </pre>
 *
 * @author Paulo Gandra Sousa
 *
 */
package eapli.framework.infrastructure.authz;