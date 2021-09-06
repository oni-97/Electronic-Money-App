package com.example.androidenshugroup2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_name ASC")
    List<ItemData> loadItemsMatchingGenreOrderByItemNameASC(String genre);

    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_name DESC")
    List<ItemData> loadItemsMatchingGenreOrderByItemNameDESC(String genre);

    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_price ASC")
    List<ItemData> loadItemsMatchingGenreOrderByItemPriceASC(String genre);

    @Query("SELECT * FROM item_data WHERE genre = :genre ORDER BY item_price DESC")
    List<ItemData> loadItemsMatchingGenreOrderByItemPriceDESC(String genre);

    @Query("SELECT * FROM item_data WHERE id = :id")
    ItemData loadItemsMatchingId(int id);


    /*ここから変更したよ*/
    @Insert
    void insert(ItemData itemData);
    @Update
    void update(ItemData itemData);
    @Delete
    void delete(ItemData itemData);
}
