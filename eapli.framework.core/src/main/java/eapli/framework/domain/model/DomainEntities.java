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
package eapli.framework.domain.model;

import java.util.Objects;

import eapli.framework.util.HashCoder;

/**
 * helper class for common domain entities operations
 *
 * @author Paulo Gandra Sousa
 */
public final class DomainEntities {

    private DomainEntities() {
        // ensure utility
    }

    /**
     * checks if two domain objects refer to the same real world entity by
     * checking their identities. if any of the parameters is null this method
     * returns false
     *
     * @param one
     * @param other
     * @return
     */
    public static boolean areEqual(final DomainEntity<?> one, final Object other) {
        if (other == null || one == null) {
            return false;
        }
        if (one == other) {
            return true;
        }
        if (!(one.getClass().isInstance(other))) {
            return false;
        }

        final DomainEntity<?> that = (DomainEntity<?>) other;
        return Objects.equals(one.identity(), that.identity());
    }

    /**
     * creates an hash code of the domain entity passed as argument, based on
     * its identity
     *
     * @param one
     * @return
     */
    public static int hashCode(final DomainEntity<?> one) {
        return new HashCoder().of(one.identity()).code();
    }
}
