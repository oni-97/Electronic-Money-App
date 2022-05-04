package com.inoueken.androidenshugroup2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PurchaseDao {
    @Insert
    void insert(PurchaseData purchaseData);

    @Query("SELECT * FROM purchase_data")
    List<PurchaseData> getAll();
}
