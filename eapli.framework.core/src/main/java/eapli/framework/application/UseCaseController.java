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
package eapli.framework.application;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A Use case controller is the API
 * to the domain translating from the outside world (e.g., DTO) to the inside
 * world (domain). Use case controllers do not perform any business logic but
 * coordinate/dispatch the execution to the right business object. Usually a
 * couple of lines of code are enough:
 *
 * <ol>
 * <li>translate input DTOs to domain Value Objects
 * <li>lookup the necessary Aggregate Root in the repository, based on the
 * input/value objects
 * <li>call a domain logic business method on the aggregate
 * <li>save the modified aggregate back to the repository
 * </ol>
 *
 * <p>
 * Controllers should be stateless (no properties) except for injected
 * dependencies, e.g., repositories and services. That is, even if the user has
 * to enter name, email, and other "fields", the controller should not have a
 * Person object as its state neither have a setName(), setEmail(), and alike
 * methods.
 * </p>
 * <p>
 * Another important rule, is that a use case controller should only modify one
 * single object (aggregate root) and transactional controllers that modify more
 * than object should be avoided.
 * </p>
 * <p>
 * For example, consider the following use case in an hypothetical agenda
 * application:
 *
 * <blockquote> <strong>Add a new contact</strong>
 * <p>
 * As a User I want to enter a new contact by providing the name, email, and
 * phone number of a person. Optionally I want to provide the full address
 * (including country) of residence and the date of birth of the person.
 * </blockquote>
 *
 * A controller for this use case would look like:
 *
 * <pre>
 * <code>
 * &#64;UseCaseController
 * class AddContactController {
 *      private ContactRepository repoContact;
 *      private CountryRepository repoCountry;
 *
 *      public AddContactController(ContactRepository repoContact, CountryRepository repoCountry) {
 *          this.repoContact = repoContact;
 *          this.repoCountry = repoCountry;
 *      }
 *
 *      public Iterable<Country> countries() {
 *          return repoCountry.findAll();
 *      }
 *
 *      public Contact addContact(String name, String email, String phone) {
 *          Contact c = new Contact(Name.valueOf(name), EmailAddress.valueOf(email), PhoneNumber.valueOf(phone));
 *          return repoContact.save(c);
 *      }
 *
 *      public Contact addContact(String name, String email, String phone, String Street, String local, String countryCode, String dateOfBirth) {
 *          Country country = repoCountry.ofIdentity(CountryCode.valueOf(countryCode));
 *          Address a = new Address(Street.valueOf(street), Local.valueOf(local), country);
 *          Contact c = new Contact(Name.valueOf(name),
 *                                  EmailAddress.valueOf(email),
 *                                  PhoneNumber.valueOf(phone),
 *                                  DateOfBirth.valueOf(dateOfBirth),
 *                                  a);
 *          return repoContact.save(c);
 *      }
 * }
 * </code>
 * </pre>
 *
 * <p>
 * Controllers can also perform some infrastructure jobs like ensuring
 * permissions (authentication and authorization) to access the method,
 * eventough that is better delegated to some framework, e.g., spring Security.
 * </p>
 *
 * @author Paulo Gandra Sousa
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE })
public @interface UseCaseController {
    // empty
}
