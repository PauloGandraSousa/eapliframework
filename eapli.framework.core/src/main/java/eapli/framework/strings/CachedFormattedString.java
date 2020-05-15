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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A cached formatted string. If the formatted value is needed in several places, there is no need to format it every
 * time it is needed. This class is thread-safe.
 * <p>
 * This is an application of the Decorator GoF pattern.
 *
 * @author Paulo Gandra Sousa 13/05/2020
 *
 */
public class CachedFormattedString implements FormattedString {

    /**
     * Just a value to use as key for the cache mechanism. No special meaning.
     */
    private static final int CACHE_KEY = 1;

    private final BaseFormattedString decorated;

    // using a ConcurrentHashMap for a simple thread-safe cache
    private final Map<Integer, String> cache = new ConcurrentHashMap<>();

    public CachedFormattedString(final BaseFormattedString toCache) {
        decorated = toCache;
    }

    @Override
    public String toString() {
        return cache.computeIfAbsent(CACHE_KEY, i -> decorated.toString());
    }
}
