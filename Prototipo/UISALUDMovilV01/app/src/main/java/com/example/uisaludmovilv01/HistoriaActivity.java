package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uisaludmovilv01.adaptadores.HistoriaRecyclerAdapter;
import com.example.uisaludmovilv01.adaptadores.OrdenesRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.OrdenProcedimiento;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class HistoriaActivity extends AppCompatActivity {


    private static final String TAG = "HistoriaActivity";

    // Ui components
    private RecyclerView recyclerView;
    private ImageButton historia_back;
    private TextView single_elem_title, single_elem_subtitle;

    // variables
    private ArrayList<String> historia = new ArrayList<>();
    private HistoriaRecyclerAdapter historiaRecyclerAdapter;

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

        if (getIntent().hasExtra("selected_usuario")) {
            Usuario usuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");

            usuario.archivar("Uh, at least one, I guess.");

            historia = usuario.getHistoria();

            single_elem_title.setText(usuario.getNombre());
            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: historia.size() = " + historia.size() + " i.");


        }
        
        setBackListener();
        
        initRecyclerView();
        
        
//        setSupportActionBar((Toolbar) findViewById(R.id.historia_header));
//        setTitle("Historia");

        Log.i(TAG, "onCreate: SetTitle i.");


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


