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
package eapli.framework.identities.impl;

import eapli.framework.identities.IdentityGenerator;
import eapli.framework.validations.Preconditions;

/**
 * An id generator that uses a prefix and a sequence number, e.g., AX00001
 * <p>
 * This class is thread-safe.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class PrefixedNumberSequenceGenerator implements IdentityGenerator<String> {

    private final String prefix;
    private final String mask;
    // we rely on the thread-safety of {@link NumberSequenceGenerator}
    private final NumberSequenceGenerator numberPartGenerator;

    public PrefixedNumberSequenceGenerator(final String prefix, final int length) {
        this(prefix, length, 0L);
    }

    public PrefixedNumberSequenceGenerator(final String prefix, final int length, final Long seed) {
        Preconditions.nonEmpty(prefix);
        Preconditions.isPositive(length);

        this.prefix = prefix;
        mask = "%s%0" + length + "d";
        numberPartGenerator = new NumberSequenceGenerator(seed);
    }

    @Override
    public String newId() {
        return String.format(mask, prefix, numberPartGenerator.newId());
    }
}
