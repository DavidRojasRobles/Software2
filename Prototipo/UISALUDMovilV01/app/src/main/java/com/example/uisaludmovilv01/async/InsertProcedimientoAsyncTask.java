package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.persistencia.ProcedimientoDao;

public class InsertProcedimientoAsyncTask extends AsyncTask<Procedimiento, Void, Void> {

    private static final String TAG = "InsertProcedimientoAsyn";
    private ProcedimientoDao mProcedimientoDao;
    public InsertProcedimientoAsyncTask(ProcedimientoDao dao) {
        mProcedimientoDao = dao;
    }

    @Override
    protected Void doInBackground(Procedimiento... procedimientos) {
        Log.d(TAG, "doinBackgrund: thread: "+ Thread.currentThread().getName());
        Log.i(TAG, "doInBackground: called i.");
        mProcedimientoDao.insertarProcedimiento(procedimientos);
        return null;
    }
}
