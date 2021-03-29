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
package eapli.framework.representations.builders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.general.domain.model.Money;
import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.representations.dto.GeneralDTO;

/**
 * A builder for {@link GeneralDTO} objects.
 * <p>
 * This class is thread-safe.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class GeneralDTORepresentationBuilder implements RepresentationBuilder<GeneralDTO> {

    private final GeneralDTO dto;

    public GeneralDTORepresentationBuilder(final String type) {
        dto = new GeneralDTO(type);
    }

    @Override
    public GeneralDTO build() {
        return dto;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final String value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Integer value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Float value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Money value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Designation value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Description value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final EmailAddress value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Long value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Double value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Boolean value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final BigInteger value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final BigDecimal value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Calendar value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withElement(final String value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
