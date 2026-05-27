package org.sopra.rogueguild.repository.model;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private ArrayList<Quest> quests = new ArrayList<>();
    private ArrayList<Item> ItemEquipped = new ArrayList<>();

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
        ItemEquipped();

    }

    public void ItemEquipped(){
        Item itemWeapon = new Weapon(name, gold, gold);
        Item itemWeapon2 = new Weapon(name, gold, gold);
        Item itemArmor = new Armor(name, gold, gold);
        Item itemBoots = new Boots(name, gold, gold);
        Item itemHelmet = new Helmet(name, gold, gold);
        ItemEquipped.add(itemWeapon);
        ItemEquipped.add(itemWeapon2);
        ItemEquipped.add(itemArmor);
        ItemEquipped.add(itemBoots);
        ItemEquipped.add(itemHelmet);
    }

    public String getName() { return name; }

    public int getGold() { return gold; }

    public void buy(Item item) { this.gold -= item.getPrice(); addItem(item); }
    public void removeItem(Item item) {
        boolean removed = inventory.remove(item);
        if (!removed) {
            System.out.println("El item no está en el inventario");
        }
    }


    public void addItem(Item item) {
        inventory.add(item);
    }

    public String showInventory(){
        String items = "";
        for (Item item : inventory){
            items += item.toString() + "\n ";
        }

        return items;
    }

    public List<Item> getInventory(){
        return inventory;
    }

    public boolean addGold(int gold) throws Exception{
        this.gold += gold;
        return goldLimit();
    }

    public boolean goldLimit() throws Exception{
        boolean isPastLimit = false;
        if (this.gold > 500) {
            this.gold = 500;
            isPastLimit = true;
            throw new Exception("Has alcanzado el límite de oro");
        }

        return isPastLimit;
    }

    public ArrayList<Item> getItemEquipped(){
        return this.ItemEquipped;
    }

}

