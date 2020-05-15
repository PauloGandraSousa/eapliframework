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
package eapli.framework.activerecord;

/**
 * A business object that knows how to handle persistence on its own including
 * deletion
 *
 * @param <I>
 *            the type of the business identity
 *
 * @author Paulo Gandra Sousa
 */
public interface DeleteableActiveRecord<I extends Comparable<I>> extends ActiveRecord<I> {

    /**
     * Deletes the current object from the persistence store. The object in
     * memory no longer becomes managed by the repository.
     */
    void delete();

    /**
     * Checks if the object in memory is currently disconnected from the
     * persistence store.
     *
     * @return {@code true} if this instance has been deleted from the persistence store
     *         or has been marked as deleted
     */
    boolean isDeleted();
}
