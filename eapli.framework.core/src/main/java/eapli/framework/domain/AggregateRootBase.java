/**
 *
 */
package eapli.framework.domain;

/**
 * a simple base class for aggregate roots
 *
 * @author pgsou_000
 *
 */
public abstract class AggregateRootBase<I> extends DomainEntityBase<I> implements AggregateRoot<I> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
