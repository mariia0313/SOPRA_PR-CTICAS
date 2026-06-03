package org.sopra.rogueguild.view.components;

import java.io.PrintStream;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Item;

/**
 * Componente de vista que renderiza el inventario de la tienda.
 *
 * Muestra los items disponibles ordenados por ID, con o sin número seleccionable
 * según si el jugador está en proceso de compra.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class StockView {
    private final PrintStream out;

    public StockView(PrintStream out) { this.out = out; }

    public void displayStock(Map<Integer, Item> itemMap, boolean inPurchase) {
        out.println("  _______________________________________________________________");
        out.println(" /  ___________________________________________________________  \\");
        out.println("|| /                                                           \\ ||");
        out.println("|| |                    INVENTARIO DE LA TIENDA                | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                                           | ||");

        itemMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> {
                    int id = e.getKey();
                    Item item = e.getValue();
                    
                    String infoItem = item.toString();

                    if (inPurchase) {
                        out.printf("|| |  [%d] %-50s  | ||%n", id, infoItem);
                    } else {
                        out.printf("|| |  [%s] %-50s  | ||%n", "-", infoItem);
                    }
                });

        out.println("|| |                                                           | ||");
        out.println("|| \\___________________________________________________________/ ||");
        out.println(" \\_______________________________________________________________/");
    }
}
