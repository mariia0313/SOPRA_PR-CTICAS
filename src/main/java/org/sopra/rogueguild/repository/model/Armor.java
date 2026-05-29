package org.sopra.rogueguild.repository.model;

/**
 * Representa una armadura equipable por el jugador.
 *
 * Hereda de Item añadiendo el atributo de escudo.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class Armor extends Item {

  private int shield;

  public Armor(String name, int price, int shield) {
    super(name, price, ItemCategory.ARMOR);
    this.shield = shield;
  }

  public String toString(){
        return getName() + " (" + getPrice() + " oro) | Escudo: " + shield;
    }

  public int getShield(){
    return this.shield;
  }

}
