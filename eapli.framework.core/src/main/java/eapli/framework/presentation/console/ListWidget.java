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

import java.util.ArrayList;
import java.util.Collection;

import eapli.framework.validations.Preconditions;
import eapli.framework.visitor.Visitor;

/**
 * A simple widget to list the items of a collection, e.g., create a widget for User.
 *
 * <pre>
 * Collection<User> items = controller.all();
 * ListWidget<User> wg = new ListWidget<>(items, new ShowUserVisitor());
 * </pre>
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of element each item in list is
 */
@SuppressWarnings("squid:S106")
public class ListWidget<T> {

    protected final Collection<T> source;
    private final Visitor<T> visitor;
    private String header;

    public ListWidget(final String header, final Collection<T> source) {
        this(header, source, System.out::println);
    }

    public ListWidget(final String header, final Iterable<T> source) {
        this(header, source, System.out::println);
    }

    public ListWidget(final String header, final Collection<T> source, final Visitor<T> visitor) {
        Preconditions.noneNull(header, source, visitor);

        this.header = header;
        this.source = source;
        this.visitor = visitor;
    }

    public ListWidget(final String header, final Iterable<T> source, final Visitor<T> visitor) {
        Preconditions.noneNull(header, source, visitor);

        this.header = header;
        this.source = new ArrayList<>();
        source.forEach(this.source::add);
        this.visitor = visitor;
    }

    public void show() {
        System.out.println(header);
        int position = 0;
        for (final T et : this.source) {
            position++;
            System.out.print(position + ". ");
            this.visitor.visit(et);
            System.out.print("\n");
        }
    }

    protected int size() {
        return this.source.size();
    }
}
