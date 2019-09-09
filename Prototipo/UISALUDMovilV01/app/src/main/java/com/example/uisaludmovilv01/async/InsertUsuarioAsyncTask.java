package com.example.uisaludmovilv01.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uisaludmovilv01.modelos.Usuario;
import com.example.uisaludmovilv01.persistencia.UsuarioDao;

public class InsertUsuarioAsyncTask extends AsyncTask<Usuario, Void, Void> {

    private static final String TAG = "InsertUsuarioAsyncTask";
    private UsuarioDao mUsuarioDao;

    public InsertUsuarioAsyncTask(UsuarioDao dao) {
        mUsuarioDao = dao;
    }

    @Override
    protected Void doInBackground(Usuario... usuarios) {
        Log.d(TAG, "doinBackgrund: thread: " + Thread.currentThread().getName());
        mUsuarioDao.insertarUsuario(usuarios);
        return null;
    }
}
