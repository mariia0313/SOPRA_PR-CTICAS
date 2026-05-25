package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;

public class Quest {

    private String description;
    private int goldReward;
    private ArrayList<Item> requiredItems;
    private boolean isCompleted;

    public Quest(String description, int goldReward, ArrayList<Item> requiredItems){
        this.description = description;
        this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
        this.requiredItems = requiredItems;
        isCompleted = false;
    }


    public boolean checkRequirement(Player player){
        boolean questRequirements = false;
        if(player.getInventory().containsAll(requiredItems)){
            questRequirements = true;
        }
        return questRequirements;
    }

    public void completeQuest(){
        this.isCompleted = true;
    }

}
