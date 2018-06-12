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
public class MenuItem {

    private final int option;
    private final String text;
    private final Action action;

    public MenuItem(int option, String text, Action action) {
        Preconditions.nonNull(text);
        Preconditions.nonNull(action);

        this.option = option;
        this.text = text;
        this.action = action;
    }

    public boolean select() {
        return action.execute();
    }

    public void show() {
        System.out.print(option + ". ");
        System.out.print(text());
    }

    /**
     * @return the text
     */
    public String text() {
        return text;
    }

    public int option() {
        return option;
    }
}
