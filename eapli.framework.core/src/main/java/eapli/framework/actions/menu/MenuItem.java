/*
 * Copyright (c) 2013-2019 the original author or authors.
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
import eapli.framework.util.Strings;
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
    }

    /**
     * constructs a non-actionable menu item with just a plain label
     *
     * @param text
     */
    private MenuItem(final String text) {
        Preconditions.nonNull(text);

        this.text = text;
    }

    /**
     * Factory method constructs a non-actionable menu item to work as a separator among the other menu
     * Items.
     *
     * @param symbol
     * @param size
     *
     * @return
     */
    public static MenuItem separator(final char symbol, final int size) {

        return separator(Strings.repeat(symbol, size));
    }

    /**
     * Factory method constructs a non-actionable menu item to work as a separator among the other menu
     * Items.
     *
     * @param label
     *
     * @return
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
     * @return
     */
    public static MenuItem of(final int option, final String text, final Action action) {
        return new ActionableMenuItem(option, text, action);
    }

    /**
     * @return the text
     */
    public String text() {
        return text;
    }

    /**
     * Returns the option associated with this menu item or an empty Optional if this is a
     * non-actionable item.
     *
     * @return the option associated with this menu item or an empty Optional if this is a
     *         non-actionable item.
     */
    public abstract Optional<Integer> option();

    /**
     * Executes the action associated with this item. Non-actionable items like the ones created with
     * {@link #separator(String)} will simply return false.
     *
     * @return true in case of success. false if the action failed.
     */
    public abstract boolean select();
}
