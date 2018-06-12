package eapli.framework.actions;

class SampleAddAction implements UndoableAction {
    private final SampleAdder target;
    private final int lump;

    public SampleAddAction(SampleAdder target, int lump) {
        this.target = target;
        this.lump = lump;
    }

    @Override
    public boolean execute() {
        target.add(lump);
        return true;
    }

    @Override
    public void undo() {
        target.subtract(lump);
    }
}
