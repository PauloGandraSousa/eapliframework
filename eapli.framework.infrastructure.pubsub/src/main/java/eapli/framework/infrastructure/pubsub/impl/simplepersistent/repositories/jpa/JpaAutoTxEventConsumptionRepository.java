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
package eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.jpa;

import java.util.Map;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.model.EventConsumption;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventConsumptionRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * The JPA implementation for scenarios where the application is not running in
 * a container.
 *
 * @author Paulo Gandra de Sousa 2022.05.10
 */
public class JpaAutoTxEventConsumptionRepository extends JpaAutoTxRepository<EventConsumption, Long, Long>
        implements EventConsumptionRepository {

    public JpaAutoTxEventConsumptionRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaAutoTxEventConsumptionRepository(final String puname,
            @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties) {
        super(puname, properties, "id");
    }

}
