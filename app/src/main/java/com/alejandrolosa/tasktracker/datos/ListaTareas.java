package com.alejandrolosa.tasktracker.datos;

import com.alejandrolosa.tasktracker.modelos.Tarea;

import java.util.ArrayList;
import java.util.List;

public class ListaTareas implements RepositorioTareas {
    protected List<Tarea> listaTareas;

    public ListaTareas() {
        listaTareas = new ArrayList<Tarea>();
    }

    public Tarea elemento(int id) {
        return listaTareas.get(id);
    }


    public void anyade(Tarea tarea) {
        listaTareas.add(tarea);
    }

    public void borrar(int id) {
        listaTareas.remove(id);
    }

    public int tamanyo() {
        return listaTareas.size();
    }

    public void actualiza(int id, Tarea tarea) {
        listaTareas.set(id, tarea);
    }
}
