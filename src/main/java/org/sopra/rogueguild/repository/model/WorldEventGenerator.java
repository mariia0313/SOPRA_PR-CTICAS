package org.sopra.rogueguild.repository.model;

/**
 * Genera un evento de mundo aleatorio al inicio de cada sesión de tienda.
 *
 * Selecciona aleatoriamente una categoría de items, un porcentaje y si el efecto
 * es descuento o subida de precio, y aplica los cambios al stock del repositorio.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */



import java.util.Map;
import java.util.Random;

import org.sopra.rogueguild.repository.ShopRepository;

public class WorldEventGenerator{

    private final Random random;

    public WorldEventGenerator(){
        this.random = new Random();
    }

    public WorldEvent generateRandomWorldEventer(ShopRepository shopRepository){
        int targetItem = random.nextInt(6);
        Map<Integer, Item> stock = shopRepository.getAllStock();
        String target = "";
        int percentage = random.nextInt(21) * 5;
        int discountOrPriceRise = random.nextInt(2);
        double rawPrice = 0;
        int finalPrice = 0;
        
        switch(targetItem){
            case 0:
                target = "TODOS";
            break;
            case 1:
                target = "WEAPON";
            break;
            case 2:
                target = "BOOTS";
            break;
            case 3:
                target = "ARMOR";
            break;
            case 4:
                target = "HELMET";
            break;
            case 5:
                target = "POTION";
            break;
        }

        WorldEvent worldEvent = new WorldEvent(target, discountOrPriceRise, percentage);

        for (Item item : stock.values()) {
            if (target.equals("TODOS") || item.getItemCategory().name().equals(target)) {
                double factor = percentage / 100.0;
                if (discountOrPriceRise == 0) {
                    rawPrice = item.getBasePrice() - item.getBasePrice()*factor;
                } else {
                    rawPrice = item.getBasePrice() + item.getBasePrice()*factor;
                }

                finalPrice = (int) (Math.round(rawPrice / 5.0) * 5);
                item.setPrice(finalPrice);
            }
            
        }
        return worldEvent;
    }
}