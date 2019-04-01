package eapli.framework.memento;

/**
 * very simple sample class for the use of memento
 *
 * @author Paulo Gandra de Sousa
 *
 */
class SampleAdder implements Restorable {

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

    //
    // MEMENTO
    //

    private class SampleAdderMemento implements Memento {
        private final int state;

        public SampleAdderMemento(int x) {
            state = x;
        }

        public int getState() {
            return state;
        }
    }

    @Override
    public Memento snapshot() {
        return new SampleAdderMemento(current);
    }

    @Override
    public void restoreTo(Memento previousState) {
        if (!(previousState instanceof SampleAdderMemento)) {
            throw new IllegalArgumentException();
        }
        final SampleAdderMemento previous = (SampleAdderMemento) previousState;
        current = previous.getState();
    }

}
