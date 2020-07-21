package com.example.uisaludmovilv01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.ProjectRepositorio;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AgendarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//    implements AdapterView.OnItemSelectedListener

    private static final String TAG = "AgendarActivity";

    //Fake data
    private static TreeSet<String> treeEspecialidades = new TreeSet<>();
    private ArrayList<Doctor> doctores = new ArrayList<>();

    //Array Spinners
    private ArrayList<Especialidad> especialidades;
    private ArrayList<String> nombreEsp;
    private ArrayList<Doctor> filtroDoctores = new ArrayList<>();

    private ArrayList<Horario> mHorarios = new ArrayList<>();
    private ProjectRepositorio repositorio;

    //UI elements
    private TextView ag_title;
    private TextView ag_subtitle;
    private Dialog prompt;
    private TextView pd_title, pd_content;
    private Button pd_cancel_btn, pd_accept_btn;
    private ImageButton ag_back;
    private Spinner ag_esp;
    private Spinner ag_doctor;
    private Spinner ag_hora;
    private TextView ag_fecha;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button ag_agendar;

    //Variables

    private Usuario mUsuario;
    private Doctor mDoctor;
    private String mEspecialidad;
    private LocalDate mFecha;
    private LocalTime mHora;
    private boolean horarioDia;
    private boolean agendaDia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agendar);

        prompt = new Dialog(this);
        prompt.setContentView(R.layout.layout_dialog);
        prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        repositorio = new ProjectRepositorio(this);

        ag_title = findViewById(R.id.single_elem_title);
        ag_subtitle = findViewById(R.id.single_elem_subtitle);

        pd_title = prompt.findViewById(R.id.pd_title);
        pd_content = prompt.findViewById(R.id.pd_content);
        pd_cancel_btn = prompt.findViewById(R.id.pd_cancel_btn);
        pd_accept_btn = prompt.findViewById(R.id.pd_accept_btn);

        ag_back = findViewById(R.id.set_back_button);
        ag_esp = findViewById(R.id.ag_esp);
        ag_doctor = findViewById(R.id.ag_doctor);
        ag_fecha = findViewById(R.id.ag_fecha);
        ag_hora = findViewById(R.id.ag_hora);
        ag_agendar = findViewById(R.id.ag_agendar);

        ag_title.setText("Agendar");
        ag_subtitle.setText("");

        //initializeFakeData();
        Log.i(TAG, "onCreate: Se llenaron los doctores i.");

//        if (getIntent().hasExtra("selected_usuario")) {
//            mUsuario = (Usuario) getIntent().getSerializableExtra("selected_usuario");
//
//            Log.i(TAG, "onCreate: has extra i.");
//            Log.i(TAG, "onCreate: " + mUsuario.toString());
//        }

        mUsuario = NavigationMenu.getmUsuario();

        populateEsp();
        setListeners();
        Log.i(TAG, "onCreate: Listeners set i.");

