package com.example.uisaludmovilv01;

import android.app.Application;
import android.content.res.Configuration;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyCustomApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
