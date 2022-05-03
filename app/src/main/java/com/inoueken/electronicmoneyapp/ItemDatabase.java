package com.inoueken.electronicmoneyapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ItemData.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
}

