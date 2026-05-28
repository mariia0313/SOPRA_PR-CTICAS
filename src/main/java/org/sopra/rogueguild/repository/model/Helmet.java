package org.sopra.rogueguild.repository.model;

import java.security.PublicKey;

public class Helmet extends Item {

    private int defense;

    public Helmet(String name, int price, int defense) {
        super(name, price, ItemCategory.HELMET);
        this.defense = defense;
    }

    public String toString(){
        return getName() + " (" + getPrice() + " oro) | Defensa: " + defense;
    }

    public int getDefense(){
        return defense;
    }
}
