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
package eapli.framework.money.domain.model;

import java.time.LocalDateTime;
import java.util.Currency;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * A conversion rate between two currencies.
 * <p>
 * <em>Note:</em> inspired by
 * <a href="https://verraes.net/2019/06/emergent-contexts-through-refinement/">Emergent contexts
 * through refinement</a>
 *
 * @author Paulo Gandra de Sousa 19/05/2023
 *
 */
@Embeddable
@Value
@Accessors(fluent = true)
public class ConversionRate implements ValueObject {

    private static final long serialVersionUID = 1L;

    private Currency from;
    private Currency to;
    private double rate;
    private LocalDateTime asOf;

    /**
     * Applies this conversion rate to a value.
     *
     * @param m
     * @return
     */
    public Money convert(final Money m) {
        Preconditions.ensure(m.currency().equals(from));

        return new Money(m.multiply(rate).amount(), to);
    }
}
