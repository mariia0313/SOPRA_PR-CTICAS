package org.sopra.rogueguild.repository.model;

/**
 * Representa unas botas equipables por el jugador.
 *
 * Hereda de Item añadiendo el atributo de agilidad.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
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
