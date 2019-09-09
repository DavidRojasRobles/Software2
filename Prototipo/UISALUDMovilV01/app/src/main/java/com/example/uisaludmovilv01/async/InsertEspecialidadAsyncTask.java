package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.persistencia.EspecialidadDao;

public class InsertEspecialidadAsyncTask extends AsyncTask<Especialidad, Void, Void> {

    private EspecialidadDao mEspecialidadDao;

    public InsertEspecialidadAsyncTask(EspecialidadDao dao) {
        mEspecialidadDao = dao;
    }

    @Override
    protected Void doInBackground(Especialidad... especialidades) {
        mEspecialidadDao.insertarEspecialidad(especialidades);
        return null;
    }
}
