package com.example.androidenshugroup2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page_activity);

        //　商品購入に遷移
        Button mpiBtn = findViewById(R.id.mv_purchase_item_btn);
        mpiBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), PurchaseItemActivity.class);
            startActivity(intent);
        });

        /* 購入履歴に遷移
        Button mphBtn = findViewById(R.id.mv_purchase_history_btn);
        mphBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), .class);
            startActivity(intent);
        });
         */

        /* 設定に遷移
        Button msBtn = findViewById(R.id.mv_setting_btn);
        msBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), .class);
            startActivity(intent);
        });
         */

        /* 商品登録に遷移
        Button mriBtn = findViewById(R.id.mv_register_item_btn);
        mriBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getApplication(), .class);
            startActivity(intent);
        });
        */

        // ログアウトに遷移
        Button mlBtn = findViewById(R.id.mv_logout_btn);
        mlBtn.setOnClickListener(v -> finish());

    }
}