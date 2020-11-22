package com.alejandrolosa.tasktracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.modelos.Tarea;

import java.text.BreakIterator;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    protected RepositorioTareas tareas; // Lista de lugares a mostrar
    public Adaptador(RepositorioTareas tareas) {
        this.tareas = tareas;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, tipo, fecha;
        public ImageView importancia;
        public CheckBox checkbox;
        public ConstraintLayout color;


        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.nombreTarea);
            tipo = itemView.findViewById(R.id.tag);
            fecha = itemView.findViewById(R.id.fecha);
            importancia = itemView.findViewById(R.id.iconoAlerta);
            checkbox = itemView.findViewById(R.id.checkbox_tarea);
            color = itemView.findViewById(R.id.colorVer);
        }

        public void personaliza(Tarea tarea){
            titulo.setText(tarea.getTitulo());
            tipo.setText(tarea.getTipo());
            fecha.setText(tarea.getFecha().toString());
            color.setBackgroundColor(Color.rgb(tarea.getColor().getRed(), tarea.getColor().getGreen(), tarea.getColor().getBlue()));

            if(tarea.isImportante() == true){
                importancia.setVisibility(View.VISIBLE);
            } else {
                importancia.setVisibility(View.GONE);
            }

            if (tarea.isStatus() == true) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listelement, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Tarea tarea = tareas.elemento(posicion);
        holder.personaliza(tarea);
    }

    @Override public int getItemCount() {
        return tareas.tamanyo();
    }
}