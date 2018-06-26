/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.actions.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eapli.framework.util.Preconditions;

/**
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
        this.title = title;
    }

    public void add(final MenuItem item) {
        Preconditions.nonNull(item);

        itens.add(item);
        if (item.option().isPresent()) {
            itemByOption.put(item.option().get(), item);
        }
    }

    public String title() {
        return title;
    }

    public Iterable<MenuItem> itens() {
        return itens;
    }

    public MenuItem item(final int option) {
        return itemByOption.get(option);
    }
}
