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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra de Sousa 26/05/2020
 *
 */
public class InMemoryRepositoryInheritanceTest {

    protected abstract class BaseRecord {
        public Long pk;

        public BaseRecord(final Long pk) {
            this.pk = pk;
        }
    }

    protected class ARecord extends BaseRecord {
        public String sfield;

        public ARecord(final Long pk, final String field) {
            super(pk);
            this.sfield = field;
        }
    }

    protected class AnotherRecord extends BaseRecord {
        public float ffield;

        public AnotherRecord(final Long pk, final float field) {
            super(pk);
            this.ffield = field;
        }
    }

    private static class TestInheritanceRepository extends InMemoryRepository<BaseRecord, Long> {

        public TestInheritanceRepository() {
            super((o) -> o.pk);
        }
    }

    private final static Long A_ID = 1L;
    private final static Long B_ID = 2L;

    private InMemoryRepository<BaseRecord, Long> subject;

    @Before
    public void setup() {
        InMemoryRepository.reset();
        subject = new TestInheritanceRepository();
    }

    @Test
    public void ensureCountIsZero() {
        assertEquals(0, subject.count());
    }

    @Test
    public void ensureSaveAddsOneARecord() {

        final long oldcount = subject.count();

        final BaseRecord added = addARecord();

        assertNotNull(added);
        assertTrue(subject.count() > oldcount);
        assertTrue(added instanceof ARecord);
    }

    private BaseRecord addARecord() {
        final ARecord entity = new ARecord(A_ID, "abc");
        final BaseRecord added = subject.save(entity);
        return added;
    }

    @Test
    public void ensureSaveAddsOneAnotherRecord() {

        final long oldcount = subject.count();

        final BaseRecord added = addAnotherRecord();

        assertNotNull(added);
        assertTrue(subject.count() > oldcount);
        assertTrue(added instanceof AnotherRecord);
    }

    private BaseRecord addAnotherRecord() {
        final AnotherRecord entity = new AnotherRecord(B_ID, 3.1415F);
        final BaseRecord added = subject.save(entity);
        return added;
    }

    @Test
    public void ensureFindARecord() {
        addARecord();
        addAnotherRecord();

        final BaseRecord found = subject.findById(A_ID).orElseThrow(IllegalStateException::new);

        assertNotNull(found);
        assertTrue(found instanceof ARecord);
    }

    @Test
    public void ensureFindAnotherRecord() {
        addARecord();
        addAnotherRecord();

        final BaseRecord found = subject.findById(B_ID).orElseThrow(IllegalStateException::new);

        assertNotNull(found);
        assertTrue(found instanceof AnotherRecord);
    }
}
