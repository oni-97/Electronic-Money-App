package com.example.androidenshugroup2;

import android.content.Context;

import androidx.room.Room;

public class ItemDatabaseSingleton {
    private static ItemDatabase instance = null;

    public static ItemDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context,
                ItemDatabase.class, "item_database").build();
        return instance;
    }
}
