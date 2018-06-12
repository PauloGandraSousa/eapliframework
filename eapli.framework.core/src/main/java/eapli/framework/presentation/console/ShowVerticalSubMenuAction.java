/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.presentation.console;

import eapli.framework.actions.Action;
import eapli.framework.util.Preconditions;

/**
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public class ShowVerticalSubMenuAction implements Action {

    private final Menu menu;
    private final VerticalMenuRenderer renderer;

    public ShowVerticalSubMenuAction(Menu menu) {
        Preconditions.nonNull(menu);

        this.menu = menu;
        renderer = new VerticalMenuRenderer(menu);
    }

    @Override
    public boolean execute() {
        System.out.println("\n>> " + menu.title());
        renderer.show();
        return false;
    }
}
