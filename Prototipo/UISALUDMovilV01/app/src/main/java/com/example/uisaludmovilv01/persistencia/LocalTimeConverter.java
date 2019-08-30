package com.example.uisaludmovilv01.persistencia;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.LocalTime;

public class LocalTimeConverter {

    @TypeConverter
    public static LocalTime toLocalTime(String str) {
        if (str == null) {
            return null;
        } else {
            return LocalTime.parse(str);
        }
    }

    @TypeConverter
    public static String toString(LocalTime ld) {
        if (ld == null) {
            return null;
        } else {
            return ld.toString();
        }
    }
}
