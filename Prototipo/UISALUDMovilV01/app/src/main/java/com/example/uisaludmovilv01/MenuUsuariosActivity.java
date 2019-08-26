package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uisaludmovilv01.modelos.Usuario;

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
    private Usuario usuario;

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
        usuario = new Usuario();

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


    public void setButtonListeners() {
        Log.i(TAG, "setButtonListeners: ");
        Intent intent;
        usuario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
        usuario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
        usuario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: usuaruio 3 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
        doctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 1 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
        doctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: doctor 2 clicked i.");
                Intent intent = new Intent(getApplicationContext(), ListaCitasActivity.class);
                //Change for corresponding user
                intent.putExtra("selected_usuario", usuario);
                startActivity(intent);
            }
        });
    }

}
