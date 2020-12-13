package com.alejandrolosa.tasktracker.servicios;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.alejandrolosa.tasktracker.MainActivity;
import com.alejandrolosa.tasktracker.R;
import com.alejandrolosa.tasktracker.TareasOlvidadasActivity;
import com.alejandrolosa.tasktracker.datos.DatabaseSQLite;

import java.util.Calendar;

public class ServicioNotificaciones extends Service {
    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean value = pref.getBoolean("notificaciones",false);

        if (value == true){
            DatabaseSQLite conn = new DatabaseSQLite(this, "bd_tareas", null, 1);
            SQLiteDatabase database = conn.getWritableDatabase();

            Calendar calendario = Calendar.getInstance();
            int mesHoy = calendario.get(Calendar.MONTH) + 1;
            int yearHoy = calendario.get(Calendar.YEAR);
            int diaHoy = calendario.get(Calendar.DATE)-1;
            int fecha = Integer.parseInt(""+yearHoy+mesHoy+diaHoy);
            Log.e("FECHA", ""+fecha);

            Cursor consulta = database.rawQuery("SELECT * FROM tareas WHERE estado = 0 AND orden = "+fecha, null);
            if (consulta.moveToFirst()){
                lanzarNotificacion();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intencion) {
        return null;
    }

    public void lanzarNotificacion() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CANAL_ID, "Mis Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this, CANAL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("No has completado la tarea!")
                .setContentText("Tenías una tarea asignada para ayer que no has completado.");
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, TareasOlvidadasActivity.class), 0);
        notificacion.setContentIntent(intencionPendiente);

        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
    }

}
