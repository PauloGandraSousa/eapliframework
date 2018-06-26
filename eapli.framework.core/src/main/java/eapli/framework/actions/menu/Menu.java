/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.actions.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.util.Preconditions;

/**
 * a collection of actions identified by a label and id
 *
 * @author Paulo Gandra Sousa
 */
public class Menu {

    private final String title;
    private final List<MenuItem> itens = new ArrayList<>();
    private final Map<Integer, MenuItem> itemByOption = new HashMap<>();

    public Menu() {
        title = "";
    }

    public Menu(final String title) {
        Preconditions.nonEmpty(title);

        this.title = title;
    }

    public void addItem(final MenuItem item) {
        Preconditions.nonNull(item);

        itens.add(item);

        final Optional<Integer> op = item.option();
        if (op.isPresent()) {
            itemByOption.put(op.get(), item);
        }
    }

    /**
     * allows for hierarchical composition of menus (composite pattern)
     *
     * @param option
     * @param menu
     * @param action
     *            the action to execute when the user selects (opens) this submenu.
     *            usually it's a renderer
     */
    public void addSubMenu(final int option, final Menu menu, final Action action) {
        addItem(MenuItem.of(option, menu.title(), action));
    }

    public String title() {
        return title;
    }

    /**
     *
     * @return the collection of items in this menu. this collection can be directly
     *         manipulated by the caller, that is, it is mutable
     */
    public Iterable<MenuItem> itens() {
        return itens;
    }

    public MenuItem item(final int option) {
        return itemByOption.get(option);
    }
}
