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

import com.example.uisaludmovilv01.adaptadores.OrdenesRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ListaOrdenesActivity extends NavigationMenu implements OrdenesRecyclerAdapter.OnOrdenListener {


    private static final String TAG = "ListaOrdenesActivity";

    // Ui components
    private RecyclerView recyclerView;

    // variables
    private ArrayList<Orden> mOrdenes = new ArrayList<>();
    private ArrayList<Procedimiento> mCitas = new ArrayList<>();
    private ArrayList<Doctor> mDoctores = new ArrayList<>();
    private OrdenesRecyclerAdapter ordenesRecyclerAdapter;
    private Usuario mUsuario;
    private ProjectRepositorio repositorio;

    NavigationMenu menu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenes);
        recyclerView = findViewById(R.id.ordenesRV);

        repositorio = new ProjectRepositorio(this);

//        if (getIntent().hasExtra("selected_usuario")) {
//            mUsuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");
//
//            insertarOrdenes();
//
//            Log.i(TAG, "onCreate: has extra i.");
//            Log.i(TAG, "onCreate: " + mUsuario.getNombre());
//        }

        mUsuario = NavigationMenu.getmUsuario();
        inicializarOrdenes();

        for(Orden o : mOrdenes){
            insertarCitas(o.getCita());
            insertarDoctores(mCitas.get(mCitas.size()-1).getDoctor());
        }

        initRecyclerView();

        setSupportActionBar((Toolbar) findViewById(R.id.ordenes_toolbar));
        setTitle("Ordenes");


    }

    /**
     * Obtiene las ordenes de la BD
     */
    private void inicializarOrdenes() {
        try {
            repositorio.getOrdenesUsuario(mUsuario.getId()).observe(this, new Observer<List<Orden>>() {
                @Override
                public void onChanged(@Nullable List<Orden> o) {
                    if (mOrdenes.size() > 0)
                        mOrdenes.clear();
                    if (o != null) {
                        Log.i(TAG, "onChanged: proc recibidos.size() = " + o.size());
                        mOrdenes.addAll(o);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "El usuario no tiene ordenes", Toast.LENGTH_SHORT).show();
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

    /**
     * Crea el obj cita de la BD y lo inserta en la lista mCitas
     * @param citaId
     */
    private void insertarCitas(int citaId) {
        try {
            repositorio.getProcedimientoById(citaId).observe(this, new Observer<Procedimiento>() {
                @Override
                public void onChanged(@Nullable Procedimiento cita) {
                    if (cita != null) {
                        Log.i(TAG, "onChanged: proc recibido." );
                        mCitas.add(cita);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "La cita no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.i(TAG, "insertarCitas: No esta definida la cita");
            Toast.makeText(getApplicationContext(),
                    "No esta definida la cita", Toast.LENGTH_SHORT).show();
            finish();
            Log.i(TAG, "insertarCitas: " + e.toString());
        }
    }

    /**
     * Crea el obj doctor de la BD y lo inserta en la lista mDoctores
     * @param doctorId
     */
    private void insertarDoctores(int doctorId) {
        try {
            repositorio.getUnDoctor(doctorId).observe(this, new Observer<Doctor>() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ordenesRecyclerAdapter = new OrdenesRecyclerAdapter(mOrdenes, mDoctores, mCitas, this);
        recyclerView.setAdapter(ordenesRecyclerAdapter);
    }

    @Override
    public void onOrdenClick(int position) {
        Log.i(TAG, "onCitaClick: clicked i.");

        Intent intent = new Intent(this, SingleOrdenActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_orden", mOrdenes.get(position));

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }


}


