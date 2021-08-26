package com.example.androidenshugroup2;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface PurchaseDao {
    @Insert
    void insert(PurchaseData purchaseData);
}
