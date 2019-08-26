package com.example.uisaludmovilv01;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Procedimiento;

public class SingleCitaActivity extends AppCompatActivity {

    private static final String TAG = "SingleCitaActivity";

    //ui components
    private Dialog prompt;
    private TextView pd_title, pd_content;
    private Button pd_cancel_btn, pd_accept_btn;
    private ImageButton single_elem_back;
    private TextView single_elem_title;
    private TextView single_elem_subtitle;
    private TextView single_cita_fecha;
    private TextView single_cita_hora;
    private TextView single_cita_doctor;
    private TextView single_cita_consultorio;
    private Button single_cita_cancelar;

    //vars
    private Procedimiento mCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cita);

        prompt = new Dialog(this);
        prompt.setContentView(R.layout.layout_dialog);
        prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        pd_title = prompt.findViewById(R.id.pd_title);
        pd_content = prompt.findViewById(R.id.pd_content);
        pd_cancel_btn = prompt.findViewById(R.id.pd_cancel_btn);
        pd_accept_btn = prompt.findViewById(R.id.pd_accept_btn);

        single_elem_back = findViewById(R.id.set_back_button);
        single_elem_title = findViewById(R.id.single_elem_title);
        single_elem_subtitle = findViewById(R.id.single_elem_subtitle);
        single_cita_fecha = findViewById(R.id.single_cita_fecha);
        single_cita_hora = findViewById(R.id.single_cita_hora);
        single_cita_doctor = findViewById(R.id.single_cita_doctor);
        single_cita_consultorio = findViewById(R.id.single_cita_consultorio);
        single_cita_cancelar = findViewById(R.id.single_cita_cancelar);

        Log.i(TAG, "onCreate: called i.");

        if (getIntent().hasExtra("selected_cita")) {
            mCita = (Procedimiento) getIntent().getSerializableExtra("selected_cita");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mCita.toString());

            setListeners();
            setCitaProperties();
        }
    }


    private void setListeners(){

        single_elem_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ag_back clicked i.");

                finish();

            }
        });

        single_cita_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ag_agendar clicked i.");

                setPromptDialog();

                prompt.show();

            }
        });

    }

    private void setPromptDialog() {

        pd_title.setText("¿CANCELAR ESTA CITA?");
        pd_content.setText(mCita.getDatos());

        pd_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.hide();
            }
        });
        pd_accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: cita cancelada i.");

                //Cancelar cita

                mCita.getUsuario().cancelarCita(mCita);

                Toast.makeText(getApplicationContext(), "Cita cancelada",
                        Toast.LENGTH_SHORT).show();
                prompt.hide();
                finish();
            }
        });


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
