package com.inoueken.electronicmoneyapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccountData.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();
}

