package com.example.androidenshugroup2;

import android.content.Context;

import androidx.room.Room;

public class PurchaseDatabaseSingleton {
    private static PurchaseDatabase instance = null;

    public static PurchaseDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context,
                PurchaseDatabase.class, "purchase_database").build();
        return instance;
    }
}
