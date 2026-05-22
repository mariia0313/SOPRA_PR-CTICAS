package org.sopra.rogueguild.repository.model;
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
        String target = "";
        
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

        int percentage = random.nextInt(21) * 5;
        int discountOrPriceRise = random.nextInt(2);
        WorldEvent worldEvent = new WorldEvent(target, discountOrPriceRise, percentage);
        double rawPrice = 0;
        int finalPrice = 0;
        
        Map<Integer, Item> stock = shopRepository.getAllStock();
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