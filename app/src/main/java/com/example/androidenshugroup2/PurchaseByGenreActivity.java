package com.example.androidenshugroup2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PurchaseByGenreActivity extends AppCompatActivity {
    private String genre;
    private RecyclerView recyclerView;
    private String sortBy = "alpha";
    private ItemDatabaseHelper itemDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_by_genre_activity);

        //遷移元のアクティビティから「ジャンル (String型)」を受け取る。
        //タイトルをジャンルに応じて設定
        Intent intentMain = getIntent();
        genre = intentMain.getStringExtra(PurchaseItemActivity.EXTRA_MESSAGE);
        setTitle("商品購入 > " + genre);

        //「戻る」ボタン
        Button returnButton = findViewById(R.id.return_btn);
        returnButton.setOnClickListener(v -> finish());

        // 各種ソートボタン
        Button sortHigherPriceBtn = findViewById(R.id.sort_higher_price_btn);
        sortHigherPriceBtn.setOnClickListener(v-> {
            sortBy = "higher_price";
            itemDatabaseHelper.getDataAndUpdateRecyclerView(sortBy);
        });
        Button sortLowerPriceBtn = findViewById(R.id.sort_lower_price_btn);
        sortLowerPriceBtn.setOnClickListener(v-> {
            sortBy = "lower_price";
            itemDatabaseHelper.getDataAndUpdateRecyclerView(sortBy);
        });
        Button sortAlphaBtn = findViewById(R.id.sort_alpha_btn);
        sortAlphaBtn.setOnClickListener(v-> {
            sortBy = "alpha";
            itemDatabaseHelper.getDataAndUpdateRecyclerView(sortBy);
        });

        //RecyclerViewの設定
        recyclerView = findViewById(R.id.item_rview);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration deco = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(deco);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        itemDatabaseHelper = new ItemDatabaseHelper(getApplicationContext(), this);
        itemDatabaseHelper.getDataAndUpdateRecyclerView(sortBy);
    }

    public String getGenre() {
        return genre;
    }

    public void displayMessage(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();  //トーストを表示
    }

    public void updateItemRecyclerView(List<ItemData> itemDataList) {
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(itemDataList, this);
        recyclerView.setAdapter(adapter);
    }

    public void completePurchase() {
        Intent intent = new Intent();
        intent.putExtra(PurchaseItemActivity.EXTRA_MESSAGE, "purchase_complete");
        setResult(RESULT_OK, intent);
        this.finish();
    }
}