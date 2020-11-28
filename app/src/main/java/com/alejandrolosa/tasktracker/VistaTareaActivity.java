package com.alejandrolosa.tasktracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alejandrolosa.tasktracker.casos_uso.CasosUsoTarea;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.modelos.Tarea;

public class VistaTareaActivity extends AppCompatActivity {
    private RepositorioTareas tareas;
    private CasosUsoTarea usoTarea;
    private int pos;
    private Tarea tarea;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_tarea);

        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", 0);
        tareas = ((Aplicacion) getApplication()).tareas;
        usoTarea = new CasosUsoTarea(this, tareas);
        tarea = tareas.elemento(pos);


        actualizaVistas();
    }

    public void actualizaVistas() {
        TextView titulo = findViewById(R.id.nombreTareaEditar);
        titulo.setText(tarea.getTitulo());

        TextView tipo = findViewById(R.id.verTag);
        tipo.setText(tarea.getTipo());

        TextView fecha = findViewById(R.id.fechaEditar);
        fecha.setText(tarea.getFecha().toString());

        CheckBox status = findViewById(R.id.checkboxTareaEditar);
        status.setChecked(tarea.isStatus());

        ConstraintLayout color = findViewById(R.id.colorVer);
        color.setBackgroundColor(Color.rgb(tarea.getColor().getRed(), tarea.getColor().getGreen(), tarea.getColor().getBlue()));

        ImageView imagen = findViewById(R.id.foto);
        imagen.setVisibility(View.GONE);
    }
}