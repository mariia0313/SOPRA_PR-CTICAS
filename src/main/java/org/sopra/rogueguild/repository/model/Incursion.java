package org.sopra.rogueguild.repository.model;

import java.util.Random;


public class Incursion {

    private String description;
    private String shortName;
    private int goldReward;
    private Item itemReward;

    public Incursion(int option, Player player){
        switch (option) {
            case 1:
                createIncursionMajorItem(player);
                break;

            case 2:
                createIncursionGoldReward(player);;
                break;
                
            case 3:
                createIncursionMinorItemWithGold(player);
                break;    
        
            default:
                break;
        }
    }

    public void createIncursionGoldReward(Player player){
        this.shortName = "Incursión de saqueo";
        this.itemReward = null;
        Random random = new Random();
        int rawGold = 101 + random.nextInt(100);
        this.goldReward = (int) (Math.round(rawGold / 5.0) * 5);
        int oldGold = player.getGold();
        if (player.addGold(goldReward) == true){
            this.description = "Has obtenido " + (500-oldGold) + " de oro";
        } else {
            this.description = "Has obtenido " + this.goldReward + " de oro";
        }
    }

    public void createIncursionMajorItem(Player player){
        this.shortName = "Incursión de alto valor";
        this.goldReward = 0;
        ItemGenerator item = new ItemGenerator();
        this.itemReward = item.createRandomItem();
        this.description = "Has obtenido el objeto " + itemReward.getName();
        player.addItem(itemReward);
    }
    
    public void createIncursionMinorItemWithGold(Player player){
        this.shortName = "Incursión menor";
        ItemGenerator itemGenerator = new ItemGenerator();
        Item item = itemGenerator.createRandomItem();
        Random random = new Random();
        int rawGold = random.nextInt(50) + 1;
        item.setPrice((int) (Math.round(rawGold / 5.0) * 5));
        rawGold = random.nextInt(30) + 1;
        this.goldReward = (int) (Math.round(rawGold / 5.0) * 5);
        this.itemReward = item;
        int oldGold = player.getGold();
        if (player.addGold(goldReward) == true){
            this.description = "Has obtenido " + (500-oldGold) + " de oro y el objeto " + this.itemReward.getName();;
        } else {
            this.description = "Has obtenido " + this.goldReward + " de oro y el objeto " + this.itemReward.getName();;
        }

        player.addItem(itemReward);
    }


    public int getGold(){
        return goldReward;
    }

    public Item getItem(){
        return itemReward;
    }

    public String getDescription(){
        return description;
    }
}
