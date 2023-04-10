/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
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
package eapli.framework.general.domain.model.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.general.domain.model.Text;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class TextTestBase {
	protected Text instance;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance = Text.valueOf(getText());
	}

	public abstract String getText();

	public abstract long getExpectedWordCount();

	public abstract long getExpectedLineCount();

	public abstract List<String> getExpectedWords();

	public abstract Map<String, Integer> getExpectedFrequency();

	@Test
	public void ensureWordCount() {
		assertEquals(getExpectedWordCount(), instance.wordCount());
	}

	@Test
	public void ensureLineCount() {
		assertEquals(getExpectedLineCount(), instance.lineCount());
	}

	@Test
	public void ensureFrequency() {
		assertEquals(getExpectedFrequency(), instance.frequency());
	}

	@Test
	public void ensureWords() {
		final var expected = getExpectedWords();
		assertEquals(expected.size(), instance.words().count());
		assertTrue(instance.words().allMatch(e -> expected.contains(e)));
	}
}
