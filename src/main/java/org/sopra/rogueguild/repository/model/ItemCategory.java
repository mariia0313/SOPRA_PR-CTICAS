package org.sopra.rogueguild.repository.model;

import java.util.Random;

public enum ItemCategory {
    WEAPON(new String[]{"Espada", "Hacha", "Daga", "Lanza", "Mandoble", "Arco", "Maza", "Bastón"}),
    ARMOR(new String[]{"Armadura", "Cota", "Peto", "Coraza", "Malla"}),
    BOOTS(new String[]{"Botas", "Grebas", "Sandalias", "Escarpines"}),
    HELMET(new String[]{"Yelmo", "Casco", "Celada", "Capucha", "Visera"}),
    POTION(new String[]{"Poción", "Elixir", "Brewaje", "Ungüento", "Tintura"});

    private final String[] prefixes;
    private final Random random = new Random();

    ItemCategory(String[] prefixes) {
        this.prefixes = prefixes;
    }

    public String getRandomPrefix() {
        return prefixes[random.nextInt(prefixes.length)];
    }
}