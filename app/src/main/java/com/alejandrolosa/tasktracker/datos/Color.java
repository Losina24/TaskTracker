package com.alejandrolosa.tasktracker.datos;

import com.alejandrolosa.tasktracker.modelos.ColorRGB;

public enum Color {
    ROJO (new ColorRGB(250, 101, 101)),
    AZUL (new ColorRGB(101, 126, 240)),
    CIAN (new ColorRGB(75, 235, 221)),
    AMARILLO (new ColorRGB(240, 223, 101)),
    NARANJA (new ColorRGB(235, 155, 75)),
    MORADO (new ColorRGB(158, 109, 214)),
    ROSA (new ColorRGB(255, 117, 221)),
    VERDE (new ColorRGB(106, 232, 102));

    private final ColorRGB color;

    Color(ColorRGB colorRGB) {
        this.color = colorRGB;
    }

    public String getColor(){
        return color.getHex();
    }
    public ColorRGB getRGB() { return color; }
}
