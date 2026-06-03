package org.sopra.rogueguild.repository.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HealingPotionTest{
    
    @Test
    public void testHealingPotion(){
        Player player = new Player("Player", 500);
        player.setHitPoints(10);
        Item item = new Potion("TestPotion", 100, 10);
        player.healPlayer((Potion) item);
        int beforeHeal = player.getHitPoints();
        assertEquals(beforeHeal, 20);
    }
}