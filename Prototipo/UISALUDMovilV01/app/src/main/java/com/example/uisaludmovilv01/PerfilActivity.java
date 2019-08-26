package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uisaludmovilv01.modelos.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "PerfilaActivity";

    //ui components
    private ImageButton perfil_back;
    private TextView perfil_nombre;
    private TextView perfil_cedula;
    private TextView perfil_telefono;
    private TextView perfil_direccion;
    private Button perfil_historia;

    //vars
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfil_back = findViewById(R.id.perfil_back);
        perfil_nombre = findViewById(R.id.perfil_nombre);
        perfil_cedula = findViewById(R.id.perfil_cedula);
        perfil_telefono = findViewById(R.id.perfil_telefono);
        perfil_direccion = findViewById(R.id.perfil_direccion);
        perfil_historia = findViewById(R.id.perfil_historia);

        Log.i(TAG, "onCreate: called i.");
        usuario = new Usuario();

        setPerfilListeners();

        setPerfilProperties();
    }

    private void setPerfilListeners() {
        perfil_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: perfil_back clicked i.");

                finish();

            }
        });

        perfil_historia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: perfil_historia clicked i.");
                Intent intent = new Intent(getApplicationContext(), HistoriaActivity.class);
                Log.i(TAG, "onHistoriaClick: intent created i.");
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
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
