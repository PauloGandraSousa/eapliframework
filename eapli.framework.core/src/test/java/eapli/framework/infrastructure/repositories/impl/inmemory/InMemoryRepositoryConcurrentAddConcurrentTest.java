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

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

/**
 * Use concurrent JUnit to test multiple threads simultaneously add a new object to the repositoy..
 *
 * @author Paulo Gandra de Sousa 26/05/2020
 * @see InMemoryRepositoryConcurrentAddTest
 *
 */
@RunWith(ConcurrentTestRunner.class)
public class InMemoryRepositoryConcurrentAddConcurrentTest {

    protected class TestRecord {
        public long pk;
        public String field;

        public TestRecord(final long pk, final String field) {
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

    private final static int N_TASKS = 10;
    private final static AtomicInteger id = new AtomicInteger();

    @Test
    @ThreadCount(N_TASKS)
    public void testAdd() {

        final long eid = id.addAndGet(1);
        final long tid = Thread.currentThread().getId();
        final TestRecord entity = new TestRecord(eid, "abc: " + tid);
        final TestRecord added = subject.save(entity);
        System.out.printf("»» Saved entity %d in thread %d\n", eid, tid);

        assertNotNull(added);
    }

    @After
    public void ensureAllAdded() {
        assertEquals(N_TASKS, subject.count());
    }
}
