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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.datos.UtilidadesDatabase;
import com.alejandrolosa.tasktracker.modelos.ColorRGB;
import com.alejandrolosa.tasktracker.modelos.Fecha;
import com.alejandrolosa.tasktracker.modelos.Tarea;

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

        // Personalizar la vista //
        actualizaVistas();

        // Listeners //
        botonEliminar = findViewById(R.id.removeButton);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEliminar(null);
                //eliminarTarea();
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
    }

    public void actualizaVistas() {
        TextView titulo = findViewById(R.id.nombreTareaEditar);
        titulo.setText(tarea.getTitulo());

        TextView tipo = findViewById(R.id.verTag);
        tipo.setText(tarea.getTipo());

        TextView fecha = findViewById(R.id.fechaEditar);
        fecha.setText(tarea.getFecha().toStringCompacto());

        CheckBox status = findViewById(R.id.checkboxTareaEditar);
        status.setChecked(tarea.isStatus());

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
        //String consulta = "DELETE FROM tareas WHERE id = "+idTarea;

        db.execSQL(consulta);
        db.close();

        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

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

            //finish();
            //startActivity(getIntent());

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

            CheckBox status = findViewById(R.id.checkboxTareaEditar);
            status.setChecked(Boolean.parseBoolean(consulta.getString(7)));

            ConstraintLayout color = findViewById(R.id.colorVer);
            color.setBackgroundColor(Color.rgb(colors.getRed(), colors.getGreen(), colors.getBlue()));
        }
    }
}