package com.example.uisaludmovilv01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class SingleCitaActivity extends AppCompatActivity {

    private static final String TAG = "SingleCitaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cita);

        Log.d(TAG, "onCreate: called d.");
        Log.i(TAG, "onCreate: called i.");

//        setSupportActionBar((Toolbar)findViewById(R.id.single_cita_toolbar));
//        setTitle("Cita n");
    }
}
