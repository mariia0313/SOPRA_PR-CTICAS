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

    private int healPoint;

    public Potion(String name, int price, int healPoint) {
        super(name, price, ItemCategory.POTION);
        this.healPoint = healPoint;
    }

    public String toString(){
        return getName() + " (" + getPrice() + " oro) | Curación: " + healPoint;
    }

    public int getHealPoint(){
        return healPoint;
    }
}
