package com.alejandrolosa.tasktracker.modelos;

import com.alejandrolosa.tasktracker.datos.Color;

public class Tarea {
    // Atributos
    private int id;
    private String titulo;
    private Fecha fecha;
    private boolean importante;
    private String tipo;
    private boolean status;
    private ColorRGB color;

    // Constructores
    public Tarea(String titulo, Fecha fecha, boolean importancia, String tipo, ColorRGB color, int id) { // Constructor completo
        this.titulo = titulo;
        this.fecha = fecha;
        this.importante = importancia;
        this.tipo = tipo;
        this.color = color;
        this.status = false;
        this.id = id;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public ColorRGB getColor() { return color; }

    public void setColor(ColorRGB color) { this.color = color; }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
