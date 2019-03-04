/**
 *
 */
package eapli.framework.domain.repositories;

/**
 * An exception that indicates a violation of a data integrity constraint (e.g.,
 * a duplicate key) has occurred
 *
 * @author Paulo Gandra Sousa
 *
 */
public class IntegrityViolationException extends Exception {

    private static final long serialVersionUID = 7328099647714453484L;

    public IntegrityViolationException() {
        // empty
    }

    /**
     * @param arg0
     */
    public IntegrityViolationException(final String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public IntegrityViolationException(final Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public IntegrityViolationException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }
}
