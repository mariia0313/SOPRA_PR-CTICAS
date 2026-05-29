package org.sopra.rogueguild.view.utils;

/**
 * Utilidad para aplicar colores ANSI a texto de consola.
 *
 * Proporciona constantes de color y el método {@link #c(String, String)}
 * para envolver texto con códigos de escape. El coloreado puede desactivarse
 * globalmente mediante {@link #enabled}.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public final class Ansi {
    public static boolean enabled = true;

    public static final String R     = "\u001B[0m";
    public static final String GRAY  = "\u001B[90m";
    public static final String RED   = "\u001B[31m";
    public static final String PURP  = "\u001B[35m";

    public static String c(String color, String text) {
        if (!enabled) return text;
        return color + text + R;
    }

    private Ansi() {}
}