//        selectItems();


    }


    public void setListeners() {

        ag_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ag_back clicked i.");

                finish();

            }
        });

        ag_esp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG, "onItemSelected: Selected from ag_esp. Position " + position + " i.");

                mEspecialidad = ag_esp.getSelectedItem().toString();

                Log.i(TAG, "onItemSelected: Especialidad seleccionada " + mEspecialidad + " i.");

                populateDoctores(mEspecialidad);

                Log.i(TAG, "onItemSelected: Doctores populates i.");
                Log.i(TAG, "onItemSelected: ag_doctor.size() = " + ag_doctor.getCount() + " i.");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ag_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mDoctor = findDoctor(ag_doctor.getSelectedItem().toString());

                Log.i(TAG, "onItemSelected: Doctor seleccionado " + mDoctor.getNombre() + " i.");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ag_fecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ag_fecha clicked i.");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mFecha = LocalDate.now();
                }
                datePickerDialog = new DatePickerDialog(AgendarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                ag_fecha.setText(day + " / " + (month + 1) + " / " + year);

                                //colocar restricciones

                                mFecha = LocalDate.of(year, month, day);
                                setmHorarios(mFecha);
                                setmAgendas(mFecha);

                                if (horarioDia || agendaDia) {
                                    populateHoras();
                                } else {
                                    Toast.makeText(getApplicationContext(), "El doctor no trabaja los "
                                            + mFecha.getDayOfWeek().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, mFecha.getYear(), mFecha.getMonthValue(), mFecha.getDayOfMonth());
                datePickerDialog.show();
            }
        });

        ag_hora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG, "onItemSelected: Selected from ag_hora. Position " + position + " i.");

                mHora = LocalTime.parse(ag_hora.getSelectedItem().toString());

                Log.i(TAG, "onItemSelected: hora seleccionada " + mHora + " i.");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ag_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ag_agendar clicked i.");

                setPromptDialog();

                prompt.show();


            }
        });
    }

    private void setPromptDialog() {

        Procedimiento cita = new Procedimiento(0, mUsuario.getId(), mDoctor.getId(),
                mFecha, mHora);

        pd_title.setText("¿AGENDAR ESTA CITA?");

        String datos = "Especialidad: " + mDoctor.getEspecialidad() + "\n"
                + "Fecha: " + mFecha + "\n"
                + "Hora: " + mHora + "\n"
                + "Consultorio: " + mDoctor.getConsultorio() + "\n"
                + "Doctor: " + mDoctor.getNombre() + "\n\n";

        pd_content.setText(datos);

        pd_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.hide();
            }
        });
        pd_accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: cita agendada i.");

                //Guardar cita
                Toast.makeText(getApplicationContext(), "Cita agendada",
                        Toast.LENGTH_SHORT).show();
                prompt.hide();
                finish();
            }
        });


    }

    private void populateEsp() {

        Log.i(TAG, "populateEsp: called i.");

        insertarEspecialidades();
        setNombreEsp();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, new ArrayList<String>(nombreEsp));
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        ag_esp.setAdapter(adapter);


    }

    private void populateHoras() {

        Log.i(TAG, "populateHoras: called i.");
        ArrayList<String> horasDisponibles = new ArrayList<>();

        for (Horario h : mHorarios) {
            horasDisponibles.add(h.getHora());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, new ArrayList<String>(nombreEsp));
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        ag_esp.setAdapter(adapter);


    }

    private void setNombreEsp() {
        if (nombreEsp.size() > 0)
            nombreEsp.clear();
        for (Especialidad esp : especialidades) {
            nombreEsp.add(esp.getEspNombre());
        }
    }

    private void insertarEspecialidades() {
        try {
            repositorio.getEspecialidades().observe(this, new Observer<List<Especialidad>>() {
                @Override
                public void onChanged(@Nullable List<Especialidad> esp) {
                    if (especialidades.size() > 0)
                        especialidades.clear();
                    if (esp != null) {
                        especialidades.addAll(esp);
                        Log.i(TAG, "onChanged: especialidades.size() = " + especialidades.size());
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No existen especialidades", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "insertarEsp: No hay especialidades");
            Toast.makeText(getApplicationContext(),
                    "No hay especialidades", Toast.LENGTH_SHORT).show();
            finish();
            Log.i(TAG, "insertarEsp: " + e.toString());
        }
    }

    private void populateDoctores(String e) {

        Log.i(TAG, "populateDoctores: called i.");

        filtroDoctores = filtrarEsp(e);
        ArrayList<String> nombresDoctores = new ArrayList<>();

        for (Doctor d : filtroDoctores) {
            nombresDoctores.add(d.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, new ArrayList<String>(nombresDoctores));
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        ag_doctor.setAdapter(adapter);

    }

//    private void initializeFakeData() {
//
//        Log.i(TAG, "llenarArrayDoctores: called i.");
//
//        mUsuario = new Usuario();
//
//        Doctor d1 = new Doctor("Dr. One", "101", "General");
//        d1.anadirDia("MONDAY", new boolean[]{true, true, false, false, false,
//                false, false, false, false, false, false});
//        d1.anadirDia("THURSDAY", new boolean[]{true, true, true, false, false,
//                false, false, false, true, true, false});
//
//
//        doctores.add(d1);
//
//        Doctor d2 = new Doctor("Dr. Two", "102", "Cardiologia");
//        d2.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        d2.anadirDia("WEDNESDAY", new boolean[]{true, true, true, true, true,
//                false, false, false, false, false, false});
//        doctores.add(d2);
//
//        Doctor d3 = new Doctor("Dr. Three", "103", "Odontologia");
//        d3.anadirDia("MONDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        d3.anadirDia("WEDNESDAY", new boolean[]{true, true, true, true, true,
//                false, false, false, false, false, false});
//        d3.anadirDia("FRIDAY", new boolean[]{true, true, true, true, true,
//                false, false, false, false, false, false});
//        doctores.add(d3);
//
//        Doctor d4 = new Doctor("Dr. Four", "104", "Ginecologia");
//        d4.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        d4.anadirDia("THURSDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        doctores.add(d4);
//
//        Doctor d5 = new Doctor("Dr. Five", "104", "General");
//        d5.anadirDia("WEDNESDAY", new boolean[]{true, true, true, false, false,
//                false, false, false, true, true, false});
//        d5.anadirDia("THURSDAY", new boolean[]{true, true, true, false, false,
//                false, false, false, true, true, false});
//        doctores.add(d5);
//
//        Doctor d6 = new Doctor("Dr. Six", "104", "Ginecologia");
//        d6.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        d6.anadirDia("THURSDAY", new boolean[]{false, false, false, false, false,
//                false, true, true, true, true, true});
//        doctores.add(d6);
//
//        for (int i = 0; i < doctores.size(); i++) {
//            treeEspecialidades.add(doctores.get(i).getEspecialidad());
//        }
//
//    }

    private ArrayList<Doctor> filtrarEsp(String esp) {

        Log.i(TAG, "filtrarEsp: called i.");

        final ArrayList<Doctor> docEsp = new ArrayList<>();
        docEsp.clear();

        try {
            repositorio.getDoctoresEsp(esp).observe(this, new Observer<List<Doctor>>() {
                @Override
                public void onChanged(@Nullable List<Doctor> doctores) {
                    if (docEsp.size() > 0)
                        docEsp.clear();
                    if (doctores != null) {
                        docEsp.addAll(doctores);
                        Log.i(TAG, "onChanged: docEsp.size() = " + docEsp.size());
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No existen doctores para esta especialidad", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception e) {
            Log.i(TAG, "getDoctoresEsp: No hay doctores");
            Toast.makeText(getApplicationContext(),
                    "No hay especialidades", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "getDoctoresEsp: " + e.toString());
        }

        return docEsp;
    }

    private Doctor findDoctor(String esp) {

        Log.i(TAG, "findDoctor: called i.");

        Doctor doc = null;

        try {
            for (Doctor d : filtroDoctores) {
                if (d.getEspecialidad().equals(esp)) return d;
                Log.i(TAG, "findDoctor: doctor found i.");
            }
        } catch (Exception e) {
            Log.i(TAG, "findDoctor: doctor not found i.");
        }

        return doc;
    }

    private void setmHorarios(LocalDate fecha) {

        try {
            repositorio.getHorarioByDia(mDoctor.getId(), fecha.toString()).observe(this, new Observer<List<Horario>>() {
                @Override
                public void onChanged(@Nullable List<Horario> horarios) {
                    if (mHorarios.size() > 0) {
                        mHorarios.clear();
                    }
                    if (horarios != null) {
                        mHorarios.addAll(horarios);
                    }

                    horarioDia = (horarios.size() > 0) ? true : false;
                    Log.i(TAG, "onChanged: horarios.size() = " + horarios.size());

                }
            });
        } catch (Exception e) {
            Log.i(TAG, "verificarDia: No hay doctores");
            Toast.makeText(getApplicationContext(),
                    "No hay especialidades", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "verificarDia: " + e.toString());
        }
    }

    private void setmAgendas(LocalDate fecha) {


        try {
            repositorio.getAgendaByFecha(mDoctor.getId(), fecha.toString()).observe(this, new Observer<List<Agenda>>() {
                @Override
                public void onChanged(@Nullable List<Agenda> agendas) {

                    agendaDia = (agendas.size() > 0) ? true : false;
                    Log.i(TAG, "onChanged: horarios.size() = " + agendas.size());

                }
            });
        } catch (Exception e) {
            Log.i(TAG, "verificarDia: No hay doctores");
            Toast.makeText(getApplicationContext(),
                    "No hay especialidades", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "verificarDia: " + e.toString());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: " +
                especialidades.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "onNothingSelected: Spinner item not selected i.");
    }


}
