package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Usuario;

public class NavigationMenu extends AppCompatActivity {
    private static final String TAG = "NavigationMenu";
    public static Usuario mUsuario;
    public static Doctor mDoctor;

    public static Usuario getmUsuario() {
        return mUsuario;
    }

    public static void setmUsuario(Usuario usuario) {
        NavigationMenu.mUsuario = usuario;
    }

    public static Doctor getmDoctor() {
        return mDoctor;
    }

    public static void setmDoctor(Doctor mDoctor) {
        NavigationMenu.mDoctor = mDoctor;
    }

    //
//    LinearLayout menu_view, menu_empty, menu_btn_agendar, menu_btn_ordenes, menu_btn_perfil, menu_btn_citas;
//
//    FloatingActionButton fb_menu;
//
//    Boolean isMenuOpen = false;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_menu);
//
//        fb_menu = findViewById(R.id.fb_menu);
//        menu_view = findViewById(R.id.menu_view);
//        menu_empty = findViewById(R.id.menu_empty);
//        menu_btn_agendar = findViewById(R.id.menu_btn_agendar);
//        menu_btn_ordenes = findViewById(R.id.menu_btn_ordenes);
//        menu_btn_perfil = findViewById(R.id.menu_btn_perfil);
//        menu_btn_citas = findViewById(R.id.menu_btn_citas);
//    }
//
//    public Boolean getIsMenuOpen(){
//        return isMenuOpen;
//    }
//
//    public void changeMenuState(){
//        isMenuOpen = !isMenuOpen;
//    }
//
//    public void openMenu(){
//
//        if (!getIsMenuOpen()){
//
//            menu_view.setVisibility(View.VISIBLE);
//            fb_menu.setAlpha(0f);
//        }
//
//        changeMenuState();
//    }
//
//    public void closeMenu(){
//
//        if (getIsMenuOpen()){
//            menu_view.setVisibility(View.GONE);
//            fb_menu.setAlpha(1f);
//        }
//
//        changeMenuState();
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent;
//
//        switch (v.getId()){
//
//            case R.id.fb_menu:
//                Log.i(TAG, "onClick: fb_menu. Open menu i.");
//                openMenu();
//                break;
//
//            case R.id.menu_empty:
//                Log.i(TAG, "onClick: menu_empty. Close menu i.");
//                closeMenu();
//                break;
//
//            case R.id.menu_btn_agendar:
//                Log.i(TAG, "onClick: menu_btn_agendar i.");
//                break;
//
//            case R.id.menu_btn_ordenes:
//                Log.i(TAG, "onClick: menu_btn_ordenes i.");
//                intent = new Intent(this, ListaOrdenesActivity.class);
//                startActivity(intent);
//                closeMenu();
//                break;
//
//            case R.id.menu_btn_perfil:
//                Log.i(TAG, "onClick: menu_btn_perfil i.");
//                break;
//
//            case R.id.menu_btn_citas:
//                Log.i(TAG, "onClick: menu_btn_citas i.");
//                intent = new Intent(this, ListaCitasActivity.class);
//                startActivity(intent);
//                closeMenu();
//                break;
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_usuario:
                Log.i(TAG, "onClick: menu_btn_agendar i.");
                intent = new Intent(this, MenuUsuariosActivity.class);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
                return true;

            case R.id.menu_ordenes:
                Log.i(TAG, "onClick: menu_btn_ordenes i.");
                intent = new Intent(this, ListaOrdenesActivity.class);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
                return true;

            case R.id.menu_perfil:
                Log.i(TAG, "onClick: menu_btn_perfil i.");
                intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
                return true;

            case R.id.menu_citas:
                Log.i(TAG, "onClick: menu_btn_citas i.");
                intent = new Intent(this, ListaCitasActivity.class);
                intent.putExtra("selected_usuario", mUsuario);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
