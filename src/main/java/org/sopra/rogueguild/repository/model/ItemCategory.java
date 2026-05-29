package org.sopra.rogueguild.repository.model;

import java.util.Random;

/**
 * Enumeración de las categorías de items disponibles en el juego.
 *
 * Cada categoría lleva asociado un conjunto de prefijos aleatorios
 * utilizados por ItemGenerator para generar nombres de items.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public enum ItemCategory {
    WEAPON(new String[]{"Espada", "Hacha", "Daga", "Lanza", "Mandoble", "Arco", "Maza", "Bastón"}),
    ARMOR(new String[]{"Armadura", "Cota", "Peto", "Coraza", "Malla"}),
    BOOTS(new String[]{"Botas", "Grebas", "Sandalias", "Escarpines"}),
    HELMET(new String[]{"Yelmo", "Casco", "Celada", "Capucha", "Visera"}),
    POTION(new String[]{"Poción", "Elixir", "Brewaje", "Ungüento", "Tintura"}),
    OTHERS(new String[]{"Amuleto", "Anillo", "Colgante", "Reliquia", "Artefacto", "Pergamino", "Llave", "Libro"});

    private final String[] prefixes;
    private final Random random = new Random();

    ItemCategory(String[] prefixes) {
        this.prefixes = prefixes;
    }

    public String getRandomPrefix() {
        return prefixes[random.nextInt(prefixes.length)];
    }
}