package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.persistencia.EvolucionDao;
import com.example.uisaludmovilv01.persistencia.HorarioDao;

public class InsertHorarioAsyncTask extends AsyncTask <Horario, Void, Void> {

    private static final String TAG = "InsertHorarioAsyncTask";    private HorarioDao mHorarioDao;
    public InsertHorarioAsyncTask(HorarioDao dao) { mHorarioDao = dao; }

    @Override
    protected Void doInBackground(Horario... horarios) {
        Log.d(TAG, "doinBackgrund: thread: "+ Thread.currentThread().getName());
        mHorarioDao.insertarHorario(horarios);
        return null;
    }
}
