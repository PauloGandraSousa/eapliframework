/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.infrastructure.repositories.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * An utility class for implementing simple agnostic JPA repositories, that is,
 * not tied to any particular managed entity. it is a base for Reporting
 * Repositories
 *
 * <p>
 * based on <a href=
 * "http://stackoverflow.com/questions/3888575/single-dao-generic-crud-methods-jpa-hibernate-spring"
 * > stackoverflow</a> and on
 * <a href="https://burtbeckwith.com/blog/?p=40">burtbeckwith</a>.
 * <p>
 * also have a look at
 * <a href="http://blog.xebia.com/tag/jpa-implementation-patterns/">JPA
 * implementation patterns</a>
 *
 * @author sou03408
 *
 */
public abstract class JpaEntityManagerProvider {

    @PersistenceUnit
    private EntityManagerFactory emFactory;
    private EntityManager entityManager;

    public JpaEntityManagerProvider() {
        super();
    }

    protected EntityManagerFactory entityManagerFactory() {
        return this.emFactory;
    }

    protected EntityManager entityManager() {
        if (this.entityManager == null || !this.entityManager.isOpen()) {
            this.entityManager = entityManagerFactory().createEntityManager();
        }
        return this.entityManager;
    }
}