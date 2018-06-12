/**
 *
 */
package eapli.framework.presentation.console;

/**
 * @author Paulo Gandra Sousa
 *
 */
@SuppressWarnings("squid:S106")
public class HorizontalMenuRenderer extends MenuRenderer {

    public HorizontalMenuRenderer(Menu menu) {
        super(menu);
    }

    @Override
    protected void doShow() {
        System.out.print("| ");
        for (final MenuItem item : menu.itens()) {
            item.show();
            System.out.print(" | ");
        }
    }
}
