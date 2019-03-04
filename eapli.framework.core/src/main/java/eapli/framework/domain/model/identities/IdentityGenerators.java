/**
 *
 */
package eapli.framework.domain.model.identities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Singleton;

/**
 * @author sou03408
 *
 */
public class IdentityGenerators implements Singleton {

    private static class LazyHolder {
        private static final IdentityGenerators INSTANCE = new IdentityGenerators();

        private LazyHolder() {

        }
    }

    private final Map<String, IdentityGenerator<?>> generators = new ConcurrentHashMap<>();

    private IdentityGenerators() {

    }

    public static IdentityGenerators instance() {
        return LazyHolder.INSTANCE;
    }

    public IdentityGenerators with(String context, IdentityGenerator<?> gen) {
        Preconditions.nonNull(gen);
        Preconditions.nonEmpty(context);

        generators.put(context, gen);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T newId(String context) {
        return (T) generators.get(context).newId();
    }
}
