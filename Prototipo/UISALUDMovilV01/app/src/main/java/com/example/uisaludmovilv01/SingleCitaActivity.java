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
    private TextView single_elem_title;
    private TextView single_elem_subtitle;
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

        single_elem_title = findViewById(R.id.single_elem_title);
        single_elem_subtitle = findViewById(R.id.single_elem_subtitle);
        single_cita_fecha = findViewById(R.id.single_cita_fecha);
        single_cita_hora = findViewById(R.id.single_cita_hora);
        single_cita_doctor = findViewById(R.id.single_cita_doctor);
        single_cita_consultorio = findViewById(R.id.single_cita_consultorio);

        Log.i(TAG, "onCreate: called i.");

        if (getIntent().hasExtra("selected_cita")) {
            mCita = (Procedimiento) getIntent().getSerializableExtra("selected_cita");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mCita.toString());
            setCitaProperties();
        }
    }

    private void setCitaProperties() {

        Log.i(TAG, "setCitaProperties: called i.");

        String title = "Cita: ";

        if(mCita.getClass() == Procedimiento.class){
            title = "Procedimiento: ";
        }

        single_elem_title.setText(title);
        single_elem_subtitle.setText(mCita.getDoctor().getEspecialidad());
        single_cita_fecha.setText(mCita.getFecha().toString());
        single_cita_hora.setText(mCita.getHora().toString());
        single_cita_doctor.setText(mCita.getDoctor().getNombre());
        single_cita_consultorio.setText(mCita.getDoctor().getConsultorio());

        Log.i(TAG, "setCitaProperties: set all properties i.");

    }


}
