package org.sopra.rogueguild.repository.model;

public class Potion extends Item {

    private int healingAmount;

    public Potion(String name, int price, int healingAmount) {
        super(name, price, ItemCategory.POTION);
        this.healingAmount = healingAmount;
    }

    public String toString(){
        return getName() + " (" + getPrice() + " oro) | Curación: " + healingAmount;
    }
}
