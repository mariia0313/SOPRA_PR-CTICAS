package org.sopra.rogueguild.repository.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.ShopRepository;

public class WorldEventTest{
    @Test
    public void testIfWorldEventWorks(){
        ShopRepository shopRepository = new ShopRepository();
        WorldEventGenerator worldEventGenerator = new WorldEventGenerator();

        Map<Integer, Item> stock = shopRepository.getAllStock();
        Item itemTestigo = stock.get(1);
        
        int precioBase = itemTestigo.getBasePrice();
        ItemCategory categoriaItem = itemTestigo.getItemCategory();

        WorldEvent worldEvent = worldEventGenerator.generateRandomWorldEventer(shopRepository);

        double factor = worldEvent.getPercentage() / 100.0;
        double precioPreRedondeo;

        if (worldEvent.getDiscountOrPriceRise() == 0) {
            // Descuento
            precioPreRedondeo = precioBase - (precioBase * factor);
        } else {
            // Subida
            precioPreRedondeo = precioBase + (precioBase * factor);
        }

        int precioEsperado = (int) (Math.round(precioPreRedondeo / 5.0) * 5);
        if (precioEsperado <= 0) {
            precioEsperado = 5;
        }

        if (worldEvent.getTarget().equals("TODOS") || worldEvent.getTarget().equals(categoriaItem.name())) {
            
            assertEquals(precioEsperado, itemTestigo.getPrice(), 
                "El precio final del ítem afectado por el evento no es correcto.");

        } else {
            int precioOriginalRedondeado = (int) (Math.round(precioBase / 5.0) * 5);
            if (precioOriginalRedondeado <= 0) precioOriginalRedondeado = 5;

            assertEquals(precioOriginalRedondeado, itemTestigo.getPrice(), 
                "El precio de un ítem cambió misteriosamente cuando el evento no era para su categoría.");
        }
    }
    }