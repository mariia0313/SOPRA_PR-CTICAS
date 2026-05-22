package org.sopra.rogueguild.repository.model;

public class Boots extends Item {

    private int agility;

    public Boots(String name, int price, int agility) {
        super(name, price, ItemCategory.BOOTS);
        this.agility = agility;
    }

    public String toString(){
        return getName() + " (" + getPrice() + " oro) | Agilidad: " + agility;
    }
}
