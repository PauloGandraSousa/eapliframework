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
package eapli.framework.presentation.console;

import java.util.function.Supplier;

import eapli.framework.validations.Preconditions;
import eapli.framework.visitor.Visitor;

/**
 * A generic List UI to avoid code duplication since most of the List UIs are quite similar.
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of element this user interface will list
 */
public class ListUI<T> extends AbstractListUI<T> {

    protected final Iterable<T> source;
    protected final Supplier<Iterable<T>> elementSupplier;

    protected final Visitor<T> printer;
    protected final String theElementName;
    protected final String header;
    protected final String headline;
    protected final String theEmptyMessage;

    /**
     * Eagerly Constructs the UI. This constructor should be avoided if the underlying data is
     * fetched
     * from an external database or file as the actual data needs to be evaluated by the compiler in
     * the
     * moment of construction and not only when it is needed. That is, even if the {@link #show()}
     * method is never called, the call to the underlying database would have been issued anyway
     * when
     * the compiler passes the argument to the constructor. See
     * {@link #ListUI(Supplier, Visitor, String, String, String, String)} for lazy construction.
     *
     * @param source
     *            the list of elements to show
     * @param printer
     *            the visitor that knows how to render one element
     * @param elementName
     *            the name of the element
     * @param header
     *            the header of the listing
     * @param headline
     *            the headline of the "screen"
     * @param emptyMessage
     *            the message to show if there no elements
     */
    public ListUI(final Iterable<T> source, final Visitor<T> printer, final String elementName,
            final String header, final String headline, final String emptyMessage) {
        Preconditions.noneNull(source, printer, elementName, header, headline, emptyMessage);

        this.source = source;
        elementSupplier = null;
        this.printer = printer;
        this.theElementName = elementName;
        this.header = header;
        this.headline = headline;
        this.theEmptyMessage = emptyMessage;
    }

    /**
     * Lazily constructs the UI. Since this constructor uses a {@link Supplier} object, the fetching
     * of
     * data only occurs when the data is really needed and not at the moment of construction of the
     * {@code ListUI} object.
     *
     * @param elementSupplier
     *            the supplier of the elements
     * @param printer
     *            the visitor that knows how to render one element
     * @param elementName
     *            the name of the element
     * @param header
     *            the header of the listing
     * @param headline
     *            the headline of the "screen"
     * @param emptyMessage
     *            the message to show if there no elements
     */
    public ListUI(final Supplier<Iterable<T>> elementSupplier, final Visitor<T> printer,
            final String elementName, final String header, final String headline,
            final String emptyMessage) {
        Preconditions.noneNull(elementSupplier, printer, elementName, header, headline, emptyMessage);

        this.elementSupplier = elementSupplier;
        this.source = null;
        this.printer = printer;
        this.theElementName = elementName;
        this.header = header;
        this.headline = headline;
        this.theEmptyMessage = emptyMessage;
    }

    @Override
    public String headline() {
        return headline;
    }

    /**
     * The source to list.
     *
     * @return
     */
    @Override
    protected Iterable<T> elements() {
        Iterable<T> elems;
        if (source == null) {
            elems = elementSupplier.get();
        } else {
            elems = source;
        }
        return elems;
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
        return header;
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

    @Override
    protected String emptyMessage() {
        return "";
    }
}
