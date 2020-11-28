package com.alejandrolosa.tasktracker.datos;

import com.alejandrolosa.tasktracker.modelos.Tarea;

public interface RepositorioTareas {
    Tarea elemento(int id); // Devuelve una tarea según su id
    void anyade(Tarea tarea); // Añade una tarea
    void borrar(int id); // Borra una tarea según el id
    int tamanyo(); // Devuelve el número de tareas
    void vaciar();
    void actualiza(int id, Tarea tarea); // Reemplaza un elemento

    void anyadeEjemplos();
}