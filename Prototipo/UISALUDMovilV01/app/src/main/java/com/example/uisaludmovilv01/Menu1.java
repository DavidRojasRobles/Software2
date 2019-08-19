package com.example.uisaludmovilv01;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class Menu1 extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Menu1";

    LinearLayout menu_view, menu_empty, menu_btn_agendar, menu_btn_ordenes, menu_btn_perfil, menu_btn_citas;

    FloatingActionButton fb_menu;

    Boolean isMenuOpen = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        fb_menu = findViewById(R.id.fb_menu);
        menu_view = findViewById(R.id.menu_view);
        menu_empty = findViewById(R.id.menu_empty);
        menu_btn_agendar = findViewById(R.id.menu_btn_agendar);
        menu_btn_ordenes = findViewById(R.id.menu_btn_ordenes);
        menu_btn_perfil = findViewById(R.id.menu_btn_perfil);
        menu_btn_citas = findViewById(R.id.menu_btn_citas);
    }

    public Boolean getIsMenuOpen(){
        return isMenuOpen;
    }

    public void changeMenuState(){
        isMenuOpen = !isMenuOpen;
    }

    public void openMenu(){

        if (!getIsMenuOpen()){

            menu_view.setVisibility(View.VISIBLE);
            fb_menu.setAlpha(0f);
        }

        changeMenuState();
    }

    public void closeMenu(){

        if (getIsMenuOpen()){
            menu_view.setVisibility(View.GONE);
            fb_menu.setAlpha(1f);
        }

        changeMenuState();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){

            case R.id.fb_menu:
                Log.i(TAG, "onClick: fb_menu. Open menu i.");
                openMenu();
                break;

            case R.id.menu_empty:
                Log.i(TAG, "onClick: menu_empty. Close menu i.");
                closeMenu();
                break;

            case R.id.menu_btn_agendar:
                Log.i(TAG, "onClick: menu_btn_agendar i.");
                break;

            case R.id.menu_btn_ordenes:
                Log.i(TAG, "onClick: menu_btn_ordenes i.");
                intent = new Intent(this, ListaOrdenesActivity.class);
                startActivity(intent);
                closeMenu();
                break;

            case R.id.menu_btn_perfil:
                Log.i(TAG, "onClick: menu_btn_perfil i.");
                break;

            case R.id.menu_btn_citas:
                Log.i(TAG, "onClick: menu_btn_citas i.");
                intent = new Intent(this, ListaCitasActivity.class);
                startActivity(intent);
                closeMenu();
                break;
        }

    }

}
