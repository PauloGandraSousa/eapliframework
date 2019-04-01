/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * A collection of actions identified by a label and id,
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

    /**
     * adds an item to the menu
     *
     * @param item
     */
    public void addItem(final MenuItem item) {
        Preconditions.nonNull(item);

        itens.add(item);

        final Optional<Integer> op = item.option();
        op.ifPresent(i -> itemByOption.put(i, item));
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

    /**
     * returns the MenuItem associated with an option in the menu
     *
     * @param option
     * @return
     */
    public MenuItem item(final int option) {
        return itemByOption.get(option);
    }
}
