package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest{
    @Test
    public void testRemoveItem(){
        Player player = new Player("Héroe", 300);
        Item itemInInventory = new Weapon("Espada Inicial", 50, 10);
        player.addItem(itemInInventory);
        Item ghostItem = new Weapon("Hacha Legendaria Inexistente", 20, 15);

        assertDoesNotThrow(()-> {
            player.removeItem(ghostItem);
        }, "El método removeItem lanzó un error inesperado al intentar borrar un objeto inexistente.");

        assertEquals(1, player.getInventory().size(), "El tamaño del inventario cambió de forma incorrecta.");

        assertTrue(player.getInventory().contains(itemInInventory), "El ítem original desapareció del inventario.");

        assertFalse(player.getInventory().contains(ghostItem));
    }
}