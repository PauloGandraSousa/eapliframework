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
package eapli.framework.strings;

import org.junit.Test;

/**
 * @author Paulo Gandra de Sousa 15/05/2020
 *
 */
class PrettyJsonStringFromStringTest {

    private final String TEST_ONE = "{\"one\":\"two\"}";
    private final String TEST_COL_1 = "[{\"one\":\"two\"}, {\"a\":\"b\"}]";
    private final String TEST_COL_2 = "[1, 2, 3, 4]";
    private final String TEST_CHILD = "{\"one\":{\\\"a\\\":\\\"b\\\"},\"c\":\"d\"}";

    private final String TEST_WRONG_ONE = "{\"one\":two}";
    private final String TEST_WRONG_COL_1 = "[\"one\":two]";
    private final String TEST_WRONG_COL_2 = "[\"one\",two}";

    @Test
    void test() {
        final PrettyJsonString subject = PrettyJsonString.fromString(TEST_ONE);

    }
}
