package com.alejandrolosa.tasktracker.modelos;

public class ColorRGB {
    private int red, green, blue;
    private String hex;

    public ColorRGB(int r, int g, int b){
        this.red = r;
        this.green = g;
        this.blue = b;

        hex = "#";
        hex.concat(Integer.toHexString(r)).concat(Integer.toHexString(g)).concat(Integer.toHexString(b));
        this.hex = hex;
    }

    public int getRed() {
        return red;
    }
    public int getGreen() {
        return green;
    }
    public int getBlue() {
        return blue;
    }
    public String getHex() {
        return hex;
    }

    @Override
    public String toString() {
        return this.red + ", " + this.green + ", " + this.blue;
    }
}
