package com.example.androidenshugroup2;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_name ASC")
    List<ItemData> loadItemsMatchingGenreOrderByItemNameASC(String genre);

    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_price ASC, item_name ASC")
    List<ItemData> loadItemsMatchingGenreOrderByItemPriceASC(String genre);

    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_price DESC, item_name ASC")
    List<ItemData> loadItemsMatchingGenreOrderByItemPriceDESC(String genre);
}
