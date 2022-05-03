package com.inoueken.electronicmoneyapp;

import android.content.Context;

import androidx.room.Room;

public class AccountDatabaseSingleton {
    private static AccountDatabase instance = null;

    public static AccountDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context,
                AccountDatabase.class, "account_database").build();
        return instance;
    }
}
