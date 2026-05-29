package org.sopra.rogueguild.repository.model;

/**
 * Representa una poción consumible disponible en la tienda.
 *
 * Hereda de Item añadiendo el atributo de cantidad de curación.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
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
