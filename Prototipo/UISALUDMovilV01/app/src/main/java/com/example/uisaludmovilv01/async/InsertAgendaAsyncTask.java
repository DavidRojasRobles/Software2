package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.persistencia.AgendaDao;

public class InsertAgendaAsyncTask extends AsyncTask<Agenda, Void, Void> {

    private static final String TAG = "InsertAgendaAsyncTask";
    private AgendaDao mAgendaDao;
    public InsertAgendaAsyncTask(AgendaDao dao) {
        mAgendaDao = dao;
    }

    @Override
    protected Void doInBackground(Agenda... agendas) {
        Log.d(TAG, "doinBackgrund: thread: "+ Thread.currentThread().getName());
        mAgendaDao.insertarAgenda(agendas);
        return null;
    }
}
