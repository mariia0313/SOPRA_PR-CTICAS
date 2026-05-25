package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

import org.sopra.rogueguild.view.components.MessageView;

public class Quests {

    private ArrayList<Quest> createdQuests;


    public Quests(){
        createdQuests = new ArrayList<>();
    }

    public void showAvailableQuests(){
        String questsAvailable = "";
        for (int i = 0; i<createdQuests.size(); i++) {
            if(createdQuests.get(i).getStatus() == false){
                questsAvailable += "| " + (i+1) + ". " + createdQuests.get(i).getDescription() + "\n";                
            }
        }

        System.out.println(questsAvailable);

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
        createdQuests.add(DanzaDeMuerte);
        ArrayList<ItemCategory> requiredItems2 = new ArrayList<>(List.of(ItemCategory.WEAPON, ItemCategory.HELMET, ItemCategory.ARMOR, ItemCategory.BOOTS));
        Quest CaballeroDelFenix = new Quest("Caballero del Fénix", 100, requiredItems2);
        createdQuests.add(CaballeroDelFenix);
    }


}
