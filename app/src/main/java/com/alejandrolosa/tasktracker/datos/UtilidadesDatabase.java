package com.alejandrolosa.tasktracker.datos;

public class UtilidadesDatabase { // Esta clase almacena constantes que se reutilizaran para hacer consultas a la bbdd

    // Constantes CAMPOS //
    public static final String TABLA_TAREAS = "tareas";
    public static final String TAREA_ID = "id";
    public static final String TAREA_TITULO = "titulo";
    public static final String TAREA_DIA = "dia";
    public static final String TAREA_MES = "mes";
    public static final String TAREA_YEAR = "year";
    public static final String TAREA_IMPORTANCIA = "importante";
    public static final String TAREA_TIPO = "tipo";
    public static final String TAREA_ESTADO = "estado";
    public static final String TAREA_COLOR = "color";
    public static final String TAREA_ORDEN = "orden";

    // Esta constante almacena la consulta para crear la BBDD
    public static final String CREAR_TABLA_TAREAS ="CREATE TABLE tareas(id INTEGER PRIMARY KEY, titulo text, " +
            "dia int, mes int, year int, importante int, tipo text, estado int, color text, orden int)";
}
