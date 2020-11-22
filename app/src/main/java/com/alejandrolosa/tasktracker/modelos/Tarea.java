package com.alejandrolosa.tasktracker.modelos;

public class Tarea {
    // Atributos
    private int id;
    private String titulo;
    private String descripcion;
    private Fecha fecha;
    private boolean importante;
    private String tipo;
    private boolean status;
    private ColorRGB color;

    // Constructores
    public Tarea(String titulo, String descripcion, Fecha fecha, boolean importancia, String tipo, ColorRGB color) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.importante = importancia;
        this.tipo = tipo;
        this.color = color;
        this.status = false;
    }

    public Tarea(String titulo, Fecha fecha, boolean importancia, String tipo, ColorRGB color){
        this.titulo = titulo;
        this.fecha = fecha;
        this.importante = importancia;
        this.tipo = tipo;
        this.color = color;
        this.status = false;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
