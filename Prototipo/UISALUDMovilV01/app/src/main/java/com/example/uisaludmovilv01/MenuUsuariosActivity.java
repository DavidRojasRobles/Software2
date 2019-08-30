package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.content.Intent;
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

import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class MenuUsuariosActivity extends AppCompatActivity {

    private static final String TAG = "MenuUsuariosActivity";
    //ui components
    private Button usuario1;
    private Button usuario2;
    private Button usuario3;
    private Button doctor1;
    private Button doctor2;
    private Intent intent;

    //vars
    private Usuario mUsuario;
    private Doctor mDoctor;
    private ArrayList<Procedimiento> a = new ArrayList<>();
    private ProjectRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuarios);


        usuario1 = findViewById(R.id.usuario1);
        usuario2 = findViewById(R.id.usuario2);
        usuario3 = findViewById(R.id.usuario3);
        doctor1 = findViewById(R.id.doctor1);
        doctor2 = findViewById(R.id.doctor2);

        Log.i(TAG, "onCreate: called i.");

        repositorio = new ProjectRepositorio(this);

        Log.i(TAG, "onCreate: se va a añadir el proceimiento.");
        aniadirProcedimientos();
        Log.i(TAG, "onCreate: se añadio el procedimiento.");

        setSupportActionBar((Toolbar) findViewById(R.id.home_toolbar));
        setTitle("UISALUD Movil");

        setMenuProperties();
        setButtonListeners();

    }

    private void setMenuProperties() {
        Log.i(TAG, "setMenuProperties: called i.");
        usuario1.setText("usuario1");
        usuario2.setText("usuario2");
        usuario3.setText("usuario3");
        doctor1.setText("doctor1");
        doctor2.setText("doctor2");
    }

    private void initializeUsuario(int id) {

        repositorio.encontrarUsuario(id).observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable Usuario usuario) {
                if (usuario != null) {
                    mUsuario = usuario;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No existe el usuario", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void aniadirProcedimientos(){
        Log.i(TAG, "aniadirProcedimientos: called i.");

        Usuario user1 = new Usuario();

        Doctor d1 = new Doctor("Dr. One", "101", "General");

        Procedimiento cita1 = new Procedimiento(
                0,
                user1.getId(),
                d1.getId(),
                LocalDate.of(2019, 8, 12),
                LocalTime.of(8, 0),
                d1.getEspecialidad());


        repositorio.insertarProcedimientoTask(cita1);

        checkDB();

    }

    private void checkDB(){
        Log.i(TAG, "checkDB: called i.");
        Log.i(TAG, "checkDB: before a.size() = " + a.size());
        repositorio.getProcedimientos().observe(this, new Observer<List<Procedimiento>>() {
            @Override
            public void onChanged(@Nullable List<Procedimiento> procedimientos) {
                Log.i(TAG, "onChanged: procedimientos.size() i. = " + procedimientos.size());
                if (procedimientos != null)
                    a.addAll(procedimientos);
            }
        });
        Log.i(TAG, "checkDB: after a.size() = " + a.size());
    }


    private void initializeDoctor(int id) {


        repositorio.encontrarDoctor(id).observe(this, new Observer<Doctor>() {
            @Override
            public void onChanged(@Nullable Doctor doctor) {
                if (doctor != null) {
                    mDoctor = doctor;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No existe el doctor", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    public void setButtonListeners() {
        Log.i(TAG, "setButtonListeners: ");
        Intent intent;
        usuario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
//                initializeUsuario(1);
                NavigationMenu.setmUsuario(new Usuario());
                mUsuario = NavigationMenu.getmUsuario();
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        usuario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                initializeUsuario(2);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        usuario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 3 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                initializeUsuario(3);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        doctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasDoctorActivity.class);
                //Change for corresponding user
                initializeDoctor(1);
                intent.putExtra("selected_doctor", mDoctor);
                startActivity(intent);
            }
        });
        doctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasDoctorActivity.class);
                //Change for corresponding user
                initializeDoctor(2);
                intent.putExtra("selected_doctor", mDoctor);
                startActivity(intent);
            }
        });
    }

}
