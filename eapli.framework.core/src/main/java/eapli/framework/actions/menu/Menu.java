/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.actions.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.validations.Preconditions;

/**
 * A collection of actions identified by a label and id.
 *
 * @author Paulo Gandra Sousa
 */
public class Menu {

    private final String title;
    private final List<MenuItem> itens = new ArrayList<>();
    private final Map<Integer, MenuItem> itemByOption = new HashMap<>();

    /**
     * Constructs a Menu.
     *
     */
    public Menu() {
        title = "";
    }

    /**
     * Constructs a Menu with a specific title.
     *
     * @param title
     */
    public Menu(final String title) {
        Preconditions.nonEmpty(title);

        this.title = title;
    }

    /**
     * Adds an item to the menu.
     *
     * @param item
     */
    public void addItem(final MenuItem item) {
        Preconditions.nonNull(item);

        itens.add(item);

        item.option().ifPresent(i -> {
            if (itemByOption.containsKey(i)) {
                throw new IllegalStateException(
                        "Cannot add an item with an already existing option id");
            }
            itemByOption.put(i, item);
        });
    }

    /**
     * Helper method.
     *
     * @param option
     * @param text
     * @param action
     */
    public void addItem(final int option, final String text, final Action action) {
        addItem(MenuItem.of(option, text, action));
    }

    /**
     * Allows for hierarchical composition of menus (composite pattern).
     *
     * @param option
     * @param submenu
     */
    public void addSubMenu(final int option, final Menu submenu) {
        addItem(MenuItem.submenu(option, submenu));
    }

    /**
     *
     * @return the title of the menu
     */
    public String title() {
        return title;
    }

    /**
     * Returns the collection of items in this menu. This collection can be directly manipulated by
     * the
     * caller, that is, it is mutable, which allow for dynamic menus.
     *
     * @return the collection of items in this menu.
     */
    public Iterable<MenuItem> itens() {
        return itens;
    }

    /**
     * Returns the MenuItem associated with an option in the menu.
     *
     * @param option
     * @return the MenuItem associated with an option in the menu.
     */
    public Optional<MenuItem> item(final int option) {
        return Optional.ofNullable(itemByOption.get(option));
    }
}
