package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import java.util.HashSet;

public class ItemGeneratorOthersTest {

    @Test
    public void testOthersCategoryProbabilityIsFivePercent() {
        ItemGenerator generator = new ItemGenerator();
        int totalIterations = 2000;
        int othersCount = 0;

        for (int i = 0; i < totalIterations; i++) {
            Item item = generator.createRandomItem();
            
            if (esOthers(item)) {
                othersCount++;
            }
        }

        double realPercentage = (double) othersCount / totalIterations * 100;

        System.out.println("Porcentaje real de OTHERS obtenido: " + realPercentage + "%");

        boolean rangoCorrecto = realPercentage >= 0 && realPercentage <= 5;
        
        assertTrue(rangoCorrecto, "La probabilidad se desvía demasiado: " + realPercentage + "%");
    }

    private boolean esOthers(Item item) {
        boolean resultado = false;
        if (item != null && item.getItemCategory() == ItemCategory.OTHERS) {
            resultado = true;
        }
        return resultado;
    }
}