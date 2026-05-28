package org.sopra.rogueguild.repository.model;
import org.sopra.rogueguild.view.components.MessageView;
import java.util.ArrayList;

public class Quest {

    private static Quests quests = new Quests();
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
        if (this.requiredItems != null) {
            for(ItemCategory category : this.requiredItems){
                isMatched = false;
                for (Item item : player.getInventory()) {
                    if(item.getItemCategory().equals(category) && !isMatched ){
                        isMatched = true;
                        itemsMatched++;
                    }
                }
            }
            if(itemsMatched == requiredItems.size()){
                questRequirements = true;
            }
        }
        
        return questRequirements;
    }

    public boolean hasRequiredItems(){
        boolean result = false;
        if (requiredItems != null){
            result = true;
        }

        return result;
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

    public int getGoldReward(){
        return goldReward;
    }

    public ArrayList<ItemCategory> requirementsLeft(Player player){
        ArrayList<ItemCategory> requirementsLeft = new ArrayList<>();
        boolean isMatched = false;
        int itemsMatched = 0;
        for(ItemCategory category : this.requiredItems){
            isMatched = false;
                for (Item item : player.getInventory()) {
                if(item.getItemCategory().equals(category) && !isMatched ){
                    isMatched = true;
                    itemsMatched++;
                }
            }

            if (isMatched == false) {
                requirementsLeft.add(category);
            }
        }
        return requirementsLeft;

    }

}
