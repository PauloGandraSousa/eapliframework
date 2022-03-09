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
package eapli.framework.presentation.console.menu;

import java.util.Optional;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.validations.Preconditions;

/**
 * Base class for menu renderers using the console as I/O.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class MenuRenderer {

    protected final Menu menu;
    protected final MenuItemRenderer itemRenderer;

    protected MenuRenderer(final Menu menu, final MenuItemRenderer itemRenderer) {
        Preconditions.nonNull(menu);

        this.menu = menu;
        this.itemRenderer = itemRenderer;
    }

    /**
     * Renders the menu on the screen and asks the user for an option; when the user
     * selects an option, the corresponding action is executed.
     *
     * @return the success or insuccess of the action's execution
     */
    public boolean render() {
        doRender();

        final MenuItem item = readOption();
        if (item.isSubmenu()) {
            return item.submenu().map(this::doRenderSubmenu).orElseThrow(IllegalStateException::new);
        }
        return item.select();
    }

    /**
     * Actually renders the sub menu and executes the selection. Usually this is a
     * "recursive" MenuRenderer.
     *
     * @param submenu
     * @return
     */
    @SuppressWarnings("squid:S106")
    protected boolean doRenderSubmenu(final Menu submenu) {
        final MenuRenderer renderer = new VerticalMenuRenderer(submenu, itemRenderer);
        System.out.println("\n>> " + submenu.title());
        renderer.render();
        return false;
    }

    /**
     * Actually renders the itens of this menu level.
     */
    protected abstract void doRender();

    /**
     * @return
     */
    protected MenuItem readOption() {
        Optional<MenuItem> item;
        do {
            final int option = Console.readInteger("\nPlease choose an option");
            item = menu.item(option);
        } while (!item.isPresent());
        return item.get();
    }
}