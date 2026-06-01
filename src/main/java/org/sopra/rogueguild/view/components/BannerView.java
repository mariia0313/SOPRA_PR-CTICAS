package org.sopra.rogueguild.view.components;
import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.Ansi;
import static org.sopra.rogueguild.view.utils.Ansi.PURP;
import static org.sopra.rogueguild.view.utils.Ansi.RED;
import static org.sopra.rogueguild.view.utils.Ansi.c;

/**
 * Componente de vista que renderiza el banner principal y el menú de opciones.
 *
 * Muestra el logo del juego con colores ANSI y las opciones disponibles
 * para el jugador en cada iteración del bucle principal.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class BannerView {
    private final PrintStream out;

    public BannerView(PrintStream out) { this.out = out; }

    public void landingPage() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |  " + c(RED, " ___                          ") + c(PURP, " _        _ ") + "   | ||");
        out.println("|| |  " + c(RED, "| _ \\___  __ _ _  _ ___  ") + c(PURP, " __ _(_)_ _ __| |") + "   | ||");
        out.println("|| |  " + c(RED, "|   / _ \\/ _` | || / -_) ") + c(PURP, "/ _` | | | / _` |") + "   | ||");
        out.println("|| |  " + c(RED, "|_|_\\___/\\__, |\\_,_\\___| ") + c(PURP, "\\__, |_|_|_\\__,_|") + "   | ||");
        out.println("|| |  " + c(RED, "         |___/           ") + c(PURP, "|___/            ") + "   | ||");
        out.println("|| |                                               | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Ver productos de la tienda               | ||");
        out.println("|| |  [2] Comprar un producto                      | ||");
        out.println("|| |  [3] Vender un items                          | ||");
        out.println("|| |  [4] Realizar una incursión                   | ||");
        out.println("|| |  [5] Realizar una misión                      | ||");
        out.println("|| |  [6] Equipar Item                             | ||");
        out.println("|| |  [7] Desequipar item                          | ||");
        out.println("|| |  [8] Ver items equipados                      | ||");
        out.println("|| |  [9] Viajar a otra ciudad                     | ||");           
        out.println("|| |" + c(Ansi.GRAY, "  [0] Salir                                    ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}
