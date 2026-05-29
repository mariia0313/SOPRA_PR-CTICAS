package org.sopra.rogueguild.repository;
import java.util.LinkedHashMap;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemGenerator;

/**
 * Repositorio que gestiona el stock de items disponibles en la tienda.
 *
 * Almacena los items en un map por ID y proporciona operaciones
 * de consulta, eliminación y recarga del inventario inicial.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class ShopRepository {
    private Map<Integer, Item> stock;

    public ShopRepository() {
        stock = new LinkedHashMap<>();
        loadInitialStock();
    }

    public void loadInitialStock() {
        // stock.put(1, new Weapon("Daga de las Sombras", 150, 10));
        // stock.put(2, new Weapon("Espada del Renegado", 350, 15));
        // stock.put(3, new Armor("Armadura del Sol Naciente", 200, 5));

        ItemGenerator itemGenerator = new ItemGenerator();

        for (int i = 1; i <= 5; i++){
            stock.put(i, itemGenerator.createRandomItem());
        }
    }

    public Item getItem(int id) {
        return stock.get(id);
    }

    public void removeItem(int id) { stock.remove(id); }

    public Map<Integer, Item> getAllStock() {
        return stock;
    }
}