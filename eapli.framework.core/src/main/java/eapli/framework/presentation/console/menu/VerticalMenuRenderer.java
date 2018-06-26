/**
 *
 */
package eapli.framework.presentation.console.menu;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;

/**
 * @author Paulo Gandra Sousa
 *
 */
@SuppressWarnings("squid:S106")
public class VerticalMenuRenderer extends MenuRenderer {
    public VerticalMenuRenderer(final Menu menu, final MenuItemRenderer itemRenderer) {
        super(menu, itemRenderer);
    }

    @Override
    protected void doShow() {
        for (final MenuItem item : menu.itens()) {
            itemRenderer.render(item);
            System.out.println();
        }
    }
}
