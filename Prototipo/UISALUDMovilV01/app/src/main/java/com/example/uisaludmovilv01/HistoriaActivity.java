package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uisaludmovilv01.adaptadores.HistoriaRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

import java.util.ArrayList;
import java.util.List;

public class HistoriaActivity extends AppCompatActivity {


    private static final String TAG = "HistoriaActivity";

    // Ui components
    private RecyclerView recyclerView;
    private ImageButton historia_back;
    private TextView single_elem_title, single_elem_subtitle;

    // variables
    private Usuario mUsuario;
    private String nombreDoctor;
    private ArrayList<Evolucion> historia = new ArrayList<>();
    private HistoriaRecyclerAdapter historiaRecyclerAdapter;
    private ProjectRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: iniciated i.");

        setContentView(R.layout.activity_historia);
        Log.i(TAG, "onCreate: content view set i.");

        recyclerView = findViewById(R.id.historiaRV);
        historia_back = findViewById(R.id.set_back_button);
        single_elem_title = findViewById(R.id.single_elem_title);
        single_elem_subtitle = findViewById(R.id.single_elem_subtitle);
        single_elem_title.setText("Usuario");
        single_elem_subtitle.setText(": Historia clínica");

        repositorio = new ProjectRepositorio(this);

        Log.i(TAG, "onCreate: called i.");
        if (getIntent().hasExtra("selected_usuario")) {
            mUsuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");
            nombreDoctor = "";

            inicializarHistoria();

            single_elem_title.setText(mUsuario.getNombre());
            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: historia.size() = " + historia.size() + " i.");
        } else {
            Log.i(TAG, "onCreate: no se recibió el usuario.");
            finish();
        }

        setBackListener();
        initRecyclerView();


        Log.i(TAG, "onCreate: SetTitle i.");


    }

    private void inicializarHistoria() {

        repositorio.getEvolucionesUsuario(mUsuario.getId()).observe(this, new Observer<List<Evolucion>>() {
            @Override
            public void onChanged(@Nullable List<Evolucion> evolucions) {

                if(historia.size() > 0){
                    historia.clear();
                }
                if(evolucions != null){
                    historia.addAll(evolucions);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "El usuario no tiene historia", Toast.LENGTH_SHORT).show();
                }

                for(Evolucion ev : historia){
                    getNombreDoctor(ev.getDoctor());
                    ev.setNombreDoctor(nombreDoctor);
                }

            }
        });

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
        } catch (Exception e){
            Log.i(TAG, "getNombreDoctor: " + e.toString());
        }
    }

    private void setBackListener() {
        Log.i(TAG, "setBackListener: called i.");
        historia_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: historia_back clicked i.");

                finish();

            }
        });
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView: called i.");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        Log.i(TAG, "initRecyclerView: lineatLayout created i.");
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.i(TAG, "initRecyclerView: lineatLayout set to recyclerView i.");
        historiaRecyclerAdapter = new HistoriaRecyclerAdapter(historia);
        Log.i(TAG, "initRecyclerView: historiaRecyclerAdapter created i.");
        recyclerView.setAdapter(historiaRecyclerAdapter);
        Log.i(TAG, "initRecyclerView: historiaRecyclerAdapter set i.");
    }


}


