package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.persistencia.OrdenDao;

public class InsertOrdenAsyncTask extends AsyncTask <Orden, Void, Void> {

    private static final String TAG = "InsertOrdenAsyncTask";
    private OrdenDao mOrdenDao;
    public InsertOrdenAsyncTask(OrdenDao dao) { mOrdenDao = dao; }

    @Override
    protected Void doInBackground(Orden... ordenes) {
        Log.d(TAG, "doinBackgrund: thread: "+ Thread.currentThread().getName());
        mOrdenDao.insertarOrden(ordenes);
        return null;
    }
}
