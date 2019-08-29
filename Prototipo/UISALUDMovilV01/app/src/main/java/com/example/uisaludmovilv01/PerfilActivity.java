package com.example.uisaludmovilv01;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "PerfilaActivity";

    //ui components
    private ImageButton perfil_back;
    private TextView perfil_nombre;
    private TextView perfil_cedula;
    private TextView perfil_celular;
    private TextView perfil_direccion;
    private Button perfil_historia;

    //vars
    private Usuario mUsuario;
    //private ProjectRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfil_back = findViewById(R.id.perfil_back);
        perfil_nombre = findViewById(R.id.perfil_nombre);
        perfil_cedula = findViewById(R.id.perfil_cedula);
        perfil_celular = findViewById(R.id.perfil_celular);
        perfil_direccion = findViewById(R.id.perfil_direccion);
        perfil_historia = findViewById(R.id.perfil_historia);

        //repositorio = new ProjectRepositorio(this);

        Log.i(TAG, "onCreate: called i.");
        if (getIntent().hasExtra("selected_usuario")) {
            mUsuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");

            Log.i(TAG, "onCreate: has extra i.");
            Log.i(TAG, "onCreate: " + mUsuario.getNombre());
        }else{
            Log.i(TAG, "onCreate: no se recibió el usuario.");
            finish();
        }

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
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
            }
        });
    }

    private void setPerfilProperties() {

        Log.i(TAG, "setPerfilProperties: called i.");

        perfil_nombre.setText(mUsuario.getNombre());
        perfil_cedula.setText(mUsuario.getCedula());
        perfil_celular.setText(mUsuario.getCelular());
        perfil_direccion.setText(mUsuario.getDireccion());

        Log.i(TAG, "setPerfilProperties: set all properties i.");

    }

}
