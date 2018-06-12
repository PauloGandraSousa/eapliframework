/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.presentation.console;

import eapli.framework.actions.Action;
import eapli.framework.util.Preconditions;

/**
 * an action that shows a UI
 *
 * @author Paulo Gandra Sousa
 */
public class ShowUiAction implements Action {

    private final AbstractUI ui;

    public ShowUiAction(AbstractUI ui) {
        Preconditions.nonNull(ui);

        this.ui = ui;
    }

    @Override
    public boolean execute() {
        ui.show();
        return false;
    }
}
