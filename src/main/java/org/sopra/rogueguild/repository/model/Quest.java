package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;

public class Quest {

    private static Quests quests;
    private String description;
    private int goldReward;
    private ArrayList<ItemCategory> requiredItems;
    private boolean isCompleted;

    public Quest(String description, int goldReward, ArrayList<ItemCategory> requiredItems){
        this.description = description;
        this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
        this.requiredItems = requiredItems;
        isCompleted = false;
        quests.addQuest(this);
    }


    public boolean checkRequirement(Player player){
        boolean questRequirements = false;
        boolean isMatched = false;
        int itemsMatched = 0;
        for (Item item : player.getInventory()) {
            isMatched = false;
            for(ItemCategory category : this.requiredItems){
                if(item.getItemCategory().equals(category) && isMatched ){
                    isMatched = true;
                    itemsMatched++;
                }
            }
        }
        if(itemsMatched == requiredItems.size()){
            questRequirements = true;
        }
        return questRequirements;
    }

    public void completeQuest(){
        this.isCompleted = true;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getStatus(){
        return this.isCompleted;
    }



}
