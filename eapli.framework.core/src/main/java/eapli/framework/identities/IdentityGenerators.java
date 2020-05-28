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
package eapli.framework.identities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import eapli.framework.util.Singleton;
import eapli.framework.validations.Preconditions;

/**
 * Helper class to act as a registry of identity generators and generate new ids based on an opaque
 * context.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Singleton
public class IdentityGenerators {

    private static class LazyHolder {
        private static final IdentityGenerators INSTANCE = new IdentityGenerators();

        private LazyHolder() {

        }
    }

    private final Map<String, IdentityGenerator<?>> generators = new ConcurrentHashMap<>();

    private IdentityGenerators() {
        // ensure singleton
    }

    public static IdentityGenerators instance() {
        return LazyHolder.INSTANCE;
    }

    public IdentityGenerators with(final String context, final IdentityGenerator<?> gen) {
        Preconditions.nonNull(gen);
        Preconditions.nonEmpty(context);

        generators.put(context, gen);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T newId(final String context) {
        return (T) generators.get(context).newId();
    }
}
