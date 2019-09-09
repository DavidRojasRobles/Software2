package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.uisaludmovilv01.adaptadores.CitasRecyclerAdapter;
import com.example.uisaludmovilv01.adaptadores.DoctorCitasRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ListaCitasDoctorActivity extends NavigationMenu implements DoctorCitasRecyclerAdapter.OnCitaListener {
    private static final String TAG = "ListaCitasDoctorActivit";

    // Ui components
    private RecyclerView recyclerView;
//    private NavigationMenu menu;

    // variables
    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private ArrayList<Usuario> mUsuarios = new ArrayList<>();
    private DoctorCitasRecyclerAdapter doctorCitasRecyclerAdapter;
    private Doctor mDoctor;
    private ProjectRepositorio repositorio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas_doctor);
        recyclerView = findViewById(R.id.doctor_citasRV);

        repositorio = new ProjectRepositorio(this);


//        if (getIntent().hasExtra("selected_doctor")) {
//            mDoctor = (Doctor) getIntent().getSerializableExtra("selected_doctor");
//
//            Log.i(TAG, "onCreate: has extra i.");
//            Log.i(TAG, "onCreate: " + mDoctor.getNombre());
//
//            insertarCitas();
//            for (Procedimiento cita : citas){
//                insertarUsuarios(cita.getUsuario());
//            }
//        }

        mDoctor = NavigationMenu.getmDoctor();
        inicializarCitas();
        for (Procedimiento cita : citas){
            insertarUsuarios(cita.getUsuario());
        }

        initRecyclerView();

        setSupportActionBar((Toolbar) findViewById(R.id.citas_toolbar));
        setTitle("Citas");


    }

    /**
     * Inicializa la lista de citas para RecyclerView
     */
    private void inicializarCitas(){
        try {
            repositorio.getProcedimientosDoctor(mDoctor.getId()).observe(this, new Observer<List<Procedimiento>>() {
                @Override
                public void onChanged(@Nullable List<Procedimiento> procedimientos) {
                    if (citas.size() > 0)
                        citas.clear();
                    if (procedimientos != null) {
                        Log.i(TAG, "onChanged: proc recibidos.size() = " + procedimientos.size());
                        citas.addAll(procedimientos);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "El doctor no tiene citas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.i(TAG, "insertarCitas: No esta definido el usuario o doctor");
            Toast.makeText(getApplicationContext(),
                    "No esta definido el doctor", Toast.LENGTH_SHORT).show();
            finish();
            Log.i(TAG, "insertarCitas: " + e.toString());
        }
    }

    /**
     * Crea el obj Usuario desde la BD y lo inserta en la lista mUsuarios
     * @param usuarioId
     */
    private void insertarUsuarios(int usuarioId) {
        try {
            repositorio.getUnUsuario(usuarioId).observe(this, new Observer<Usuario>() {
                @Override
                public void onChanged(@Nullable Usuario usuario) {
                    if (usuario != null) {
                        Log.i(TAG, "onChanged: proc recibido." );
                        mUsuarios.add(usuario);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        doctorCitasRecyclerAdapter = new DoctorCitasRecyclerAdapter(citas, mUsuarios, this);
        recyclerView.setAdapter(doctorCitasRecyclerAdapter);
    }

    @Override
    public void onCitaClick(int position) {
        Log.i(TAG, "onCitaClick: clicked i.");

        Intent intent = new Intent(this, AtenderCitaActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_cita", citas.get(position));
        intent.putExtra("selected_doctor", mDoctor);

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }

}
