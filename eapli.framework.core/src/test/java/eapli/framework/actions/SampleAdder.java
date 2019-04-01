package eapli.framework.actions;

/**
 * very simple sample class for the use of actions
 *
 * @author Paulo Gandra de Sousa
 *
 */
class SampleAdder {

    private int current;

    public int add(int x) {
        current += x;
        return current();
    }

    public int subtract(int x) {
        current -= x;
        return current();
    }

    public int current() {
        return current;
    }
}
