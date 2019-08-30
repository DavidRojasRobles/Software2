package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;

import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.persistencia.ProcedimientoDao;

public class InsertAsyncTask extends AsyncTask<Procedimiento, Void, Void> {

    private ProcedimientoDao mProcDao;

    public InsertAsyncTask(ProcedimientoDao dao){
        mProcDao = dao;
    }

    @Override
    protected Void doInBackground(Procedimiento... procedimientos) {
        mProcDao.insertarProcedimiento(procedimientos);
        return null;
    }
}
