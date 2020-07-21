package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

public class SingleOrdenActivity extends AppCompatActivity {

    private static final String TAG = "SingleOrdenActivity";

    //ui components
    private ImageButton single_elem_back;
    private TextView single_elem_title;
    private TextView single_elem_subtitle;
    private TextView single_orden_fecha;
    private TextView single_orden_doctor;
    private TextView single_orden_esp;
    private TextView single_orden_obs;
    private TextView single_orden_vence;
    private ConstraintLayout so_bottom;

    //vars
    private Orden orden;
    private Procedimiento mCita;
    private ProjectRepositorio repositorio;
    private String nombreDoctor;
    private String espNombre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_orden);

        single_elem_back = findViewById(R.id.set_back_button);
        single_elem_title = findViewById(R.id.single_elem_title);
        single_elem_subtitle = findViewById(R.id.single_elem_subtitle);
        single_orden_fecha = findViewById(R.id.single_orden_fecha);
        single_orden_doctor = findViewById(R.id.single_orden_doctor);
        single_orden_esp = findViewById(R.id.single_orden_esp);
        single_orden_obs = findViewById(R.id.single_orden_obs);
        single_orden_vence = findViewById(R.id.single_orden_vence);
        so_bottom = findViewById(R.id.so_bottom);

        repositorio = new ProjectRepositorio(this);

        Log.i(TAG, "onCreate: called i.");

        if (getIntent().hasExtra("selected_orden")) {
            orden = (Orden) getIntent().getSerializableExtra("selected_orden");

            encontrarCita(orden.getCita());

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + orden.toString());

            setSOBackButtonListener();
            setOrdenProperties();
        }

    }

    private void setSOBackButtonListener() {
        single_elem_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ag_back clicked i.");

                finish();

            }
        });
    }

    private void setOrdenProperties() {

        Log.i(TAG, "setOrdenProperties: called i.");

        String title = "Orden: ";
        String x = "Medicamento";

        if (orden.getTipo() == 1) {
            title = "Remisión: ";
            getEspecialidadOrden(orden);
            x = espNombre;
            single_orden_esp.setVisibility(View.VISIBLE);
            if (orden.getVigencia()) {
                so_bottom.setVisibility(View.VISIBLE);
            }
        }
        single_elem_title.setText(title);
        single_elem_subtitle.setText(x);
        single_orden_fecha.setText(mCita.getFecha().toString());
        getNombreDoctor(mCita.getDoctor());
        single_orden_doctor.setText(nombreDoctor);
        single_orden_esp.setText(x);
        single_orden_obs.setText(orden.getObservaciones());
        single_orden_vence.setText(orden.getFechaVigencia().toString());

        Log.i(TAG, "setCitaProperties: set all properties i.");

    }

    private void encontrarCita(int citaId) {
        try {
            repositorio.getProcedimientoById(citaId).observe(this, new Observer<Procedimiento>() {
                @Override
                public void onChanged(@Nullable Procedimiento procedimiento) {
                    if (procedimiento != null) {
                        mCita = procedimiento;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No existe el procedimiento", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "getDoctor: e.toString()");
        }
    }

    private void getNombreDoctor(int doctorId) {
        try {
            repositorio.getNombreDoctor(doctorId).observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    if (s != null)
                        nombreDoctor = s;
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "getNombreDoctor: " + e.toString());
        }
    }

    private void getEspecialidadOrden(Orden orden) {

        try {
            repositorio.getEspecialidadById(orden.getEspecialidad()).observe(this, new Observer<Especialidad>() {
                    @Override
                    public void onChanged(@Nullable Especialidad especialidad) {
                        if(!especialidad.equals(null)){
                            espNombre = especialidad.getEspNombre();
                        }
                    }
                }
            );
        } catch (Exception e) {
            Log.i(TAG, "getNombreDoctor: " + e.toString());
        }
    }
}
