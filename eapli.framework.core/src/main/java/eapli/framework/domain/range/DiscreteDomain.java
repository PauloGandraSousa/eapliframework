/**
 *
 */
package eapli.framework.domain.range;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import eapli.framework.domain.ValueObject;
import eapli.framework.util.Preconditions;
import eapli.framework.util.HashCoder;

/**
 * a discrete domain, mathematically a Set.
 *
 * allows for empty domains
 *
 * @todo should T extends ValueObject? the elements of a domain should be
 *       values...
 *
 * @author Paulo Gandra Sousa
 *
 */
public class DiscreteDomain<T> implements Iterable<T>, ValueObject {

    private static final long serialVersionUID = 1626025693468901924L;
    private final Set<T> elements = new HashSet<>();

    public static <T> DiscreteDomain<T> empty() {
        return new DiscreteDomainBuilder<T>().build();
    }

    protected DiscreteDomain(final Iterable<T> from) {
        Preconditions.nonNull(from);

        from.forEach(elements::add);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof DiscreteDomain<?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final DiscreteDomain<T> other = (DiscreteDomain<T>) o;
        return elements.equals(other.elements);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(elements).code();
    }

    public boolean contains(final T e) {
        return elements.contains(e);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean intersects(final DiscreteDomain<T> other) {
        return !this.intersection(other).isEmpty();
    }

    /**
     * creates a new domain consisting of the intersection of elements between this
     * domain and another domain.
     *
     * for instance, <br/>
     * D1 = [1, 2 , 3, 4] <br/>
     * D2 = [3, 4, 5] <br/>
     * D3 = [6, 5, 7, 8] <br/>
     * D4 = []
     *
     * R12 = D1.intersection(D2) = [3, 4]
     *
     * R13 = D1.intersection(D3) = []
     *
     * R14 = D1.intersection(D4) = []
     *
     * @param other
     * @return
     */
    public DiscreteDomain<T> intersection(final DiscreteDomain<T> other) {
        final DiscreteDomainBuilder<T> builder = new DiscreteDomainBuilder<>();
        for (final T each : elements) {
            if (other.contains(each)) {
                builder.add(each);
            }
        }
        return builder.build();
    }

    /**
     * creates a new domain consisting of the union of elements between this domain
     * and another domain
     *
     * for instance, <br/>
     * D1 = [1, 2 , 3, 4] <br/>
     * D2 = [3, 4, 5] <br/>
     * D3 = [6, 5, 7, 8] <br/>
     * D4 = []
     *
     * R12 = D1.union(D2) = [1, 2, 3, 4, 5]
     *
     * R13 = D1.union(D3) = [1, 2, 3, 4, 6, 5, 7, 8]
     *
     * R14 = D1.union(D4) = [1, 2 , 3, 4]
     *
     * @param other
     * @return
     */
    public DiscreteDomain<T> union(final DiscreteDomain<T> other) {
        final DiscreteDomainBuilder<T> builder = new DiscreteDomainBuilder<>();
        elements.forEach(builder::add);
        other.forEach(builder::add);
        return builder.build();
    }

    /**
     * creates a new domain consisting of the complement of elements between this
     * domain and another domain, that is, a domain with the elements of this domain
     * if and only if those elements do not exist in the other domain
     *
     * for instance, <br/>
     * D1 = [1, 2 , 3, 4] <br/>
     * D2 = [3, 4, 5] <br/>
     * D3 = [6, 5, 7, 8] <br/>
     * D4 = []
     *
     * R12 = D1.complement(D2) = [1, 2]
     *
     * R13 = D1.complement(D3) = [1, 2, 3, 4]
     *
     * R14 = D1.complement(D4) = [1, 2, 3, 4]
     *
     * @param other
     * @return
     */
    public DiscreteDomain<T> without(final DiscreteDomain<T> other) {
        final DiscreteDomainBuilder<T> builder = new DiscreteDomainBuilder<>();

        elements.forEach(e -> {
            if (!other.contains(e)) {
                builder.add(e);
            }
        });

        return builder.build();
    }

    /**
     * creates a new domain without the desired elements
     *
     * @param someElements
     * @return
     */
    public DiscreteDomain<T> without(final T... someElements) {
        final DiscreteDomainBuilder<T> builder = new DiscreteDomainBuilder<>();

        // highly inefficient!
        elements.forEach(e -> {
            if (eapli.framework.util.Collections.contains(someElements, e)) {
                builder.add(e);
            }
        });

        return builder.build();
    }

    /**
     * returns an iterator over the domain guaranteeing its immutability
     */
    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableSet(elements).iterator();
    }

    /**
     * adds support for Java 8 Streams
     *
     * @return the elements in the domain as a stream
     */
    public Stream<T> stream() {
        return elements.stream();
    }

    /**
     * adds support for Java 8 Streams
     *
     * @return the elements in the domain as a stream
     */
    public Stream<T> parallelStream() {
        return elements.parallelStream();
    }
}
