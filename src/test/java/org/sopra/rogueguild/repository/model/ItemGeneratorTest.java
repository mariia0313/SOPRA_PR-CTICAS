package org.sopra.rogueguild.repository.model;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ItemGeneratorTest{
    
    @Test
    public void testItemNotRepeated(){
        ItemGenerator itemGenerator = new ItemGenerator();
        HashSet<Item> hashSet = new HashSet<>();

        for (int i = 0; i < 100; i++){
            boolean isAdded = hashSet.add(itemGenerator.createRandomItem());
            assertTrue(isAdded, "Alerta de duplicado");
        }

        assertEquals(100, hashSet.size(), "El número de nombres únicos no coincide con el total de ítems generados.");
    }
}