package org.sopra.rogueguild.repository.model;

import java.util.Random;


public class Incursion {

    private String description;
    private String shortName;
    private int goldReward;
    private Item itemReward;

    private Incursion(String description, String shortName){
        this.description = description;
        this.shortName = shortName;
    }

    public static Incursion createIncursionGoldReward(String description, String shortName){
        Incursion incursion = new Incursion(description, shortName);
        incursion.itemReward = null;
        Random random = new Random();
        int rawGold = 101 + random.nextInt(100);
        incursion.goldReward = (int) (Math.round(rawGold / 5.0) * 5);
        return incursion;
    }

    public static Incursion createIncursionMajorItem(String description, String shortName){
        Incursion incursion = new Incursion(description, shortName);
        incursion.goldReward = 0;
        ItemGenerator item = new ItemGenerator();
        incursion.itemReward = item.createRandomItem();
        return incursion;
    }
    
    public static Incursion createIncursionMinorItemWithGold(String description, String shortName){
        Incursion incursion = new Incursion(description, shortName);
        ItemGenerator item = new ItemGenerator();
        incursion.itemReward = item.createRandomItem();
        Random random = new Random();
        int rawGold = 10 + random.nextInt(50);
        incursion.goldReward = (int) (Math.round(rawGold / 5.0) * 5);
        return incursion;
    }

}
