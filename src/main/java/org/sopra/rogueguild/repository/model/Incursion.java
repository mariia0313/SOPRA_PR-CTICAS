package org.sopra.rogueguild.repository.model;

import java.util.Random;

import org.sopra.rogueguild.view.components.MessageView;


public class Incursion {

    private String description;
    private String shortName;
    private int goldReward;
    private Item itemReward;

    public Incursion(int option, Player player) throws Exception{
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

    public void createIncursionGoldReward(Player player) throws Exception{
        MessageView message = new MessageView(System.out, 10);
        this.shortName = "Incursión de saqueo";
        this.itemReward = null;
        Random random = new Random();
        int rawGold = 101 + random.nextInt(100);
        this.goldReward = (int) (Math.round(rawGold / 5.0) * 5);
        int oldGold = player.getGold();

        try {
            player.addGold(this.goldReward);
            this.description = "Has obtenido " + this.goldReward + " de oro";
        } catch (Exception e) {
            int realRecievedOre = 500 - oldGold;
            this.description = "Has obtenido " + realRecievedOre + " de oro (Límite alcanzado)";
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
    
    public void createIncursionMinorItemWithGold(Player player) throws Exception{
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

        try {
            player.addGold(goldReward);
            this.description = "Has obtenido " + this.goldReward + " de oro";
        } catch (Exception e){
            int realRecievedOre = 500 - oldGold;
            this.description = "Has obtenido " + realRecievedOre + " de oro (Límite alcanzado)";
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
