package com.example.uisaludmovilv01.persistencia;

import android.arch.persistence.room.TypeConverter;
//
import org.threeten.bp.LocalDate;


public class LocalDateConverter {

    @TypeConverter
    public static LocalDate toLocalDate(String str) {
        if (str == null) {
            return null;
        } else {
            return LocalDate.parse(str);
        }
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate ld) {
        if (ld == null) {
            return null;
        } else {
            return ld.toString();
        }
    }

}
