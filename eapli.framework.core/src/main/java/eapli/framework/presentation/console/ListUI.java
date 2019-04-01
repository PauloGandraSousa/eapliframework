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
package eapli.framework.presentation.console;

import eapli.framework.util.Preconditions;
import eapli.framework.visitor.Visitor;

/**
 * a generic List UI to avoid code duplication since most of the List UIs are
 * quite similar. while AbstractListUI allows the client code to create its own
 * derived classes, ListUI allows the client code to work only with objects,
 * instances, of this class without the need to define new classes.
 *
 * Created by Paulo Gandra Sousa
 */
public class ListUI<T> extends AbstractListUI<T> {

    protected final Iterable<T> source;
    protected final Visitor<T> printer;
    protected final String theElementName;
    protected final String theHeader;

    public ListUI(final Iterable<T> source, final Visitor<T> printer, final String elementName, final String header) {
        Preconditions.nonNull(source);
        Preconditions.nonNull(printer);
        Preconditions.nonNull(elementName);
        Preconditions.nonNull(header);

        this.source = source;
        this.printer = printer;
        this.theElementName = elementName;
        this.theHeader = header;
    }

    /**
     * the source to list
     *
     * @return
     */
    @Override
    protected Iterable<T> elements() {
        return this.source;
    }

    /**
     * the visitor that prints the content of each element
     *
     * @return
     */
    @Override
    protected Visitor<T> elementPrinter() {
        return this.printer;
    }

    @Override
    public String listHeader() {
        return theHeader;
    }

    /**
     * the name of the type of elements to list
     *
     * @return
     */
    @Override
    protected String elementName() {
        return this.theElementName;
    }
}
