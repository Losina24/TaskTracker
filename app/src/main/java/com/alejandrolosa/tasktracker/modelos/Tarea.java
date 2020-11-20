package com.alejandrolosa.tasktracker.modelos;

public class Tarea {
    // Atributos
    private int id;
    private String titulo;
    private String mensaje;
    private Fecha fecha;
    private boolean importante;
    private TipoTarea tipo;

    // Constructores
    public Tarea(String titulo, String mensaje, Fecha fecha, boolean importancia, TipoTarea tipo) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.importante = importancia;
        this.tipo = tipo;
    }

    public Tarea(String titulo, Fecha fecha, boolean importancia, TipoTarea tipo){
        this.titulo = titulo;
        this.fecha = fecha;
        this.importante = importancia;
        this.tipo = tipo;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public TipoTarea getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarea tipo) {
        this.tipo = tipo;
    }
}
