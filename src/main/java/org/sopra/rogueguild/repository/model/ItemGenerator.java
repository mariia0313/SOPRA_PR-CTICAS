package org.sopra.rogueguild.repository.model;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

public class ItemGenerator {
    
    private final Random random;
    private HashSet<String> generatedItems = new HashSet<String>(); 
    

    public ItemGenerator(){
        this.random = new Random();
    }

    // public static Item generate() {
    //     ItemCategory category = randomCategory();
        
    //     Item item = new Item(name, price, category);
    //     return ;
    // }
    
    private Item createRandomItem(){
        ItemCategory [] categories = ItemCategory.values();
        ItemCategory selectedCategory = categories[random.nextInt(categories.length)];
        Random random = new Random();
        int numero = random.nextInt(categories.length);
        boolean isUnique = false;
        String name = "";
        
        while(!isUnique){
            String suffix = suffixes[random.nextInt(suffixes.length)];
            String preffix = selectedCategory.getRandomPrefix();
            name = preffix + " " + suffix;
            if(generatedItems.add(name)){
                isUnique = true;
            }
        }

        int maxPrice = 0;
        int minPrice = 0;

        switch (selectedCategory) {
            case POTION:
                maxPrice = 40;
                minPrice = 10;
                break;
            case BOOTS: 
                minPrice = 20;
                maxPrice = 100; 
                break; 
            case HELMET: 
                minPrice = 20; 
                maxPrice = 150; 
                break; 
            case ARMOR: 
                minPrice = 50; 
                maxPrice = 200; 
                break; 
            case WEAPON:
                minPrice = 100; 
                maxPrice = 300; 
                break;  
            }
            int rawPrice = random.nextInt((maxPrice - minPrice) + 1) + minPrice; 
            int finalPrice = (int) (Math.round(rawPrice / 5.0) * 5);

            
        return;
    }

    private final String[] suffixes = { 
        "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz", 
        "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",  
        "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna", 
        "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña" 
    };
    



}
