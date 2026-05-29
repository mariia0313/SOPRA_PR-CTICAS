package org.sopra.rogueguild.repository.model;

/**
 * Representa un arma equipable por el jugador.
 *
 * Hereda de Item añadiendo el atributo de daño.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class Weapon extends Item {

    private int damage;

    public Weapon(String name, int price, int damage) {
        super(name, price, ItemCategory.WEAPON);
        this.damage = damage;
    }

    public String toString(){
        return getName() + " (" + getPrice() + " oro) | Daño: " + damage;
    }

    public int getDamage(){
        return this.damage;
    }
}
