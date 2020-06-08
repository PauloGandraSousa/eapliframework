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
package eapli.framework.infrastructure.repositories.impl.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import eapli.framework.general.domain.model.Money;

/**
 * To avoid breaking the information hiding principle, we convert the value
 * stored in cents when saving it to the store and divide it back again when
 * reading it.
 * <p>
 * In this case we need to know that the actual value stored in the Money object
 * is stored in cents, so we must first multiply it when reading it from the
 * persistence store. The JPA mapping already forces us to know the details of
 * the class so the information hiding was broken from the beginning...
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(final Money arg0) {
        return arg0.toString();
    }

    @Override
    public Money convertToEntityAttribute(final String arg0) {
        return Money.valueOf(arg0);
    }
}
