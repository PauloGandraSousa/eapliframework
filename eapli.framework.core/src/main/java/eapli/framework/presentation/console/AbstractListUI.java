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

import eapli.framework.visitor.Visitor;

/**
 * An abstract generic List UI to avoid code duplication since most of the List
 * UIs are quite similar.
 *
 * @author Paulo Gandra Sousa
 *
 * @param <T>
 *            the type of element this user interface will list
 */
public abstract class AbstractListUI<T> extends AbstractUI {

    /**
     * The source to list.
     *
     * @return
     */
    protected abstract Iterable<T> elements();

    /**
     * The visitor that prints the content of each element.
     *
     * @return
     */
    protected abstract Visitor<T> elementPrinter();

    /**
     * The name of the type of elements to list.
     *
     * @return
     */
    protected abstract String elementName();

    /**
     * The header of the list in the UI.
     *
     * @return
     */
    protected abstract String listHeader();

    /**
     * The message to show if there are no elements to list.
     *
     * @return
     */
    protected abstract String emptyMessage();

    /**
     * General listing of elements. This is an application of the Template Method pattern (GoF)
     */
    @Override
    @SuppressWarnings("squid:S106")
    protected boolean doShow() {
        final Iterable<T> elems = elements();
        if (!elems.iterator().hasNext()) {
            System.out.println(emptyMessage() + elementName());
        } else {
            new ListWidget<>(listHeader(), elems, elementPrinter()).show();
        }
        return true;
    }

}
