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
package eapli.framework.infrastructure.repositories.impl.inmemory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra de Sousa 26/05/2020
 *
 */
public class InMemoryRepositoryEmptyRepositoryTest {

    protected class TestRecord {
        public Long pk;
        public String field;

        public TestRecord(final Long pk, final String field) {
            this.pk = pk;
            this.field = field;
        }
    }

    private static class TestRepository extends InMemoryRepository<TestRecord, Long> {

        public TestRepository() {
            super((o) -> o.pk);
        }
    }

    private InMemoryRepository<TestRecord, Long> subject;

    @Before
    public void setup() {
        InMemoryRepository.reset();
        subject = new TestRepository();
    }

    @Test
    public void ensureCountIsZero() {
        assertEquals(0, subject.count());
    }

    @Test
    public void ensureSaveAddsOneRecord() {
        final Long ID = 1L;
        final TestRecord entity = new TestRecord(ID, "abc");

        final TestRecord added = subject.save(entity);

        assertEquals(1, subject.count());
        assertEquals(added, subject.first().get());
    }

    @Test
    public void ensureFirstIsEmpty() {
        assertFalse(subject.first().isPresent());
    }

    @Test
    public void ensureFirstNIsEmpty() {
        assertFalse(subject.first(2).iterator().hasNext());
    }

    @Test
    public void ensureFindAllIsEmpty() {
        assertFalse(subject.findAll().iterator().hasNext());
    }
}
