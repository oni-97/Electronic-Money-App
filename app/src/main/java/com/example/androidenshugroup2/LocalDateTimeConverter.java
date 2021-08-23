package com.example.androidenshugroup2;

import androidx.room.TypeConverter;

import org.threeten.bp.LocalDateTime;

public class LocalDateTimeConverter {
    @TypeConverter
    public static LocalDateTime toLocalDateTime(String value) {
        if (value == null) {
            return null;
        } else {
            return LocalDateTime.parse(value);
        }
    }

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }
}
