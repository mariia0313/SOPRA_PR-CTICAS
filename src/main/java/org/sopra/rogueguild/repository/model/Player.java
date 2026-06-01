
package org.sopra.rogueguild.repository.model;

/**
 * Representa al jugador dentro del juego
 * 
 * Gestiona el oro, el inventario de items comprados y los slots de equipamiento.
 * Los slots de equipamiento se inicializan vacíos al crear el jugador.
 * 
 * @author Marc Nacher
 * @author Maria Herrero
 */

import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private ArrayList<Quest> quests = new ArrayList<>();
    private ArrayList<Item> ItemEquipped = new ArrayList<>();
    private City currenCity;

    /**
     * Crea un nuevo jugador con nombre y oro iniciales.
     * Inicializa los slots de equipamiento vacíos.
     *
     * @param name nombre del jugador
     * @param gold cantidad de oro inicial
     */

    public Player(String name, int gold, City startingCity) {
        this.name = name;
        this.gold = gold;
        this.currenCity = startingCity;
        equipItems();

    }

    /**
     * Inicializa los slots de equipamiento con items null
     * Orden: índice 0-1 armas, 2 armadura, 3 botas, 4 casco.
     */
    public void equipItems(){
        Item itemWeapon = new Weapon(null, gold, gold);
        Item itemWeapon2 = new Weapon(null, gold, gold);
        Item itemArmor = new Armor(null, gold, gold);
        Item itemBoots = new Boots(null, gold, gold);
        Item itemHelmet = new Helmet(null, gold, gold);
        ItemEquipped.add(itemWeapon);
        ItemEquipped.add(itemWeapon2);
        ItemEquipped.add(itemArmor);
        ItemEquipped.add(itemBoots);
        ItemEquipped.add(itemHelmet);
    }

    /** @return nombre del jugador */
    public String getName() { return name; }

     /** @return oro actual del jugador */
    public int getGold() { return gold; }

     /**
     * Descuenta el precio del item del oro del jugador y lo añade al inventario.
     *
     * @param item item a comprar; su precio se resta del oro actual
     */
    public void buy(Item item) { this.gold -= item.getPrice(); addItem(item); }
    public void removeItem(Item item) {
        boolean removed = inventory.remove(item);
        if (!removed) {
            System.out.println("El item no está en el inventario");
        }
    }

    /**
     * Añade un item al inventario.
     *
     * @param item item a añadir
     */

    public void addItem(Item item) {
        inventory.add(item);
    }

      /**
     * Devuelve el inventario del jugador de forma numerada.
     *
     * @return cadena con los items del inventario, uno por línea, o cadena vacía si está vacío
     */
    public String showInventory(){
        String items = "";
        for (int i = 0; i < inventory.size(); i++){
            items += (i+1) + ". " + inventory.get(i).toString() + "\n ";
        }

        return items;
    }

     /**
     * @return lista de items en el inventario del jugador
     */
    public List<Item> getInventory(){
        return inventory;
    }

     /**
     * Añade oro al jugador. Si el total supera 500, lo limita a 500 y lanza excepción.
     *
     * @param gold cantidad de oro a añadir
     * @return Devuelve true si se alcanzó el límite, false en caso contrario
     * @throws Exception si el oro supera el límite de 500
     */
    public boolean addGold(int gold) throws Exception{
        this.gold += gold;
        return goldLimit();
    }

     /**
     * Comprueba si el oro supera el límite de 500 y lo recorta si es necesario.
     *
     * @return Devuelve true si se superó el límite
     * @throws Exception con mensaje de aviso si el límite fue alcanzado
     */
    public boolean goldLimit() throws Exception{
        boolean isPastLimit = false;
        if (this.gold > 500) {
            this.gold = 500;
            isPastLimit = true;
            throw new Exception("Has alcanzado el límite de oro");
        }

        return isPastLimit;
    }

    /**
     * @return lista de items equipados.
     *         Índices: 0 = arma 1, 1 = arma 2, 2 = armadura, 3 = botas, 4 = casco.
     */
    public ArrayList<Item> getItemEquipped(){
        return this.ItemEquipped;
    }

    public void setCurrentCity(City city){
        this.currenCity = city;
    }
    public City getCurrenCity(){
        return this.currenCity;
    }

}

