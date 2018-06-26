/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.presentation.console.menu;

import eapli.framework.actions.Action;
import eapli.framework.actions.menu.Menu;
import eapli.framework.util.Preconditions;

/**
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public class ShowVerticalSubMenuAction implements Action {

    private final Menu menu;
    private final VerticalMenuRenderer renderer;

    public ShowVerticalSubMenuAction(final Menu menu) {
        Preconditions.nonNull(menu);

        this.menu = menu;
        renderer = new VerticalMenuRenderer(menu, new MenuItemRenderer() {
        });
    }

    @Override
    public boolean execute() {
        System.out.println("\n>> " + menu.title());
        renderer.render();
        return false;
    }
}
