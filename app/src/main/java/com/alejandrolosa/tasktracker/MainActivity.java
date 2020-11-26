package com.alejandrolosa.tasktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;

public class MainActivity extends AppCompatActivity {
    // Atributos
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    private RecyclerView recyclerView;
    public Adaptador adaptador;

    // Métodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Asignar vista
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creamos el caso de uso y le pasamos el contexto y el repositorio
        tareas = ((Aplicacion) getApplication()).tareas;
        usoTarea = new CasosUsoTarea(this, tareas);

        // Recycler View
        adaptador = ((Aplicacion) getApplication()).adaptador;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        // Escuchador al seleccionar un elemento del RecyclerView
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                usoTarea.mostrar(pos);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //mostrarConfigurar(null);
            return true;
        }

        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }

        /*if (id == R.id.menu_buscar) {
            lanzarBuscar(null);
            return true;
        }*/

        /*if (id == R.id.action_preferences) {
            //mostrarPreferencias(null);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

}