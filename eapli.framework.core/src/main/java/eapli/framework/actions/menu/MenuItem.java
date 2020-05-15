/*
 * Copyright (c) 2013-2020 the original author or authors.
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

import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.strings.util.Strings;
import eapli.framework.validations.Preconditions;

/**
 * A menu item.
 *
 * @author Paulo Gandra Sousa
 */
public abstract class MenuItem {

    private final String text;

    private static class ActionableMenuItem extends MenuItem {
        private final int option;
        private final Action action;

        /**
         * Constructs a menu item with a corresponding action and id.
         *
         * @param option
         * @param text
         * @param action
         */
        protected ActionableMenuItem(final int option, final String text, final Action action) {
            super(text);

            Preconditions.nonNull(action);

            this.option = option;
            this.action = action;
        }

        @Override
        public boolean select() {
            return action.execute();
        }

        @Override
        public Optional<Integer> option() {
            return Optional.of(option);
        }

        @Override
        public boolean isSubmenu() {
            return false;
        }

        @Override
        public Optional<Menu> submenu() {
            return Optional.empty();
        }
    }

    private static class NonActionableMenuItem extends MenuItem {

        protected NonActionableMenuItem(final String label) {
            super(label);
        }

        @Override
        public boolean select() {
            return false;
        }

        @Override
        public Optional<Integer> option() {
            return Optional.empty();
        }

        @Override
        public boolean isSubmenu() {
            return false;
        }

        @Override
        public Optional<Menu> submenu() {
            return Optional.empty();
        }
    }

    private static class SubMenuItem extends MenuItem {
        private final int option;
        private final Menu sub;

        protected SubMenuItem(final int option, final Menu sub) {
            super(sub.title());
            this.option = option;
            this.sub = sub;
        }

        @Override
        public boolean select() {
            return false;
        }

        @Override
        public Optional<Integer> option() {
            return Optional.of(option);
        }

        @Override
        public boolean isSubmenu() {
            return true;
        }

        @Override
        public Optional<Menu> submenu() {
            return Optional.of(sub);
        }
    }

    /**
     * Constructs a non-actionable menu item with just a plain label.
     *
     * @param text
     */
    private MenuItem(final String text) {
        Preconditions.nonNull(text);

        this.text = text;
    }

    /**
     * Factory method constructs a non-actionable menu item to work as a
     * separator among the other menu Items.
     *
     * @param symbol
     * @param size
     *
     * @return a new Menu Item
     */
    public static MenuItem separator(final char symbol, final int size) {

        return separator(Strings.repeat(symbol, size));
    }

    /**
     * Factory method constructs a non-actionable menu item to work as a
     * separator among the other menu Items.
     *
     * @param label
     *
     * @return a separator menu item
     */
    public static MenuItem separator(final String label) {
        return new NonActionableMenuItem(label);
    }

    /**
     * Factory method to construct an actionable menu item.
     *
     * @param option
     * @param text
     * @param action
     * @return a new menu item
     */
    public static MenuItem of(final int option, final String text, final Action action) {
        return new ActionableMenuItem(option, text, action);
    }

    /**
     * Factory method to create a submenu.
     *
     * @param option
     * @param sub
     * @return a sub menu
     */
    public static MenuItem submenu(final int option, final Menu sub) {
        return new SubMenuItem(option, sub);
    }

    /**
     * @return the text
     */
    public String text() {
        return text;
    }

    /**
     * Returns the option associated with this menu item or an empty Optional if
     * this is a non-actionable item.
     *
     * @return the option associated with this menu item or an empty Optional if
     *         this is a non-actionable item.
     */
    public abstract Optional<Integer> option();

    /**
     * Executes the action associated with this item. Non-actionable items like
     * the ones created with {@link #separator(String)} will simply return
     * false.
     *
     * @return true in case of success. false if the action failed.
     */
    public abstract boolean select();

    /**
     *
     * @return true if the item is a submenu.
     */
    public abstract boolean isSubmenu();

    /**
     *
     * @return the submenu or an empty optional if this item is not a submenu
     */
    public abstract Optional<Menu> submenu();
}
