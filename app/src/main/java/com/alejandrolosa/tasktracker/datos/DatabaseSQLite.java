package com.alejandrolosa.tasktracker.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {

    public DatabaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UtilidadesDatabase.CREAR_TABLA_TAREAS); // Utilizo la constante de crear la bbdd definida en UtilidadesDatabase
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tareas");
        onCreate(db);
    }
}
