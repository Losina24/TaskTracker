package com.alejandrolosa.tasktracker.datos;

import com.alejandrolosa.tasktracker.modelos.ColorRGB;

public enum Color {
    ROJO (new ColorRGB(250, 101, 101), "Rojo"),
    AZUL (new ColorRGB(101, 126, 240), "Azul"),
    CIAN (new ColorRGB(75, 235, 221), "Cian"),
    AMARILLO (new ColorRGB(240, 223, 101), "Amarillo"),
    NARANJA (new ColorRGB(235, 155, 75), "Naranja"),
    MORADO (new ColorRGB(158, 109, 214), "Morado"),
    ROSA (new ColorRGB(255, 117, 221), "Rosa"),
    VERDE (new ColorRGB(106, 232, 102), "Verde");

    private final ColorRGB color;
    private final String nombre;

    Color(ColorRGB colorRGB,  String nombre) {
        this.color = colorRGB;
        this.nombre = nombre;
    }

    public static ColorRGB getColorDadoString(String texto){
        Color resultado = null;
        for (Color color : Color.values()) {
            if(color.nombre == texto){
                resultado = color;
            }
        }
        return resultado.getRGB();
    }

    public String getColor(){
        return color.getHex();
    }

    public ColorRGB getRGB() {
        return color;
    }

    public static String[] getColores() {
        String[] resultado = new String[Color.values().length];
        for (Color color : Color.values()) {
            resultado[color.ordinal()] = color.nombre;
        }
        return resultado;
    }
}
