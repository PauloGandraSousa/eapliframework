/**
 * The user domain model
 *
 * @author Paulo Gandra de Sousa
 */
package eapli.framework.infrastructure.authz.application;

/*
 * @startuml
 *
 * class AuthenticationService <<service>> {
 * +authenticate()
 * }
 *
 * class AuthorizationService <<service>>
 *
 * class UserSession
 *
 * class Username <<value>><<id>>
 *
 * class SystemUser <<root>>
 *
 * class Password <<value>>
 *
 * class PasswordPolicy <<service>>
 *
 * class PasswordEncoder <<service>>
 *
 * class Role <<value>>
 *
 * SystemUser -> Username
 * SystemUser -> Password
 * Password ..> PasswordPolicy
 * Password ..> PasswordEncoder
 * UserSession --> SystemUser
 * AuthenticationService ..> Username
 * AuthenticationService ..> Password
 * AuthenticationService ..> Role
 * AuthenticationService ..> AuthorizationService
 * AuthorizationService --> UserSession
 * AuthenticationService ..> PasswordPolicy
 * AuthenticationService ..> PasswordEncoder
 * 
 * @enduml
 */