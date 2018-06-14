/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.presentation.console;

import java.util.ArrayList;
import java.util.Collection;

import eapli.framework.util.Preconditions;
import eapli.framework.visitor.Visitor;

/**
 * A simple widget to list the items of a collection
 *
 * e.g., create a widget for User
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
        Preconditions.nonNull(header, source, visitor);

        this.header = header;
        this.source = source;
        this.visitor = visitor;
    }

    public ListWidget(final String header, final Iterable<T> source, final Visitor<T> visitor) {
        Preconditions.nonNull(header, source, visitor);

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
