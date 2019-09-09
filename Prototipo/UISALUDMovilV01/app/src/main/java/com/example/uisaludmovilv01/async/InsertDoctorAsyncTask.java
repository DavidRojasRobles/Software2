package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.persistencia.DoctorDao;

public class InsertDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {

    private static final String TAG = "InsertDoctorAsyncTask";
    private DoctorDao mDoctorDao;

    public InsertDoctorAsyncTask(DoctorDao dao) {
        mDoctorDao = dao;
    }

    @Override
    protected Void doInBackground(Doctor... doctores) {
        Log.d(TAG, "doinBackgrund: thread: " + Thread.currentThread().getName());
        mDoctorDao.insertarDoctor(doctores);
        return null;
    }
}
