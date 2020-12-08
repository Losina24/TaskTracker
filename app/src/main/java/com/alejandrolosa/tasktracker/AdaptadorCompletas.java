package com.alejandrolosa.tasktracker;

import com.alejandrolosa.tasktracker.datos.RepositorioTareas;

public class AdaptadorCompletas extends Adaptador{
    protected RepositorioTareas tareas;
    public AdaptadorCompletas(RepositorioTareas tareas) {
        super(tareas);
        this.tareas = tareas;
    }
}
