package com.example.uisaludmovilv01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

public class AgendarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
//    implements AdapterView.OnItemSelectedListener

    private static final String TAG = "AgendarActivity";

    private ArrayList<String> especialidades;
    private Spinner ag_esp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agendar);

        ag_esp = findViewById(R.id.ag_esp);
        populateEsp();
    }

    private void populateEsp(){
        TreeSet<String> esp = new TreeSet<>();

        esp.add("General");
        esp.add("Odontología");
        esp.add("Dermatología");
        esp.add("Ginecologia");
        esp.add("Cardiologia");

        especialidades = new ArrayList<String>(esp);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>(especialidades));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ag_esp.setAdapter(adapter);
        ag_esp.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: " +
                especialidades.get(position) ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "onNothingSelected: Spinner item not selected i.");
    }
}
