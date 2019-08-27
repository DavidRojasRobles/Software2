package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.uisaludmovilv01.adaptadores.CitasRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class ListaCitasDoctorActivity extends NavigationMenu implements CitasRecyclerAdapter.OnCitaListener {
    private static final String TAG = "ListaCitasDoctorActivit";

    // Ui components
    private RecyclerView recyclerView;
//    private NavigationMenu menu;

    // variables
    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private CitasRecyclerAdapter citasRecyclerAdapter;
//    private Usuario usuarioRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas);
        recyclerView = findViewById(R.id.citasRV);


//        if (getIntent().hasExtra("selected_usuario")) {
//            usuarioRecibido = (Usuario) getIntent().getSerializableExtra("selected_usuario");
//
//            Log.i(TAG, "onCreate: has extra i.");
//            Log.i(TAG, "onCreate: " + usuarioRecibido.getNombre());
//        }


        initRecyclerView();
        insertarCitasFalsas();

        setSupportActionBar((Toolbar) findViewById(R.id.citas_toolbar));
        setTitle("Citas");


    }

    private void insertarCitasFalsas() {
        Usuario user1 = new Usuario();

        Doctor d1 = new Doctor("Dr. One", "101", "General");
        d1.anadirDia("MONDAY", new boolean[]{true, true, false, false, false,
                false, false, false, false, false, false});
        d1.anadirDia("THURSDAY", new boolean[]{true, true, true, false, false,
                false, false, false, true, true, false});

        Procedimiento cita1 = new CitaMedica(
                user1,
                LocalDate.of(2019, 8, 12),
                LocalTime.of(8, 0),
                d1);
        Procedimiento cita2 = new CitaMedica(
                user1,
                LocalDate.of(2019, 8, 15),
                LocalTime.of(10, 0),
                d1);
        Procedimiento cita3 = new CitaMedica(
                user1,
                LocalDate.of(2019, 8, 26),
                LocalTime.of(9, 0),
                d1);
        Procedimiento cita4 = new CitaMedica(
                user1,
                LocalDate.of(2019, 8, 26),
                LocalTime.of(8, 0),
                d1);
        Procedimiento cita5 = new CitaMedica(
                user1,
                LocalDate.of(2019, 8, 29),
                LocalTime.of(16, 0),
                d1);
        citas.add(cita1);
        citas.add(cita2);
        citas.add(cita3);
        citas.add(cita4);
        citas.add(cita5);

        citasRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        citasRecyclerAdapter = new CitasRecyclerAdapter(citas, this);
        recyclerView.setAdapter(citasRecyclerAdapter);
    }

    @Override
    public void onCitaClick(int position) {
        Log.i(TAG, "onCitaClick: clicked i.");

        Intent intent = new Intent(this, AtenderCitaActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_cita", citas.get(position));

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }

}
