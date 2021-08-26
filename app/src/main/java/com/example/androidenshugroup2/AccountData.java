package com.example.androidenshugroup2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_data")
public class AccountData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "password")
    private int password;

    // id = 0 にすれば自動でidが割り当てられる
    public AccountData(int id, String userName, int password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }
}
