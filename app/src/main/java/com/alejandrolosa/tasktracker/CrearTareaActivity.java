package com.alejandrolosa.tasktracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.Color;
import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.datos.Tipo;
import com.alejandrolosa.tasktracker.datos.UtilidadesDatabase;
import com.alejandrolosa.tasktracker.modelos.Fecha;

import java.util.Calendar;

public class CrearTareaActivity extends AppCompatActivity {
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    private ImageButton botonTag;
    private ImageButton botonColor;
    private ImageButton botonFecha;
    private ImageButton botonImportancia;
    private Spinner spinnerTag;
    private Spinner spinnerColor;
    private Spinner spinnerImportancia;
    private ConstraintLayout contenedorFecha;
    private ConstraintLayout constraintLayoutCrear;
    private Button enviarFormulario;
    private Button aceptarFecha;
    private DatePicker calendario;

    // Datos para crear la tarea //
    private EditText campoTitulo;
    private int diaCalendario;
    private int mesCalendario;
    private int yearCalendario;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_tarea);

        Bundle extras = getIntent().getExtras();
        tareas = ((Aplicacion) getApplication()).tareas;
        usoTarea = new CasosUsoTarea(this, tareas);

        // Enlazar variables a elementos del layout //
        enviarFormulario = findViewById(R.id.botonEnviar);
        aceptarFecha = findViewById(R.id.botonAceptarFecha);
        botonTag = findViewById(R.id.botonTag);
        botonColor = findViewById(R.id.botonColor);
        botonFecha = findViewById(R.id.botonFecha);
        botonImportancia = findViewById(R.id.botonImportancia);
        spinnerTag = findViewById(R.id.spinnerTag);
        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerImportancia = findViewById(R.id.spinnerImportancia);
        contenedorFecha = findViewById(R.id.containerFecha);
        constraintLayoutCrear = findViewById(R.id.constraintLayoutCrear);
        calendario = findViewById(R.id.datePicker);
        campoTitulo = findViewById(R.id.inputTitulo);

        // Escuchadores CLICK //
        constraintLayoutCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenedorFecha.setVisibility(View.GONE);
            }
        });

        enviarFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearTarea();
            }
        });

        botonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerTag.performClick();
            }
        });

        botonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerColor.performClick();
            }
        });

        botonImportancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerImportancia.performClick();
            }
        });

        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenedorFecha.setVisibility(View.VISIBLE);
            }
        });

        aceptarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaCalendario = calendario.getDayOfMonth();
                mesCalendario = calendario.getMonth() + 1;
                yearCalendario = calendario.getYear();

                contenedorFecha.setVisibility(View.GONE);

                String toasty = diaCalendario+"/"+mesCalendario+"/"+yearCalendario;
                Toast toast1 = Toast.makeText(getApplicationContext(), toasty, Toast.LENGTH_SHORT);
                toast1.show();
            }
        });

        // Configurar spinners //
        // Tipo
        ArrayAdapter<String> adaptadorTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tipo.getTipos());
        adaptadorTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTag.setAdapter(adaptadorTipo);

        // Color
        ArrayAdapter<String> adaptadorColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Color.getColores());
        adaptadorColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adaptadorColor);

        // Importancia
        ArrayAdapter<String> adaptadorImportancia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adaptadorImportancia.add("Urgente");
        adaptadorImportancia.add("No urgente");
        adaptadorImportancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImportancia.setAdapter(adaptadorImportancia);
    }

    private void crearTarea(){
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String titulo = campoTitulo.getText().toString();
        String color = spinnerColor.getSelectedItem().toString();
        String importancia = spinnerImportancia.getSelectedItem().toString();
        String tag = spinnerTag.getSelectedItem().toString();;
        int urgente;

        if(importancia == "Urgente"){
            urgente = 1;
        } else {
            urgente = 0;
        }

        if(diaCalendario == 0 && mesCalendario == 0 && yearCalendario == 0){
            Calendar calendario = Calendar.getInstance();
            diaCalendario = calendario.get(Calendar.DATE);
            mesCalendario = calendario.get(Calendar.MONTH) + 1;
            yearCalendario = calendario.get(Calendar.YEAR);
        }

        if(color == ""){
            color = "Rojo";
        }


        if(!titulo.isEmpty()){
            String query = "INSERT INTO "+UtilidadesDatabase.TABLA_TAREAS+"('"+UtilidadesDatabase.TAREA_TITULO+"', '"+UtilidadesDatabase.TAREA_TIPO+
                    "', '"+UtilidadesDatabase.TAREA_COLOR+"', '"+UtilidadesDatabase.TAREA_IMPORTANCIA+
                    "', '"+UtilidadesDatabase.TAREA_DIA+"', '"+UtilidadesDatabase.TAREA_MES+
                    "', '"+UtilidadesDatabase.TAREA_YEAR+"', '"+UtilidadesDatabase.TAREA_ESTADO+"') VALUES ('"+titulo+"', '"+
                    tag+"', '"+color+"', '" +
                    urgente+"', '"+diaCalendario+"', '"+mesCalendario+"', '" +
                    yearCalendario+"', '"+0+"')";

            db.execSQL(query);
            db.close();

            finish();

            Toast.makeText(this, "Tarea creada :)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay texto en la tarea", Toast.LENGTH_SHORT).show();
        }
    }
}
