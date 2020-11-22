package com.alejandrolosa.tasktracker;

import android.app.Application;
import android.content.SharedPreferences;

import com.alejandrolosa.tasktracker.datos.ListaTareas;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;

// Esta clase contiene información de toda la aplicación y métodos para acceder a la info
public class Aplicacion extends Application {
    public RepositorioTareas tareas = new ListaTareas();
    public Adaptador adaptador = new Adaptador(tareas);

    @Override
    public void onCreate() {
        super.onCreate();
    }
}