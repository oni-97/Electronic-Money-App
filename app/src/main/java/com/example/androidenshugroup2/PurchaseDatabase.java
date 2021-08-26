package com.example.androidenshugroup2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PurchaseData.class}, version = 1, exportSchema = false)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class PurchaseDatabase extends RoomDatabase {
    public abstract PurchaseDao purchaseDao();
}

