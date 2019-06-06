/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
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

import java.util.Optional;

import eapli.framework.actions.Action;
import eapli.framework.actions.Actions;
import eapli.framework.util.Strings;
import eapli.framework.validations.Preconditions;

/**
 * A menu item
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

        option = Optional.empty();
        action = Actions.FAIL;
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
     * @return true in case of success. false if the action failed or if this is
     *         a non-actionable item
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
