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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.time.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class SystemUserActivateDeactivateTest {
    private SystemUser instance;

    @Before
    public void setUp() throws Exception {
        instance = TestSystemUserFactory.dummyUser("abc");
    }

    @Test
    public void ensureAfterCreationUserIsActive() {
        assertTrue(instance.isActive());
    }

    @Test(expected = IllegalStateException.class)
    public void ensureCannotDeactivateAnInactiveUser() {
        try {
            instance.deactivate(Calendars.now());
        } catch (@SuppressWarnings("unused") final Exception e) {
            fail("something stange just happened. we are only interested in catching the exception on the second call to deactivate");
        }
        instance.deactivate(Calendars.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotDeactivatePriorToCreationDate() {
        instance.deactivate(Calendars.yesterday());
    }

    @Test
    public void ensureAfterDeactivationUserIsInactive() {
        instance.deactivate(Calendars.now());
        assertFalse(instance.isActive());
    }
}
