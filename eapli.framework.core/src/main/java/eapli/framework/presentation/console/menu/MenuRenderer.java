package eapli.framework.presentation.console.menu;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.util.Console;
import eapli.framework.util.Preconditions;

/**
 * base class for menu renderers using the console as I/O
 *
 * @author pgsou_000
 *
 */
public abstract class MenuRenderer {

    protected final Menu menu;
    protected final MenuItemRenderer itemRenderer;

    public MenuRenderer(final Menu menu, final MenuItemRenderer itemRenderer) {
        Preconditions.nonNull(menu);

        this.menu = menu;
        this.itemRenderer = itemRenderer;
    }

    /**
     * renders the menu on the screen and asks the user for an option. when the
     * user selects an option, the corresponding action is executed
     *
     * @return the success or insuccess of the action's execution
     */
    public boolean render() {
        doShow();

        final MenuItem item = readOption();
        return item.select();
    }

    protected abstract void doShow();

    /**
     * @return
     */
    protected MenuItem readOption() {
        MenuItem item;
        do {
            final int option = Console.readInteger("\nPlease choose an option");
            item = menu.item(option);
        } while (item == null);
        return item;
    }
}