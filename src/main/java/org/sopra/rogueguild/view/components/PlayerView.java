package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import static org.sopra.rogueguild.view.utils.Ansi.*;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Item;

public class PlayerView {
    private final PrintStream out;

    public PlayerView(PrintStream out) { this.out = out; }

    public void playerStatus(Player player) {
        out.println();
        out.println("    +---------------------------------------------------+");
        out.println("    |                 " + c(GRAY, "ESTADO COMPRADOR") + "                  |");
        out.println("    +--+------------------------------------------------+");
        out.println("       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        out.println("       | ░    NOMBRE:        " + player.getName());
        out.println("       | ░    ORO:           " + player.getGold() + " monedas");
        out.println("       | ░    INVENTARIO:    " + player.showInventory());
        out.println();
    }

    public void playerInventoryToSell(Player player){
        out.println("    +---------------------------------------------------+");
        out.println("    |                 " + ("INVENTARIO JUGADOR") + "    |");
        out.println("    +--+------------------------------------------------+");
        for (int i = 0; i < player.getInventory().size(); i++){
            out.println("   |  " + (i+1) + ". " + player.getInventory().get(i).getName());
        }
    }
}
