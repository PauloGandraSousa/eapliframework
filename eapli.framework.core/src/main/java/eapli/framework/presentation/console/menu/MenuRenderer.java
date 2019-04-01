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
package eapli.framework.presentation.console.menu;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.util.Console;
import eapli.framework.util.Preconditions;

/**
 * base class for menu renderers using the console as I/O
 *
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class MenuRenderer {

    protected final Menu menu;
    protected final MenuItemRenderer itemRenderer;

    public MenuRenderer(final Menu menu, final MenuItemRenderer itemRenderer) {
        Preconditions.nonNull(menu);

        this.menu = menu;
        this.itemRenderer = itemRenderer;
    }

    /**
     * renders the menu on the screen and asks the user for an option. when the user
     * selects an option, the corresponding action is executed
     *
     * @return the success or insuccess of the action's execution
     */
    public boolean render() {
        doShow();

        final MenuItem item = readOption();
        return item.select();
    }

    protected abstract void doShow();

    /**
     * @return
     */
    protected MenuItem readOption() {
        MenuItem item;
        do {
            final int option = Console.readInteger("\nPlease choose an option");
            item = menu.item(option);
        } while (item == null);
        return item;
    }
}