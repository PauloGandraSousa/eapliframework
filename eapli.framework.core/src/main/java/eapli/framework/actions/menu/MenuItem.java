/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.actions.menu;

import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.actions.Actions;
import eapli.framework.util.Preconditions;
import eapli.framework.util.Strings;

/**
 * a menu item
 *
 * @author Paulo Gandra Sousa
 */
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
    private MenuItem(final String text) {
        Preconditions.nonNull(text);

        this.text = text;

        this.option = Optional.empty();
        this.action = Actions.FAIL;
    }

    public static MenuItem separator() {

        return separator("------------------");
    }

    public static MenuItem separator(final char symbol, final int size) {

        return separator(Strings.repeat(symbol, size));
    }

    public static MenuItem separator(final String label) {
        return new MenuItem(label);
    }

    public static MenuItem of(final int option, final String text, final Action action) {
        return new MenuItem(option, text, action);
    }

    /**
     * executes the action associated with this item. note that if this a
     * non-actionable item this method do nothing but return false
     *
     * @return true in case of success. false if the action failed or if this is a
     *         non-actionable item
     */
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
