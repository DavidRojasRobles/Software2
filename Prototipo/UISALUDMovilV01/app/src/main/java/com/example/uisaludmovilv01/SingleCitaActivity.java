package com.example.uisaludmovilv01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.uisaludmovilv01.modelos.Procedimiento;

public class SingleCitaActivity extends AppCompatActivity {

    private static final String TAG = "SingleCitaActivity";

    //ui components
    private TextView single_cita_fecha;
    private TextView single_cita_hora;
    private TextView single_cita_doctor;
    private TextView single_cita_consultorio;

    //vars
    private Procedimiento mCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cita);

        single_cita_fecha = findViewById(R.id.single_cita_fecha);
        single_cita_hora = findViewById(R.id.single_cita_hora);
        single_cita_doctor = findViewById(R.id.single_cita_doctor);
        single_cita_consultorio = findViewById(R.id.single_cita_consultorio);

        Log.i(TAG, "onCreate: called i.");

        if (getIntent().hasExtra("selected_cita")) {
            mCita = getIntent().getParcelableExtra("selected_cita");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mCita.toString());
        }
        setCitaProperties();
    }

    private void setCitaProperties() {

        Log.i(TAG, "setCitaProperties: called i.");
        single_cita_fecha.setText("mayo 14");

        Log.i(TAG, "setCitaProperties: set all properties i.");

    }


}
