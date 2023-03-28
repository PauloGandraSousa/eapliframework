/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.general.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Check;
import eapli.framework.validations.Preconditions;
import lombok.EqualsAndHashCode;

/**
 * An email address.
 *
 * @author Paulo Gandra Sousa
 */
@Embeddable
@EqualsAndHashCode
public class EmailAddress implements ValueObject, Comparable<EmailAddress>, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private final String email;

    protected EmailAddress(final String address) {
        email = address;
    }

    protected EmailAddress() {
        // for ORM
        email = "";
    }

    /**
     * Factory method.
     * <p>
     * This method throws an {@code IllegalArgumentException} if any of the business rules are not
     * fulfilled. This means that a {@code try-catch} statement must be used when creating
     * {@code EmailAddress } objects, thus using exceptions for program flow control.
     * <p>
     *
     * <pre>
     * try {
     *     var e = EmailAddress.valueOf("a@b.com");
     *     user1.setEmail(e);
     * } catch (IllegalArgumentException e) {
     *     System.out.println("Invalid email");
     * }
     * </pre>
     *
     * @param address
     *
     * @return
     *
     * @throws IllegalArgumentException
     *
     * @see #tryValueOf
     */
    public static EmailAddress valueOf(final String address) {
        Preconditions.nonEmpty(address, "email address should neither be null nor empty");
        Preconditions.ensure(StringPredicates.isEmail(address), "Invalid E-mail format");

        return new EmailAddress(address);
    }

    /**
     * Factory method.
     * <p>
     * This method returns either a new instance of {@code  EmailAddress } or a {@code String } with
     * an error message, thus avoiding the use of {@code try-catch } statements for program flow
     * control.
     * <p>
     *
     * <pre>
     * EmailAddress.tryValueOf("a@b.com")
     *         .onLeft((m) -&gt; {
     *             System.out.println("Invalid email" + m);
     *         })
     *         .onRight((e) -&gt; {
     *             user1.setEmail(e);
     *         });
     * </pre>
     *
     * @param address
     *
     * @return
     */
    public static Either<String, EmailAddress> tryValueOf(final String address) {
        return new Check<String, EmailAddress>()
                .failIf()
                .isEmpty(address, () -> "email address should neither be null nor empty")
                .not(StringPredicates.isEmail(address), () -> "Invalid E-mail format")
                .elseSucceed(() -> new EmailAddress(address));
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public int compareTo(final EmailAddress o) {
        return email.compareTo(o.email);
    }
}
