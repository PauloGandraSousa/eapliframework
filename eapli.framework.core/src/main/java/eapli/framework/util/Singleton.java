package eapli.framework.util;

/**
 * a marker interface for Singletons. classes implementing this interface must
 * not allow to create more than one instance. usually this is done by making
 * the class final, making the constructors private and providing a static
 * method to access the only instance of the class.
 *
 * see the lazy holder pattern for thread safety:
 * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
 *
 * @author Paulo Gandra Sousa
 *
 */
public interface Singleton {

}
