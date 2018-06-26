/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.actions.menu;

import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.util.Preconditions;

/**
 * a menu item
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public class MenuItem {

    private final Optional<Integer> option;
    private final String text;
    private final Action action;

    /**
     * constructs a menu item with a corresponding action and id
     *
     * @param option
     * @param text
     * @param action
     */
    public MenuItem(final int option, final String text, final Action action) {
        Preconditions.nonNull(text);
        Preconditions.nonNull(action);

        this.option = Optional.of(option);
        this.text = text;
        this.action = action;
    }

    /**
     * constructs a non-actionable menu item with just a plain label
     *
     * @param text
     */
    public MenuItem(final String text) {
        Preconditions.nonNull(text);

        this.text = text;

        this.option = Optional.empty();
        this.action = null;
    }

    public boolean select() {
        return action.execute();
    }

    /**
     * @return the text
     */
    public String text() {
        return text;
    }

    public Optional<Integer> option() {
        return option;
    }
}
