package com.alejandrolosa.tasktracker.casos_uso;

import android.app.Activity;
import android.content.Intent;

import com.alejandrolosa.tasktracker.CrearTareaActivity;
import com.alejandrolosa.tasktracker.EditarTareaActivity;
import com.alejandrolosa.tasktracker.VistaTareaActivity;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.modelos.Tarea;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CasosUsoTarea {
    private Activity actividad;
    private RepositorioTareas tareas;

    // Constructor
    public CasosUsoTarea(Activity actividad, RepositorioTareas tareas) { // Se necesita la actividad (el contexto) y el repositorio de tareas
        this.actividad = actividad;
        this.tareas = tareas;
    }

    // Métodos
    public void mostrar(int pos) {
        Intent i = new Intent(actividad, VistaTareaActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

    public void editar(int pos) { // Edita la tarea selecciona conociendo la posición en el RecyclerView (pos)
        Intent i = new Intent(actividad, EditarTareaActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

    public void crear(){
        Intent i = new Intent(actividad, CrearTareaActivity.class);
        actividad.startActivity(i);
    }
}