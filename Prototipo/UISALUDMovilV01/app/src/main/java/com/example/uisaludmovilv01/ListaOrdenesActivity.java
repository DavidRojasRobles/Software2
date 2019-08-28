package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.uisaludmovilv01.adaptadores.OrdenesRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class ListaOrdenesActivity extends NavigationMenu implements OrdenesRecyclerAdapter.OnOrdenListener {


    private static final String TAG = "ListaOrdenesActivity";

    // Ui components
    private RecyclerView recyclerView;

    // variables
    private ArrayList<Orden> ordenes = new ArrayList<>();
    private OrdenesRecyclerAdapter ordenesRecyclerAdapter;

    NavigationMenu menu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenes);
        recyclerView = findViewById(R.id.ordenesRV);

        initRecyclerView();
        insertarOrdenesFalsas();

        setSupportActionBar((Toolbar) findViewById(R.id.ordenes_toolbar));
        setTitle("Ordenes");


    }

    private void insertarOrdenesFalsas() {
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
        Orden orden1 = new OrdenMedicamento((CitaMedica) cita1, "Dolex", LocalDate.of(2019, 9, 12));
        Orden orden2 = new OrdenProcedimiento((CitaMedica) cita1, "Odontologia", "Remisión por caries", LocalDate.of(2019, 9, 12));
        Orden orden3 = new OrdenMedicamento((CitaMedica) cita2, "Corticoide", LocalDate.of(2019, 8, 15));
        Orden orden4 = new OrdenProcedimiento((CitaMedica) cita2, "Dermatologia", "Test alergicos", LocalDate.of(2019, 9, 12));

        ordenes.add(orden1);
        ordenes.add(orden2);
        ordenes.add(orden3);
        ordenes.add(orden4);
        ordenes.add(orden1);

        ordenesRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ordenesRecyclerAdapter = new OrdenesRecyclerAdapter(ordenes, this);
        recyclerView.setAdapter(ordenesRecyclerAdapter);
    }

    @Override
    public void onOrdenClick(int position) {
        Log.i(TAG, "onCitaClick: clicked i.");

        Intent intent = new Intent(this, SingleOrdenActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_orden", ordenes.get(position));

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }


}


