package com.alejandrolosa.tasktracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;
import androidx.transition.Transition;
import androidx.transition.Fade;
import android.view.animation.Animation;

import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;
import com.alejandrolosa.tasktracker.datos.RepositorioTareas;
import com.alejandrolosa.tasktracker.datos.UtilidadesDatabase;
import com.alejandrolosa.tasktracker.modelos.Tarea;
import com.google.android.material.animation.AnimationUtils;

import java.text.BreakIterator;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    protected RepositorioTareas tareas; // Lista de lugares a mostrar
    public Adaptador(RepositorioTareas tareas) {
        this.tareas = tareas;
    }
    protected View.OnClickListener onClickListener;

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, tipo, fecha;
        public ImageView importancia;
        public CheckBox checkbox;
        public ConstraintLayout color, elementoLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.nombreTarea);
            tipo = itemView.findViewById(R.id.tag);
            fecha = itemView.findViewById(R.id.fecha);
            importancia = itemView.findViewById(R.id.iconoAlerta);
            checkbox = itemView.findViewById(R.id.checkbox_tarea);
            color = itemView.findViewById(R.id.colorVer);
            elementoLayout = itemView.findViewById(R.id.layout_elemento);
        }

        public void personaliza(Tarea tarea, int pos){
            titulo.setText(tarea.getTitulo());
            tipo.setText(tarea.getTipo());
            fecha.setText(tarea.getFecha().toStringCompacto());
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

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listelement, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Tarea tarea = tareas.elemento(posicion);
        holder.personaliza(tarea, posicion);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseSQLite conn = new DatabaseSQLite(buttonView.getContext(), "bd_tareas", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();

                CheckBox checkbox = holder.checkbox;
                int stat;
                if(checkbox.isChecked() == true){
                     stat = 1;
                     tarea.setStatus(true);
                } else {
                     stat = 0;
                     tarea.setStatus(false);
                }

                db.execSQL("UPDATE "+ UtilidadesDatabase.TABLA_TAREAS+" SET '"+UtilidadesDatabase.TAREA_ESTADO+"' = "+stat+" WHERE "
                        +UtilidadesDatabase.TAREA_ID+" = "+tarea.getId());
                db.close();

                holder.elementoLayout.animate()
                        .translationX(holder.elementoLayout.getWidth())
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                //holder.elementoLayout.setVisibility(View.GONE);
                            }
                        });

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                tareas.borrar(posicion);
                                notifyDataSetChanged();
                            }
                        }, 500);
            }
        });

    }

    @Override public int getItemCount() {
        return tareas.tamanyo();
    }

}