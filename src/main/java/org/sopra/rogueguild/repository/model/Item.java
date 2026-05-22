package org.sopra.rogueguild.repository.model;

public abstract class Item {
    private String name;
    private int price;
    private ItemCategory category;
    private final int basePrice;

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

    public void setPrice(int price) { this.price = price; }

    public String toString() { return name + " (" + price + " oro)"; }
}
