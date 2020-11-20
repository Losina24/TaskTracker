package com.alejandrolosa.tasktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alejandrolosa.tasktracker.R;
import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;

public class MainActivity extends AppCompatActivity {
    // Atributos
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    //private RecyclerView recyclerView;
    //public AdaptadorTareas adaptador;

    // Métodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Asignar vista
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creamos el caso de uso y le pasamos el contexto y el repositorio
        tareas = ((Aplicacion) getApplication()).tareas;
        usoTarea = new CasosUsoTarea(this, tareas);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Recycled View
        //adaptador = ((Aplicacion) getApplication()).adaptador;
        //recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
    }
}