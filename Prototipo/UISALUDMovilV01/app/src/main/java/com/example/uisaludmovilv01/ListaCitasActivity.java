package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.uisaludmovilv01.adaptadores.CitasRecyclerAdapter;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class ListaCitasActivity extends AppCompatActivity implements CitasRecyclerAdapter.OnCitaListener {
    private static final String TAG = "ListaCitasActivity";

    // Ui components
    private RecyclerView recyclerView;
//    private Menu1 menu;

    // variables
    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private CitasRecyclerAdapter citasRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas);
        recyclerView = findViewById(R.id.citasRV);


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

        Intent intent = new Intent(this, SingleCitaActivity.class);
        Log.i(TAG, "onCitaClick: intent created i.");
        intent.putExtra("selected_cita", citas.get(position));

        Log.i(TAG, "onCitaClick: intent extra added i.");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_agendar:
                Log.i(TAG, "onClick: menu_btn_agendar i.");
                return true;

            case R.id.menu_ordenes:
                Log.i(TAG, "onClick: menu_btn_ordenes i.");
                intent = new Intent(this, ListaOrdenesActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_perfil:
                Log.i(TAG, "onClick: menu_btn_perfil i.");
                intent = new Intent(this, PerfilActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_citas:
                Log.i(TAG, "onClick: menu_btn_citas i.");
                intent = new Intent(this, ListaCitasActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
