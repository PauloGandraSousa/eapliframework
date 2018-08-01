/**
 *
 */
package eapli.framework.presentation.console.menu;

import eapli.framework.actions.menu.MenuItem;

/**
 * @author pgsou_000
 *
 */
public interface MenuItemRenderer {

    @SuppressWarnings({ "squid:S106", "squid:S3655" })
    default void render(final MenuItem item) {
        if (item.option().isPresent()) {
            System.out.print(item.option().get() + ". ");
        }
        System.out.print(item.text());
    }
}
