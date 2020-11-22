package com.alejandrolosa.tasktracker;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SyncStateContract;

public class AcercaDeActivity extends Activity {
    @Override
    public void onCreate (Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.acercade);
    }
}
