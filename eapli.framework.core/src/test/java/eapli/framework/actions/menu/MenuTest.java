package eapli.framework.actions.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import eapli.framework.actions.Actions;

class MenuTest {

    @Test
    void ensureAddsOneOption() {
        final Menu subject = new Menu();
        final var i1 = MenuItem.of(1, "", Actions.SUCCESS);

        subject.addItem(i1);

        assertEquals(i1, subject.item(1).orElseThrow());
    }

    @Test
    void ensureCannotAddAnExistingOption() {
        final Menu subject = new Menu();
        final var i1 = MenuItem.of(1, "", Actions.SUCCESS);
        final var i2 = MenuItem.of(1, "", Actions.SUCCESS);

        subject.addItem(i1);

        assertThrows(IllegalStateException.class, () -> subject.addItem(i2));
    }

    @Test
    void ensureCannotAddASubmenuWithAnExistingOption() {
        final Menu subject = new Menu();
        final var i1 = MenuItem.of(1, "", Actions.SUCCESS);

        final var m2 = new Menu();
        final var i2 = MenuItem.of(1, "", Actions.SUCCESS);
        m2.addItem(i2);

        subject.addItem(i1);

        assertThrows(IllegalStateException.class, () -> subject.addSubMenu(1, m2));
    }
}
