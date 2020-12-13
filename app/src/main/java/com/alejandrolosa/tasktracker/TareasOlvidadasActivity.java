package com.alejandrolosa.tasktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.Color;
import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.modelos.ColorRGB;
import com.alejandrolosa.tasktracker.modelos.Fecha;
import com.alejandrolosa.tasktracker.modelos.Tarea;

import java.util.Calendar;

public class TareasOlvidadasActivity extends AppCompatActivity {
    // Atributos
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    private RecyclerView recyclerView;
    public Adaptador adaptador;
    private TextView sinTareas;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_olvidadas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            lanzarPreferencias(null);
            return true;
        }

        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }

        if (id == R.id.tareasCompletadas) {
            lanzarTareasCompletas(null);
            return true;
        }

        if (id == R.id.tareasPendientes) {
            lanzarTareasPendientes(null);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Recycler View
        adaptador = ((Aplicacion) getApplication()).adaptador;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();

        // Escuchador al seleccionar un elemento del RecyclerView
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                usoTarea.mostrar(pos);
            }
        });

        recyclerView.getRecycledViewPool().clear();
        adaptador.notifyDataSetChanged();
        generarTareas(null);

        sinTareas = findViewById(R.id.sinTareas);
        sinTareas.setText("No tienes tareas olvidadas.");
        if(tareas.tamanyo() > 0) {
            sinTareas.setVisibility(View.GONE);
        } else {
            sinTareas.setVisibility(View.VISIBLE);
        }
    }

    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }

    public void lanzarTareasCompletas(View view){
        Intent i = new Intent(this, TareasCompletasActivity.class);
        startActivity(i);
    }

    public void lanzarTareasPendientes(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void lanzarAddTarea(View view){
        usoTarea.crear();
    }

    public void generarTareas(View view){
        tareas.vaciar();
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase database = conn.getWritableDatabase();

        Calendar calendario = Calendar.getInstance();
        int mesHoy = calendario.get(Calendar.MONTH) + 1;
        int yearHoy = calendario.get(Calendar.YEAR);
        int diaHoy = calendario.get(Calendar.DATE);

        int fecha = Integer.parseInt(""+yearHoy+mesHoy+diaHoy);

        Cursor consulta = database.rawQuery("SELECT * FROM tareas WHERE estado = 0 AND orden < "+fecha+" ORDER BY orden ASC", null);

        if (consulta.moveToFirst()){
            for(int i = 0; i < consulta.getCount(); i++){
                ColorRGB colors = Color.getColorDadoString(consulta.getString(8));
                boolean aux;

                if(Integer.parseInt(consulta.getString(5)) == 1){
                    aux = true;
                } else {
                    aux = false;
                }

                tareas.anyade(new Tarea(consulta.getString(1),
                        new Fecha(Integer.parseInt(consulta.getString(2)),
                                Integer.parseInt(consulta.getString(3)),
                                Integer.parseInt(consulta.getString(4))),
                        aux,
                        consulta.getString(6),
                        colors,
                        Integer.parseInt(consulta.getString(0)),
                        false));

                consulta.moveToNext();
            }
        }
        recyclerView.getRecycledViewPool().clear();
        adaptador.notifyDataSetChanged();
    }
}