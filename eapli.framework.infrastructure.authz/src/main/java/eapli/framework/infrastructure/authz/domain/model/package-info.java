/**
 * The user domain model
 *
 * @author Paulo Gandra de Sousa
 */
package eapli.framework.infrastructure.authz.domain.model;

/*
 * @startuml
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
 * class Name <<value>>
 *
 * class RoleSet
 *
 * class RoleAssignment <<entity>>
 *
 * class Role <<value>>
 *
 * interface Set<RoleAssignment>
 *
 * class SystemUserBuilder
 *
 * SystemUserBuilder ..> SystemUser
 *
 * SystemUser -> Username
 * SystemUser -> Password
 * SystemUser -> Name
 * SystemUser -> RoleSet
 * RoleSet ..|> Set
 * Set ..> RoleAssignment
 * RoleAssignment --> Role
 * Password ..> PasswordPolicy
 * Password ..> PasswordEncoder
 *
 * @enduml
 */