package com.alejandrolosa.tasktracker.datos;

import com.alejandrolosa.tasktracker.modelos.Fecha;
import com.alejandrolosa.tasktracker.modelos.Tarea;
import com.alejandrolosa.tasktracker.datos.Color;

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

    public void vaciar(){
        listaTareas.clear();
    }

    public void anyadeEjemplos() {
        anyade(new Tarea("Mi Tarea 1",  new Fecha(22, 11, 2020), false,"Trabajo", Color.ROJO.getRGB(), 0));
        anyade(new Tarea("Mi Tarea 2",  new Fecha(23, 11, 2020), false,"Trabajo", Color.AZUL.getRGB(), 1));
        anyade(new Tarea("Mi Tarea 3",  new Fecha(24, 11, 2020), false,"Trabajo", Color.VERDE.getRGB(), 2));
        anyade(new Tarea("Mi Tarea 4",  new Fecha(25, 11, 2020), false,"Trabajo", Color.MORADO.getRGB(), 3));
        anyade(new Tarea("Mi Tarea 5",  new Fecha(26, 11, 2020), false,"Trabajo", Color.CIAN.getRGB(), 4));
        anyade(new Tarea("Mi Tarea 6",  new Fecha(26, 11, 2020), false,"Trabajo", Color.CIAN.getRGB(), 5));
    }
}
