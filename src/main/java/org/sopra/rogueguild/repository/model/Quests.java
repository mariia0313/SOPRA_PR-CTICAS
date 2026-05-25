package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class Quests {

    private ArrayList<Quest> createdQuests;


    public Quests(){
        createdQuests = new ArrayList<>();
    }

    public void showAvailableQuests(){
        for (int i = 1; i<=createdQuests.size(); i++) {
            if(createdQuests.get(i).getStatus()){
                System.out.print(i + createdQuests.get(i).getDescription());                
            }
        }
    }
    public void addQuest(Quest quest){
        createdQuests.add(quest);
    }

    public ArrayList<Quest> getQuests(){
        return this.createdQuests;
    }


    public void createInitialQuests(){
        ArrayList<ItemCategory> requiredItems = new ArrayList<>(List.of(ItemCategory.WEAPON, ItemCategory.WEAPON));
        Quest DanzaDeMuerte = new Quest("Danza de muerte", 100, requiredItems);
        ArrayList<ItemCategory> requiredItems2 = new ArrayList<>(List.of(ItemCategory.WEAPON, ItemCategory.HELMET, ItemCategory.ARMOR, ItemCategory.BOOTS));
        Quest CaballeroDelFenix = new Quest("Caballero del Fénix", 100, requiredItems2);
    }


}
