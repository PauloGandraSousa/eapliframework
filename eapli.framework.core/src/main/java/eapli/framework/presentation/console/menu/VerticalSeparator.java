/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.presentation.console.menu;

import eapli.framework.actions.menu.MenuItem;

/**
 * A separator for vertical menus.
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public class VerticalSeparator extends MenuItem {

    private static VerticalSeparator instance = new VerticalSeparator();

    public VerticalSeparator() {
        super("------------------");
    }

    public static VerticalSeparator separator() {
        return instance;
    }
}
