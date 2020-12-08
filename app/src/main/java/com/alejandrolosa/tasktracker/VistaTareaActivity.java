package com.alejandrolosa.tasktracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.datos.Tipo;
import com.alejandrolosa.tasktracker.datos.UtilidadesDatabase;
import com.alejandrolosa.tasktracker.modelos.ColorRGB;
import com.alejandrolosa.tasktracker.modelos.Fecha;
import com.alejandrolosa.tasktracker.modelos.Tarea;

import java.util.ArrayList;

public class VistaTareaActivity extends AppCompatActivity {
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    private int pos;
    private Tarea tarea;
    private int idTarea;

    private ImageButton botonEliminar;
    private ImageButton botonEditar;
    private ImageButton botonAceptar;
    private ImageButton botonCancelar;
    private ImageButton botonTag;
    private ImageButton botonColor;
    private ImageButton botonAlerta;
    private ImageButton botonFecha;

    private Spinner spinnerTag;
    private Spinner spinnerColor;
    private Spinner spinnerImportancia;
    private ConstraintLayout contenedorFecha;
    private ConstraintLayout constraintLayoutCrear;
    private Button aceptarFecha;
    private DatePicker calendario;
    private CheckBox checkboxTarea;

    // Datos para editar la tarea //
    private int diaCalendario;
    private int mesCalendario;
    private int yearCalendario;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_tarea);

        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", 0);
        tareas = ((Aplicacion) getApplication()).tareas;
        usoTarea = new CasosUsoTarea(this, tareas);
        tarea = tareas.elemento(pos);
        idTarea = tarea.getId();

        botonAceptar = findViewById(R.id.botonAceptar);
        botonCancelar = findViewById(R.id.botonCancelar);
        botonTag = findViewById(R.id.tagButton);
        botonColor = findViewById(R.id.colorButton);
        botonAlerta = findViewById(R.id.importantButton);
        botonFecha = findViewById(R.id.timeButton);
        checkboxTarea = findViewById(R.id.checkboxTareaEditar);

        spinnerTag = findViewById(R.id.spinnerTipo);
        spinnerColor = findViewById(R.id.spinnerColour);
        spinnerImportancia = findViewById(R.id.spinnerImportant);
        contenedorFecha = findViewById(R.id.containerDate);
        constraintLayoutCrear = findViewById(R.id.ventanaVer);
        calendario = findViewById(R.id.fechaPicker);
        aceptarFecha = findViewById(R.id.botonAceptarDate);

        // Personalizar la vista //
        actualizaVistas();

        // Listeners //
        botonEliminar = findViewById(R.id.removeButton);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEliminar(null);
            }
        });

        botonEditar = findViewById(R.id.editButton);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTarea(null);
            }
        });

        botonAceptar = findViewById(R.id.botonAceptar);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptarEditar(null);
            }
        });

        botonCancelar = findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarEditar(null);
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

        botonAlerta.setOnClickListener(new View.OnClickListener() {
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

        checkboxTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarCheck();
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

        constraintLayoutCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenedorFecha.setVisibility(View.GONE);
            }
        });

        aceptarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarFecha();
            }
        });

        // Configurar spinners //
        // Tipo
        ArrayAdapter<String> adaptadorTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tipo.getTipos());
        adaptadorTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTag.setAdapter(adaptadorTipo);

        // Color
        ArrayAdapter<String> adaptadorColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, com.alejandrolosa.tasktracker.datos.Color.getColores());
        adaptadorColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adaptadorColor);

        // Importancia
        ArrayAdapter<String> adaptadorImportancia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adaptadorImportancia.add("Urgente");
        adaptadorImportancia.add("No urgente");
        adaptadorImportancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImportancia.setAdapter(adaptadorImportancia);

        // Asigno a cada spinner su valor actual de la tarea en su valor por defecto //
        establecerValoresDefault(); // Si el usuario no selecciona ningún valor, el spinner utiliza el valor actual de la tarea

        // Listeners seleccionar elementos de cada Spinner //
        spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarTipo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarColor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        spinnerImportancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarImportancia();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    public void actualizaVistas() {
        TextView titulo = findViewById(R.id.nombreTareaEditar);
        titulo.setText(tarea.getTitulo());

        TextView tipo = findViewById(R.id.verTag);
        tipo.setText(tarea.getTipo());

        TextView fecha = findViewById(R.id.fechaEditar);
        fecha.setText(tarea.getFecha().toStringCompacto());

        ImageView importance = findViewById(R.id.iconoAlertaVer);
        if(tarea.isImportante() == true) {
            importance.setVisibility(View.VISIBLE);
        } else {
            importance.setVisibility(View.GONE);
        }

        if(tarea.isStatus() == true ){
            checkboxTarea.setChecked(true);
        }

        ConstraintLayout color = findViewById(R.id.colorVer);
        color.setBackgroundColor(Color.rgb(tarea.getColor().getRed(), tarea.getColor().getGreen(), tarea.getColor().getBlue()));

        ImageView imagen = findViewById(R.id.foto);
        imagen.setVisibility(View.GONE);
    }

    public void alertaEliminar(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Quieres eliminar la tarea?");
        builder.setMessage("Una vez eliminada no podrás volver a recuperarla. Selecciona Aceptar solo si estás seguro/a.");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarTarea();
                actualizaVistas();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void eliminarTarea() {
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String consulta = "DELETE FROM "+UtilidadesDatabase.TABLA_TAREAS+" WHERE "+UtilidadesDatabase.TAREA_ID+" = "+idTarea;

        db.execSQL(consulta);
        db.close();

        finish();

        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }

    public void editarTarea(View v) {
        TextView tv = findViewById(R.id.nombreTareaEditar);
        EditText et = findViewById(R.id.editTarea);

        tv.setVisibility(View.GONE);
        et.setVisibility(View.VISIBLE);
        botonEditar.setVisibility(View.GONE);
        botonEliminar.setVisibility(View.GONE);
        botonAceptar.setVisibility(View.VISIBLE);
        botonCancelar.setVisibility(View.VISIBLE);
        botonTag.setVisibility(View.GONE);
        botonColor.setVisibility(View.GONE);
        botonAlerta.setVisibility(View.GONE);
        botonFecha.setVisibility(View.GONE);

        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    public void aceptarEditar(View v) {
        EditText et = findViewById(R.id.editTarea);
        String s = et.getText().toString();

        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        if(s.matches("")){
            Toast.makeText(this, "Por favor, introduce un título.", Toast.LENGTH_SHORT).show();
        } else {
            db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_TITULO+"' = '"+s+"' WHERE "
                    +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
            db.close();

            // Refresco los estilos de la tarea //
            cambiarEstaticamente();

            Toast.makeText(this, "Título actualizado", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelarEditar(View v) {
        TextView tv = findViewById(R.id.nombreTareaEditar);
        EditText et = findViewById(R.id.editTarea);

        tv.setVisibility(View.VISIBLE);
        et.setVisibility(View.GONE);
        botonEditar.setVisibility(View.VISIBLE);
        botonEliminar.setVisibility(View.VISIBLE);
        botonAceptar.setVisibility(View.GONE);
        botonCancelar.setVisibility(View.GONE);
        botonTag.setVisibility(View.VISIBLE);
        botonColor.setVisibility(View.VISIBLE);
        botonAlerta.setVisibility(View.VISIBLE);
        botonFecha.setVisibility(View.VISIBLE);
    }

    public void cambiarEstaticamente() {
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase database = conn.getWritableDatabase();

        Cursor consulta = database.rawQuery("SELECT * FROM tareas WHERE id = "+tarea.getId(), null);

        if (consulta.moveToFirst()) {
            cancelarEditar(null);

            Fecha fechaString = new Fecha(Integer.parseInt(consulta.getString(2)),
                    Integer.parseInt(consulta.getString(3)),
                    Integer.parseInt(consulta.getString(4)));

            ColorRGB colors = com.alejandrolosa.tasktracker.datos.Color.getColorDadoString(consulta.getString(8));

            TextView titulo = findViewById(R.id.nombreTareaEditar);
            titulo.setText(consulta.getString(1));

            TextView tipo = findViewById(R.id.verTag);
            tipo.setText(consulta.getString(6));

            TextView fecha = findViewById(R.id.fechaEditar);
            fecha.setText(fechaString.toStringCompacto());

            ImageView importance = findViewById(R.id.iconoAlertaVer);
            if(Integer.parseInt(consulta.getString(5)) == 1) {
                importance.setVisibility(View.VISIBLE);
            } else {
                importance.setVisibility(View.GONE);
            }

            CheckBox status = findViewById(R.id.checkboxTareaEditar);
            status.setChecked(Boolean.parseBoolean(consulta.getString(7)));

            ConstraintLayout color = findViewById(R.id.colorVer);
            color.setBackgroundColor(Color.rgb(colors.getRed(), colors.getGreen(), colors.getBlue()));
        }
    }

    public void establecerValoresDefault() {
        ColorRGB colorActual = tarea.getColor();
        Fecha fechaActual = tarea.getFecha();
        boolean importanciaAcual = tarea.isImportante();
        String tipoActual = tarea.getTipo();
        boolean checked = tarea.isStatus();

        // Tipos //
        String[] tipos = Tipo.getTipos();
        for (int i = 0; i < tipos.length; i++){
            if(tipos[i].matches(tipoActual)) {
                spinnerTag.setSelection(i);
                break;
            }
        }

        // Color //
        String colorin = com.alejandrolosa.tasktracker.datos.Color.getNombreDadoColor(colorActual);
        String[] colores = com.alejandrolosa.tasktracker.datos.Color.getColores();
        for (int i = 0; i < colores.length; i++){
            if(colores[i].matches(colorin)) {
                spinnerColor.setSelection(i);
                break;
            }
        }

        // Importancia //
        if(importanciaAcual == true) {
            spinnerImportancia.setSelection(0);
        } else {
            spinnerImportancia.setSelection(1);
        }

        // Estado //
        CheckBox status = findViewById(R.id.checkboxTareaEditar);
        if(checked == true) {
            status.setChecked(true);
        } else {
            status.setChecked(false);
        }
    }

    // Actualizar características de la tarea (Tipo, color, importancia y fecha) //
    public void actualizarTipo() {
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String tag = spinnerTag.getSelectedItem().toString();
        db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_TIPO+"' = '"+tag+"' WHERE "
                +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        db.close();

        // Refresco los estilos de la tarea //
        cambiarEstaticamente();
    }

    public void actualizarColor() {
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String color = spinnerColor.getSelectedItem().toString();
        db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_COLOR+"' = '"+color+"' WHERE "
                +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        db.close();

        // Refresco los estilos de la tarea //
        cambiarEstaticamente();
    }

    public void actualizarImportancia() {
        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String imp = spinnerImportancia.getSelectedItem().toString();
        int urgente;

        if(imp == "Urgente"){
            urgente = 1;
        } else {
            urgente = 0;
        }

        db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_IMPORTANCIA+"' = '"+urgente+"' WHERE "
                +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        db.close();

        // Refresco los estilos de la tarea //
        cambiarEstaticamente();
    }

    public void actualizarFecha() {
        String ordenMes = "";
        String ordenDia = "";
        int orden = 0;

        diaCalendario = calendario.getDayOfMonth();
        mesCalendario = calendario.getMonth() + 1;
        yearCalendario = calendario.getYear();

        if(mesCalendario < 10){
            ordenMes = "0"+mesCalendario;
        } else {
            ordenMes = ""+mesCalendario;
        }

        if(diaCalendario < 10){
            ordenDia = "0"+diaCalendario;
        } else {
            ordenDia = ""+diaCalendario;
        }

        orden = Integer.parseInt(""+yearCalendario+ordenMes+ordenDia);

        contenedorFecha.setVisibility(View.GONE);

        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_DIA+"' = "+diaCalendario+", '"
                +UtilidadesDatabase.TAREA_MES +"' = "+mesCalendario+", '"+UtilidadesDatabase.TAREA_YEAR+"' = "+yearCalendario+", " +
                "'"+UtilidadesDatabase.TAREA_ORDEN+"' =  "+orden+" WHERE " +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        db.close();

        // Refresco los estilos de la tarea //
        cambiarEstaticamente();
    }

    public void editarCheck() {
        boolean checkedl = checkboxTarea.isChecked();

        DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        if(checkedl == true) {
            db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_ESTADO+"' = 1 WHERE "
                    +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        } else {
            db.execSQL("UPDATE "+UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_ESTADO+"' = 0 WHERE "
                    +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
        }
        db.close();
    }
}