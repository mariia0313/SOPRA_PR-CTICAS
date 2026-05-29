package org.sopra.rogueguild.repository.model;

/**
 * Clase base abstracta para todos los items del juego.
 *
 * Define los atributos comunes como nombre, precio y categoría,
 * y expone los métodos de acceso compartidos por todas las subclases.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public abstract class Item {
    private String name;
    private int price;
    private ItemCategory category;
    private final int basePrice;
    private int id;

    public Item(String name, int price, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.basePrice = price;
    }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public int getBasePrice() { return basePrice; }

    public ItemCategory getItemCategory() { return category; }

    public void setCategory(ItemCategory itemCategory){this.category = itemCategory;}

    public void setPrice(int price) { this.price = price; }

    public String toString() { return name + " (" + price + " oro)"; }

    public void setId(int id) { this.id = id; }

    public int getId(){ return this.id; }
}
