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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * Test multiple threads simultaneously add a new object to the repository.
 *
 * @author Paulo Gandra de Sousa 26/05/2020
 * @see InMemoryRepositoryConcurrentAddConcurrentTest
 */
public class InMemoryRepositoryConcurrentAddTest {

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

    @Test
    public void ensureAllAdded() {

        final int N_TASKS = 100;
        final int N_THREADS = 10;
        final AtomicInteger id = new AtomicInteger();

        final ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors
                .newScheduledThreadPool(N_THREADS);
        for (int i = 0; i < N_TASKS; i++) {
            executor.schedule(() -> {
                final long eid = id.addAndGet(1);
                final long tid = Thread.currentThread().getId();
                final TestRecord entity = new TestRecord(eid, "abc: " + tid);
                subject.save(entity);
                System.out.printf("»» Saved entity %d in thread %d\n", eid, tid);
            }, 100, TimeUnit.MICROSECONDS);
            System.out.printf("Submitted task %d\n", i);
        }

        executor.shutdown();
        try {
            System.out.println("Waiting for all threads to finish");
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals(N_TASKS, subject.count());
    }
}
