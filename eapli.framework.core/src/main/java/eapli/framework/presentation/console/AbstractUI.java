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
package eapli.framework.presentation.console;

/**
 * Simple user interface abstract base class for Console I/O. derived classes
 * should implement the purely abstract methods and client code should call the
 * final methods {@link #mainLoop} or {@link #show} - Template Method GoF
 * pattern
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public abstract class AbstractUI {

    protected static final String SEPARATOR = "+------------------------------------------------------------------------------+";
    protected static final String BORDER = "+==============================================================================+";

    /**
     * derived classes should override this method to perform the actual
     * rendering of the UI. follows the Template Method pattern
     *
     * @return true if the user wants to leave this UI
     */
    protected abstract boolean doShow();

    /**
     * The title of the "window"
     *
     * @return the title of the window
     */
    public abstract String headline();

    /**
     *
     */
    public void mainLoop() {
        boolean wantsToExit;
        do {
            wantsToExit = show();
        } while (!wantsToExit);
    }

    /**
     * Template Method pattern
     *
     * @return {@code true} if the user wants to leave this UI
     */
    public boolean show() {
        drawFormTitle();
        final boolean wantsToExit = doShow();
        drawFormBorder();

        return wantsToExit;
    }

    protected void drawFormTitle() {
        System.out.println();
        drawFormTitle(headline());
        System.out.println();
    }

    protected void drawFormBorder() {
        System.out.println(BORDER);
        System.out.println();
    }

    protected void drawFormSeparator() {
        System.out.println(SEPARATOR);
    }

    protected void drawFormTitle(final String title) {
        final String titleBorder = BORDER.substring(0, 2) + " " + title + " "
                + BORDER.substring(4 + title.length());
        System.out.println(titleBorder);
    }
}
