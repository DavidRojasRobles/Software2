package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.uisaludmovilv01.adaptadores.CitasRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ListaCitasActivity extends NavigationMenu implements CitasRecyclerAdapter.OnCitaListener {
    private static final String TAG = "ListaCitasActivity";

    // Ui components
    private RecyclerView recyclerView;
    private FloatingActionButton citas_agendar;
//    private NavigationMenu menu;

    // variables
    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private ArrayList<Doctor> mDoctores = new ArrayList<>();
    private CitasRecyclerAdapter citasRecyclerAdapter;
    private Usuario mUsuario;
    private ProjectRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas);
        recyclerView = findViewById(R.id.citasRV);

        citas_agendar = findViewById(R.id.citas_agendar);

        repositorio = new ProjectRepositorio(this);

        if (getIntent().hasExtra("selected_usuario")) {
            mUsuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mUsuario.toString());
        }

        initRecyclerView();
        //insertarCitasFalsas();
        insertarCitas();
        for(Procedimiento cita : citas){
            insertarDoctores(cita.getDoctor());
        }
        setAgendarListener();

        setSupportActionBar((Toolbar) findViewById(R.id.citas_toolbar));
        setTitle("Citas");


    }

    public void setAgendarListener() {
        citas_agendar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: agendar called i.");

                Intent intent = new Intent(getApplicationContext(), AgendarActivity.class);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);

            }
        });
    }
/**
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
**/

    private void insertarCitas() {
        Log.i(TAG, "insertarCitas: called i.");
        try {
            Log.i(TAG, "insertarCitas: inside try i.");
            Log.i(TAG, "insertarCitas: mUsuario = " + mUsuario.getNombre());
            repositorio.getProcedimientosUsuario(mUsuario.getId()).observe(this, new Observer<List<Procedimiento>>() {

                @Override
                public void onChanged(@Nullable List<Procedimiento> procedimientos) {
                    Log.i(TAG, "onChanged: called i.");
                    if (citas.size() > 0)
                        citas.clear();
                    if (procedimientos != null) {
                        Log.i(TAG, "onChanged: proc recibidos.size() = " + procedimientos.size());
                        citas.addAll(procedimientos);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "El usuario no tiene citas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.i(TAG, "insertarCitas: No esta definido el usuario o doctor");
            Toast.makeText(getApplicationContext(),
                    "No esta definido el usuario", Toast.LENGTH_SHORT).show();
            finish();
            Log.i(TAG, "insertarCitas: " + e.toString());
        }
    }

    private void insertarDoctores(int doctorId) {
        Log.i(TAG, "insertarDoctores: called i.");
        try {
            repositorio.encontrarDoctor(doctorId).observe(this, new Observer<Doctor>() {
                @Override
                public void onChanged(@Nullable Doctor doctor) {
                    if (doctor != null) {
                        Log.i(TAG, "onChanged: proc recibido." );
                        mDoctores.add(doctor);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "El doctor no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.i(TAG, "insertarDoctores: No hay doctores definidos");
            Toast.makeText(getApplicationContext(),
                    "No hexiste el doctor", Toast.LENGTH_SHORT).show();
            finish();
            Log.i(TAG, "insertarDoctores: " + e.toString());
        }
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView: called i.");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        citasRecyclerAdapter = new CitasRecyclerAdapter(citas, mDoctores, this);
        recyclerView.setAdapter(citasRecyclerAdapter);
    }

    @Override
    public void onCitaClick(int position) {
        Log.i(TAG, "onCitaClick: clicked i.");

        Intent intent = new Intent(this, SingleCitaActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_cita", citas.get(position));
        intent.putExtra("selected_usuario", mUsuario);

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }

}
