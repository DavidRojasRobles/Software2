package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.persistencia.EvolucionDao;

public class InsertEvolucionAsyncTask extends AsyncTask <Evolucion, Void, Void> {

    private static final String TAG = "InsertAsyncTask";
    private EvolucionDao mEvolucionDao;
    public InsertEvolucionAsyncTask(EvolucionDao dao) { mEvolucionDao = dao; }

    @Override
    protected Void doInBackground(Evolucion... evoluciones) {
        Log.d(TAG, "doinBackgrund: thread: "+ Thread.currentThread().getName());
        mEvolucionDao.insertarEvolucion(evoluciones);
        return null;
    }
}
