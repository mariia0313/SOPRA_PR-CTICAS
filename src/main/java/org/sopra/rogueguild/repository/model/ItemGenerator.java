package org.sopra.rogueguild.repository.model;

import java.util.HashSet;
import java.util.Random;

/**
 * Genera items aleatorios para poblar el stock de la tienda.
 *
 * Combina prefijos por categoría con sufijos temáticos para crear nombres únicos,
 * y calcula el precio final en función del valor del atributo del item.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class ItemGenerator {
    
    private final Random random;
    private HashSet<String> generatedItems = new HashSet<String>(); 
    private final String[] suffixes = { 
        "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz", 
        "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",  
        "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna", 
        "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña" 
    };
    

    public ItemGenerator(){
        this.random = new Random();
    }
    
    public Item createRandomItem(){
        boolean isUnique = false;
        String name = "";
        int trys = 0;
        ItemCategory selectedCategory;
        int maxPrice = 0;
        int minPrice = 0;
        int atributteValue = 0;
        int finalPrice = 0;
        Item item = null;

        int probability = this.random.nextInt(100) + 1; 
        
        if (probability <= 5) {
            selectedCategory = ItemCategory.OTHERS;
        } else {
            ItemCategory[] comunes = {
                ItemCategory.POTION, 
                ItemCategory.BOOTS, 
                ItemCategory.HELMET, 
                ItemCategory.ARMOR, 
                ItemCategory.WEAPON
            };
            selectedCategory = comunes[this.random.nextInt(comunes.length)];
        }
        
        while(!isUnique){
            String suffix = suffixes[this.random.nextInt(suffixes.length)];
            String preffix = selectedCategory.getRandomPrefix();
            name = preffix + " " + suffix;
            if(generatedItems.add(name) || trys > 50){
                isUnique = true;
            }
            trys++;
        }


        switch (selectedCategory) {
            case POTION:
                maxPrice = 40;
                minPrice = 10;
                atributteValue = this.random.nextInt(20);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Potion(name, finalPrice, atributteValue);
                break;
            case BOOTS: 
                minPrice = 20;
                maxPrice = 100;
                atributteValue = this.random.nextInt(50);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Boots(name, finalPrice, atributteValue); 
                break; 
            case HELMET: 
                minPrice = 20; 
                maxPrice = 150;
                atributteValue = this.random.nextInt(50);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Helmet(name, finalPrice, atributteValue); 
                break; 
            case ARMOR: 
                minPrice = 50; 
                maxPrice = 200;
                atributteValue = this.random.nextInt(50);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Armor(name, finalPrice, atributteValue); 
                break; 
            case WEAPON:
                minPrice = 100; 
                maxPrice = 300;
                atributteValue = this.random.nextInt(50);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Weapon(name, finalPrice, atributteValue); 
                break;
            case OTHERS:
                minPrice = 250; 
                maxPrice = 300;
                atributteValue = this.random.nextInt(50);
                finalPrice = getFinalPrice(maxPrice, minPrice, atributteValue);
                item = new Others(name, finalPrice, atributteValue);
                break;
        }

        return item;
    }

    private int getFinalPrice(int maxPrice, int minPrice, int atributteValue) {
        int range = maxPrice - minPrice;
        int rawPrice = minPrice + (int)((atributteValue / 50.0) * range); 
        int finalPrice = (int) (Math.round(rawPrice / 5.0) * 5);
        return finalPrice;
    }


}
