package com.example.uisaludmovilv01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uisaludmovilv01.adaptadores.CitasRecyclerAdapter;
import com.example.uisaludmovilv01.adaptadores.OrdenesRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class ListaOrdenesActivity extends AppCompatActivity {


    private static final String TAG = "ListaOrdenesActivity";

    // Ui components
    private RecyclerView recyclerView;

    // variables
    private ArrayList<Orden> ordenes = new ArrayList<>();
    private OrdenesRecyclerAdapter ordenesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenes);
        recyclerView = findViewById(R.id.ordenesRV);

        initRecyclerView();
        insertarOrdenesFalsas();


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
        Orden orden1 = new OrdenMedicamento(cita1, "Dolex", LocalDate.of(2019, 9, 12), true);

        ordenes.add(cita1);
        ordenes.add(cita2);
        ordenes.add(cita3);
        ordenes.add(cita4);
        ordenes.add(cita5);

        ordenesRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ordenesRecyclerAdapter = new OrdenesRecyclerAdapter(ordenes);
        recyclerView.setAdapter(ordenesRecyclerAdapter);
    }

}


