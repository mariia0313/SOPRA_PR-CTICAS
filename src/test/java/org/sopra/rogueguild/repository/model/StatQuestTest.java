package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StatQuestTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Manolo", 200);
        
        ArrayList<Item> equippedItems = new ArrayList<>();
        player.getItemEquipped().set(0, new Weapon("Espada de Hierro", 50, 45));
        player.getItemEquipped().set(1, new Weapon(null, 0, 0));
        player.getItemEquipped().set(2, new Armor("Coraza de Acero", 100, 30));
        player.getItemEquipped().set(3, new Boots(null, 0, 0));
        player.getItemEquipped().set(4, new Helmet("Yelmo de Plata", 60, 20));
    }

    @Test
    public void testCheckRequirementDamageMatches() {
        StatQuest damageQuest = new StatQuest("Misión de Daño Alcanzable", 100, 0, 40);
        
        boolean cumpleDanio = damageQuest.checkRequirement(player);
        
        assertTrue(cumpleDanio, "El jugador tiene 45 de ataque; debería cumplir el requisito de 40.");
    }

    @Test
    public void testCheckRequirementArmorMatches() {

        StatQuest armorQuest = new StatQuest("Misión de Armadura Exacta", 100, 50, 0);
        
        boolean cumpleArmadura = armorQuest.checkRequirement(player);
        
        assertTrue(cumpleArmadura, "El jugador tiene 50 de armadura total; debería cumplir el requisito exacto de 50.");
    }

    @Test
    public void testCheckRequirementFailsWhenInsufficient() {
        StatQuest impossibleQuest = new StatQuest("Misión Imposible", 100, 70, 80);
        
        boolean cumpleRequisitos = impossibleQuest.checkRequirement(player);
        
        assertFalse(cumpleRequisitos, "El jugador no tiene suficiente nivel; el test debería fallar (false).");
    }
}