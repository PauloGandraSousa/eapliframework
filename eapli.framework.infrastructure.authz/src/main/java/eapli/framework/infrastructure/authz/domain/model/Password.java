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
package eapli.framework.infrastructure.authz.domain.model;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.security.crypto.password.PasswordEncoder;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * A password of a user. Password policy and encoding is done outside of this
 * class by {@link SystemUserBuilder} and {@link PasswordEnforcer}.
 *
 * <p>
 * IMPORTANT: passwords should never be stored in plain format
 *
 * @author Paulo Gandra Sousa
 */
@Embeddable
public class Password implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "pass")
    private final String thePassword;

    protected Password() {
        // for ORM only
        thePassword = null;
    }

    /* package */ Password(final String password) {
        Preconditions.nonNull(password);
        thePassword = password;
    }

    public static Optional<Password> valueOf(final String rawPassword, final PasswordPolicy policy,
            final PasswordEncoder encoder) {
        if (policy.isSatisfiedBy(rawPassword)) {
            return Optional.of(new Password(encoder.encode(rawPassword)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }

        final Password other = (Password) o;
        return thePassword.equals(other.thePassword);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(thePassword).code();
    }

    /**
     * Let's not return the password by mistake.
     */
    @Override
    public String toString() {
        return "********";
    }

    /**
     * for supporting Spring Security UserDetails
     */
    /* package */String value() {
        return thePassword;
    }
}
