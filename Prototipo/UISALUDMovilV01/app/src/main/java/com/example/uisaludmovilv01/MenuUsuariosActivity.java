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
import com.example.uisaludmovilv01.modelos.Especialidad;
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
    private Especialidad mEspecialidad;
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


//        Log.i(TAG, "onCreate: se va a aniaadir el proceimiento.");
//        aniadirProcedimientos();
//        checkDB();
//        Log.i(TAG, "onCreate: se aniaadio el procedimiento.");

        setSupportActionBar((Toolbar) findViewById(R.id.home_toolbar));
        setTitle("UISALUD Movil");

        setBaseUsuarios();
//        setBaseDoctores();
//        setButtonListeners();
//        setMenuProperties();

    }

    private void setMenuProperties() {
        Log.i(TAG, "setMenuProperties: called i.");
        usuario1.setText("usuario1");
        usuario2.setText("usuario2");
        usuario3.setText("usuario3");
        doctor1.setText("doctor1");
        doctor2.setText("doctor2");
    }

    /**
     * Inicializa usuarios en la base de datos si no existen.
     */
    private void setBaseUsuarios(){
        Log.i(TAG, "setBaseUsuarios: called i.");

        repositorio.getUsuarios().observe(this, new Observer<List<Usuario>>() {

            @Override
            public void onChanged(@Nullable List<Usuario> usuarios) {
                if( usuarios.size() < 3){
                    Log.i(TAG, "onChanged: los usuarios no existen.");
                    Usuario usuario;
                    for (int i = 1; i <= 3; i++){
                        Log.i(TAG, "onChanged: se anadirá el usuario " + i);
                        usuario = new Usuario("Usuario" + i,
                                "0" + i, "0" + i, "0" + i);

                        repositorio.insertarUsuarioTask(usuario);
                    }
                    Log.i(TAG, "onChanged: Se anadieron los 3 usuarios.");
                }else{
                    Log.i(TAG, "onChanged: Los usuarios ya existen.");
                }
            }
        });
    }

    /**
     * Inicializa doctores en la base de datos si no existen.
     */
    private void setBaseDoctores(){
        Log.i(TAG, "setBaseDoctores: called i.");

        repositorio.getDoctores().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(@Nullable List<Doctor> doctores) {
                if( doctores.size() < 2){
                    Log.i(TAG, "onChanged: los doctores no existen.");
                    Doctor doctor;
                    for (int i = 1; i <= 2; i++){
                        Log.i(TAG, "onChanged: se anadirá el doctor " + i);
                        getEspecialidad(i);
                        doctor = new Doctor("Doctor" + i,
                                "0" + i, "0" + i, mEspecialidad);

                        repositorio.insertarDoctorTask(doctor);
                    }
                    Log.i(TAG, "onChanged: Se anadieron los 2 doctores.");
                }else{
                    Log.i(TAG, "onChanged: Los doctores ya existen.");
                }
            }
        });
    }

    /**
     * Inicializa el usuario deseado
     * @param id
     */
    private void initializeUsuario(int id) {
        Log.i(TAG, "initializeUsuario: called i.");

        repositorio.getUnUsuario(id).observe(this, new Observer<Usuario>() {
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

    /**
     * Inizializa el doctor deseado
     * @param id
     */
    private void initializeDoctor(int id) {
        Log.i(TAG, "initializeDoctor: called i.");

        repositorio.getUnDoctor(id).observe(this, new Observer<Doctor>() {
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

//    private void aniadirProcedimientos(){
//        Log.i(TAG, "aniadirProcedimientos: called i.");
//
//        Usuario user1 = new Usuario();
//
//        Doctor d1 = new Doctor("Dr. One", "101", "General");
//
//        Procedimiento cita1 = new Procedimiento(
//                0,
//                user1.getId(),
//                d1.getId(),
//                LocalDate.of(2019, 8, 12),
//                LocalTime.of(8, 0),
//                d1.getEspecialidad());
//
//
//        repositorio.agregarProcedimiento(cita1);
//
//        checkDB();
//
//    }

    private void checkDB(){
        Log.i(TAG, "checkDB: called i.");
        Log.i(TAG, "checkDB: before a.size() = " + a.size());
        try {
            repositorio.getProcedimientos().observe(this, new Observer<List<Procedimiento>>() {
                @Override
                public void onChanged(@Nullable List<Procedimiento> procedimientos) {
                    Log.i(TAG, "onChanged: procedimientos.size() i. = " + procedimientos.size());
                    if (procedimientos.size() > 0) {
                        a.addAll(procedimientos);
                        Log.i(TAG, "onChanged: ");
                    }
                }
            });
        }catch (Exception e){
            Log.i(TAG, "checkDB: exception caught. " + e.toString());
        }
        Log.i(TAG, "checkDB: after a.size() = " + a.size());
    }

    /**
     * Encuentra una especialidad por id
     */
    private void getEspecialidad(int id){
        Log.i(TAG, "getEspecialidad: called i.");
        repositorio.getEspecialidadById(id).observe(this, new Observer<Especialidad>() {
            @Override
            public void onChanged(@Nullable Especialidad especialidad) {
                if(!especialidad.equals(null)) {
                    mEspecialidad = especialidad;
                }else{
                    Log.i(TAG, "onChanged: No existe la especialidad i.");
                }
            }
        });
    }

    private void setEspecialidades(){
        Log.i(TAG, "setEspecialidades: called i.");

    }

    /**
     * Sets the button listeners
     */
    public void setButtonListeners() {
        Log.i(TAG, "setButtonListeners: called i.");

        usuario1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                initializeUsuario(1);
                NavigationMenu.setmUsuario(mUsuario);
//                NavigationMenu.setmUsuario(new Usuario());
//                mUsuario = new Usuario();
//                mUsuario = NavigationMenu.getmUsuario();
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        Log.i(TAG, "setButtonListeners: passed usuario1");
        usuario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                initializeUsuario(2);
                NavigationMenu.setmUsuario(mUsuario);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        Log.i(TAG, "setButtonListeners: passed usuario2");

        usuario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 3 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                initializeUsuario(3);
                NavigationMenu.setmUsuario(mUsuario);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
        Log.i(TAG, "setButtonListeners: passed usuario3");

        doctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasDoctorActivity.class);
                //Change for corresponding user
                initializeDoctor(1);
                NavigationMenu.setmDoctor(mDoctor);
                intent.putExtra("selected_doctor", mDoctor);
                startActivity(intent);
            }
        });
        Log.i(TAG, "setButtonListeners: passed doctor1");

        doctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasDoctorActivity.class);
                //Change for corresponding user
                initializeDoctor(2);
                NavigationMenu.setmDoctor(mDoctor);
                intent.putExtra("selected_doctor", mDoctor);
                startActivity(intent);
            }
        });
        Log.i(TAG, "setButtonListeners: passed doctor2");

    }

}
