package com.example.uisaludmovilv01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.uisaludmovilv01.modelos.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "PerfilaActivity";

    //ui components
    private TextView perfil_nombre;
    private TextView perfil_cedula;
    private TextView perfil_telefono;
    private TextView perfil_direccion;

    //vars
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfil_nombre = findViewById(R.id.perfil_nombre);
        perfil_cedula = findViewById(R.id.perfil_cedula);
        perfil_telefono = findViewById(R.id.perfil_telefono);
        perfil_direccion = findViewById(R.id.perfil_direccion);

        Log.i(TAG, "onCreate: called i.");
        usuario = new Usuario();

        setPerfilProperties();
    }

    private void setPerfilProperties() {

        Log.i(TAG, "setPerfilProperties: called i.");

        perfil_nombre.setText(usuario.getNombre());
        perfil_cedula.setText(usuario.getCedula());
        perfil_telefono.setText(usuario.getTelefono());
        perfil_direccion.setText(usuario.getDireccion());

        Log.i(TAG, "setPerfilProperties: set all properties i.");

    }

}
