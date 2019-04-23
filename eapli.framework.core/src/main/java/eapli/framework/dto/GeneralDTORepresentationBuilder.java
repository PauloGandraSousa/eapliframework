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
package eapli.framework.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import eapli.framework.domain.model.RepresentationBuilder;

/**
 * @author sou03408
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
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final Object value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final String value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final long value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final double value) {
        dto.put(name, value);
        return this;
    }

    @Override
    public RepresentationBuilder<GeneralDTO> withProperty(final String name, final boolean value) {
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
}
