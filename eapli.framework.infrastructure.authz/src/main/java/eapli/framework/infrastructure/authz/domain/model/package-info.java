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
 * The user domain model.
 *
 * <p>
 * <img src="user-domain-model.svg">
 * </p>
 *
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml user-domain-model.svg
 *
 *           class Username <<value>><<id>>
 *
 *           class SystemUser <<root>>
 *
 *           class Password <<value>>
 *
 *           class PasswordPolicy <<service>>
 *
 *           class PasswordEncoder <<service>>
 *
 *           class Name <<value>>
 *
 *           class RoleSet
 *
 *           class RoleAssignment <<entity>>
 *
 *           class Role <<value>>
 *
 *           interface Set<RoleAssignment>
 *
 *           class SystemUserBuilder
 *
 *           SystemUserBuilder ..> SystemUser
 *           'SystemUserBuilder ..> PasswordPolicy
 *           'SystemUserBuilder ..> PasswordEncoder
 *
 *           SystemUser -> Name
 *           SystemUser -> Password
 *           SystemUser -> Username
 *           SystemUser -left-> RoleSet
 *           RoleSet ..|> Set
 *           RoleSet .down.> RoleAssignment
 *           Role <-down- RoleAssignment
 *           Password ..> PasswordPolicy
 *           Password ..> PasswordEncoder
 * @enduml
 *         -->
 */
package eapli.framework.infrastructure.authz.domain.model;