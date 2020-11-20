package com.alejandrolosa.tasktracker.modelos;

public class Tarea {
    // Atributos
    private int id;
    private String titulo;
    private String mensaje;
    private Fecha fecha;
    private boolean importante;
    private TipoTarea tipo;

    // Métodos
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
}
