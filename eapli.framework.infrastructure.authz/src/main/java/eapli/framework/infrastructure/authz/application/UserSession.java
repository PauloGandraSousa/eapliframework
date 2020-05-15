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
package eapli.framework.infrastructure.authz.application;

import java.util.Calendar;
import java.util.UUID;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;

/**
 * The session of an authenticated user.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class UserSession implements ValueObject {
    private static final long serialVersionUID = 1L;

    private final SystemUser user;
    private final UUID token;
    private final Calendar startedOn;

    /**
     *
     * @param user
     */
    public UserSession(final SystemUser user) {
        Preconditions.nonNull(user, "user must not be null");

        this.user = user;
        token = UUID.randomUUID();
        startedOn = Calendars.now();
    }

    /**
     *
     * @return the currently authenticated user
     */
    public SystemUser authenticatedUser() {
        return user;
    }

    /**
     * @return the startedOn
     */
    public Calendar startedOn() {
        return startedOn;
    }

    @Override
    public String toString() {
        return user.identity() + "@" + token;
    }
}
