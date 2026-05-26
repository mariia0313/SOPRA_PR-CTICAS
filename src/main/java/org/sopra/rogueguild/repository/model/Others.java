package org.sopra.rogueguild.repository.model;

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