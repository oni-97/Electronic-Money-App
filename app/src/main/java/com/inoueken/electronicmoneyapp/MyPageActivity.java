package com.inoueken.electronicmoneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page_activity);

        //遷移元のアクティビティから「ジャンル (String型)」を受け取る。
        //タイトルをジャンルに応じて設定
        Intent intentMain = getIntent();
        userName = intentMain.getStringExtra(MainActivity.USER_NAME);

        //　商品購入に遷移
        Button mpiBtn = findViewById(R.id.mv_purchase_item_btn);
        mpiBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), PurchaseItemActivity.class);
            intent.putExtra(MainActivity.USER_NAME, userName);
            startActivity(intent);
        });

        // 購入履歴に遷移
        Button mphBtn = findViewById(R.id.mv_purchase_history_btn);
        mphBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), HistoryActivity.class);
            intent.putExtra(MainActivity.USER_NAME, userName);
            startActivity(intent);
        });

        // 商品登録に遷移
        Button mriBtn = findViewById(R.id.buttonCoffee);
        mriBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), RegisterPage.class);
            startActivity(intent);
        });

        // ログアウトに遷移
        Button mlBtn = findViewById(R.id.mv_logout_btn);
        mlBtn.setOnClickListener(v -> finish());

    }
}