package org.sopra.rogueguild.repository.model;

/**
 * Representa un casco equipable por el jugador.
 *
 * Hereda de Item añadiendo el atributo de defensa.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
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
