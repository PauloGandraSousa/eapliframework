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

import java.util.Collection;
import java.util.Iterator;

import eapli.framework.io.util.Console;
import eapli.framework.visitor.Visitor;

/**
 * A simple widget that lists the items of a collection and allows the user to
 * select an item.
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of element in the collection
 */
@SuppressWarnings("squid:S106")
public class SelectWidget<T> extends ListWidget<T> {

    private int option = -1;

    public SelectWidget(final String header, final Collection<T> source) {
        super(header, source);
    }

    public SelectWidget(final String header, final Iterable<T> source) {
        super(header, source);
    }

    public SelectWidget(final String header, final Collection<T> source, final Visitor<T> visitor) {
        super(header, source, visitor);
    }

    public SelectWidget(final String header, final Iterable<T> source, final Visitor<T> visitor) {
        super(header, source, visitor);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("0. Exit");
        this.option = Console.readOption(1, size(), 0);
    }

    /**
     *
     * @return -1 if the user has not yet made a selection 0 if the user
     *         selected "exit" a positive number corresponding to the list index
     *         of source if the user selected an item
     */
    public int selectedOption() {
        return this.option;
    }

    public T selectedElement() {
        switch (this.option) {
        case -1:
        case 0:
            return null;
        default:
            return fromIndex();
        }
    }

    private T fromIndex() {
        int idx = 0;
        T elem = null;
        final Iterator<T> it = source.iterator();
        while (idx < this.option) {
            elem = it.next();
            idx++;
        }
        return elem;
    }
}
