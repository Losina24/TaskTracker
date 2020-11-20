package com.alejandrolosa.tasktracker.controladores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alejandrolosa.tasktracker.R;

public class MainActivity extends AppCompatActivity {
    // Atributos
    private RecyclerView recyclerView;
    public Adaptador adaptador;
    private RecyclerView.LayoutManager layoutManager;

    // Métodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recycled View
        recyclerView = findViewById(R.id.recycler_view);
        adaptador = new Adaptador(this, lugares);
        recyclerView.setAdapter(adaptador); // Establecemos el adaptador del RV
        layoutManager = new LinearLayoutManager(this); // Establecemos el layout del RV: Linear Layout
        recyclerView.setLayoutManager(layoutManager);
    }
}