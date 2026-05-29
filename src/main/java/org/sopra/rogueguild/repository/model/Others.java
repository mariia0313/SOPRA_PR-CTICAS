package org.sopra.rogueguild.repository.model;

/**
 * Representa un item de rareza especial disponible en la tienda.
 *
 * Hereda de Item con un atributo de valor genérico.
 * Tiene baja probabilidad de aparecer al generar items aleatorios.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class Others extends Item {

  private int value;

  public Others(String name, int price, int value) {
    super(name, price, ItemCategory.ARMOR);
    this.value = value;
  }

  public String toString(){
        return getName() + " (" + getPrice() + " oro) | Valor: " + value;
    }

}