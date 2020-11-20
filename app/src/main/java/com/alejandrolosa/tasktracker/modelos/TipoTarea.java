package com.alejandrolosa.tasktracker.modelos;

import android.widget.ImageView;

public class TipoTarea {
    // Atributos
    private String nombre;
    private ColorRGB color;
    private ImageView simbolo;

    // Métodos
    public TipoTarea(){
        this.nombre = "General";
        this.color = new ColorRGB(165, 165, 165);
    }

    public TipoTarea(String nombre, ColorRGB color){
        this.nombre = nombre;
        this.color = color;
    }

    public TipoTarea(String nombre, ColorRGB color, ImageView icono){
        this.nombre = nombre;
        this.color = color;
        this.simbolo = icono;
    }
}
