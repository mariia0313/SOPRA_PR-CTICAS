package org.sopra.rogueguild.repository.model;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }
    public String getName() { return name; }
    public int getGold() { return gold; }
    public void buy(Item item) { this.gold -= item.getPrice(); this.inventory.add(item); }
    public void removeItem(Item item) {
    boolean removed = inventory.remove(item);
    if (!removed) {
        System.out.println("El item no está en el inventario");
    }
}

}

