/*
 * Copyright (c) 2013-2022 the original author or authors.
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
package eapli.framework.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra Sousa 2021.07.07
 */
class MacroTest {

    @Test
    void ensureEmptyMacroExecutesWithoutErrors() {
        final var m = new Macro();
        m.execute();

        assertTrue(m.errorCause().isEmpty());
    }

    @Test
    void ensureMacroExecutionWithoutErrors() {
        final Action a1 = () -> true;

        final var m = new Macro(a1);
        m.execute();

        assertTrue(m.errorCause().isEmpty());
    }

    @Test
    void ensureMacroExecutionWithErrors() {
        final Action a1 = () -> false;

        final var m = new Macro(a1);
        m.execute();

        assertTrue(m.errorCause().isPresent());
        assertEquals(a1, m.errorCause().orElse(null));
    }

    @Test
    void ensureExecutesInTheRightOrder() {
        final List<Integer> h = new ArrayList<>();
        final Action a1 = () -> {
            h.add(1);
            return true;
        };
        final Action a2 = () -> {
            h.add(2);
            return true;
        };
        final Action a3 = () -> {
            h.add(3);
            return true;
        };
        final var m = new Macro(a1, a2, a3);
        m.execute();

        assertTrue(m.errorCause().isEmpty());
        assertEquals(3, h.size());
        assertEquals(1, h.get(0));
        assertEquals(2, h.get(1));
        assertEquals(3, h.get(2));
    }

    @Test
    void ensureConstructsAndAddsWith() {
        final List<Integer> h = new ArrayList<>();
        final Action a1 = () -> {
            h.add(1);
            return true;
        };
        final Action a2 = () -> {
            h.add(2);
            return true;
        };
        final Action a3 = () -> {
            h.add(3);
            return true;
        };
        final var m = new Macro(a1).with(a2).with(a3);
        m.execute();

        assertTrue(m.errorCause().isEmpty());
        assertEquals(3, h.size());
        assertEquals(1, h.get(0));
        assertEquals(2, h.get(1));
        assertEquals(3, h.get(2));
    }

    @Test
    void ensureConstructsEmptyAndAddsWith() {
        final List<Integer> h = new ArrayList<>();
        final Action a1 = () -> {
            h.add(1);
            return true;
        };
        final Action a2 = () -> {
            h.add(2);
            return true;
        };
        final Action a3 = () -> {
            h.add(3);
            return true;
        };
        final var m = new Macro().with(a1).with(a2).with(a3);
        m.execute();

        assertTrue(m.errorCause().isEmpty());
        assertEquals(3, h.size());
        assertEquals(1, h.get(0));
        assertEquals(2, h.get(1));
        assertEquals(3, h.get(2));
    }
}
