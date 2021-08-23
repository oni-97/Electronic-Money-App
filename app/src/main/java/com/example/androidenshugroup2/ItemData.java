package com.example.androidenshugroup2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_data")
public class ItemData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "item_price")
    private int itemPrice;

    // id = 0 にすれば自動でidが割り当てられる
    public ItemData(int id, String genre, String itemName, int itemPrice) {
        this.id = id;
        this.genre = genre;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }
}
