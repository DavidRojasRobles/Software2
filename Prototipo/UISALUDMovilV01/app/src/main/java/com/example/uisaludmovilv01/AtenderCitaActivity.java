package com.example.uisaludmovilv01;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.OrdenProcedimiento;
import com.example.uisaludmovilv01.modelos.Procedimiento;

import org.threeten.bp.LocalDate;

public class AtenderCitaActivity extends AppCompatActivity {

    private static final String TAG = "AtenderCitaActivity";

    //ui components

    private ImageButton ac_back;
    private TextView ac_usuario, ac_remision_vigencia, ac_medicina_vigencia;
    private CheckBox ac_remision_checkBox, ac_medicina_checkBox;
    private EditText ac_obs_remision, ac_obs_medicina;
    private Button ac_guardar;

    //vars
    private Procedimiento mCita;
    LocalDate vigencia_or;
    LocalDate vigencia_om;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atender_cita);

        ac_back = findViewById(R.id.ac_back);
        ac_usuario = findViewById(R.id.ac_usuario);
        ac_remision_vigencia = findViewById(R.id.ac_remision_vigencia);
        ac_medicina_vigencia = findViewById(R.id.ac_medicina_vigencia);
        ac_remision_checkBox = findViewById(R.id.ac_remision_checkBox);
        ac_medicina_checkBox = findViewById(R.id.ac_medicina_checkBox);
        ac_obs_remision = findViewById(R.id.ac_obs_remision);
        ac_obs_medicina = findViewById(R.id.ac_obs_medicina);
        ac_guardar = findViewById(R.id.ac_guardar);


        Log.i(TAG, "onCreate: called i.");

        if (getIntent().hasExtra("selected_cita")) {
            mCita = (Procedimiento) getIntent().getSerializableExtra("selected_cita");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mCita.toString());

            setCitaProperties();
            setListeners();
        }
    }


    private void setListeners() {

        ac_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ac_back clicked i.");

                finish();

            }
        });

        ac_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ac_guardar clicked i.");


                String obs_rem = ac_obs_remision.getText().toString();
                String obs_med = ac_obs_medicina.getText().toString();

                Log.i(TAG, "onClick: editText_rem = " + obs_rem);

                if (ac_remision_checkBox.isChecked() && !(obs_rem.equals(""))) {

                    mCita.getUsuario().ordenar(
                            new OrdenProcedimiento(
                                    (CitaMedica) mCita, "Ginecologia",
                                    obs_rem, vigencia_or)
                    );

                    //Remueva cita del doctor y el usuario

                    Toast.makeText(getApplicationContext(), "Orden de remision añadida",
                            Toast.LENGTH_SHORT).show();
                }

                Log.i(TAG, "onClick: editText_med = " + obs_med);

                if (ac_medicina_checkBox.isChecked() && !(obs_med.equals(""))) {

                    mCita.getUsuario().ordenar(
                            new OrdenMedicamento((CitaMedica) mCita, obs_med, vigencia_om));

                    //Remueva cita del doctor y el usuario

                    Toast.makeText(getApplicationContext(), "Orden de medicina añadida",
                            Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

    }

    private void setCitaProperties() {

        Log.i(TAG, "setCitaProperties: called i.");

        vigencia_or = LocalDate.of(mCita.getFecha().getYear(),
                mCita.getFecha().getMonth().getValue() + 1,
                mCita.getFecha().getDayOfMonth());

        vigencia_om = vigencia_or;

        ac_usuario.setText(mCita.getUsuario().getNombre());
        ac_remision_vigencia.setText(vigencia_or.toString());
        ac_medicina_vigencia.setText(vigencia_om.toString());

        Log.i(TAG, "setCitaProperties: set all properties i.");

    }


}
