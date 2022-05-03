package com.inoueken.electronicmoneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PurchaseItemActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE
            = "com.example.electronicmoneyapp.MESSAGE";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_item_activity);

        //遷移元のアクティビティから「ジャンル (String型)」を受け取る。
        //タイトルをジャンルに応じて設定
        Intent intentMain = getIntent();
        userName = intentMain.getStringExtra(MainActivity.USER_NAME);

        //「戻る」ボタン
        Button returnBtn = findViewById(R.id.buttonRegiBack);
        returnBtn.setOnClickListener(v -> finish());

        //「ジャンル」ボタン
        Button juiceBtn = findViewById(R.id.buttonJuice);
        juiceBtn.setOnClickListener(v -> moveToClickedGenre("ジュース"));
        Button snackBtn = findViewById(R.id.buttonSnack);
        snackBtn.setOnClickListener(v -> moveToClickedGenre("お菓子"));
        Button iceBtn = findViewById(R.id.buttonIce);
        iceBtn.setOnClickListener(v -> moveToClickedGenre("アイス"));
        Button noodleBtn = findViewById(R.id.buttonNoodle);
        noodleBtn.setOnClickListener(v -> moveToClickedGenre("カップ麺"));
        Button coffeeBtn = findViewById(R.id.buttonCoffee);
        coffeeBtn.setOnClickListener(v -> moveToClickedGenre("コーヒー"));
        Button othersBtn = findViewById(R.id.buttonOther);
        othersBtn.setOnClickListener(v -> moveToClickedGenre("その他"));
    }

    private void moveToClickedGenre(String genre) {
        //クリックされたジャンルのページに遷移
        //遷移先のアクティビティに「ジャンル (String型)」を渡す
        Intent intent = new Intent(getApplication(), PurchaseByGenreActivity.class);
        intent.putExtra(EXTRA_MESSAGE, genre);
        intent.putExtra(MainActivity.USER_NAME, userName);
        mStartForResult.launch(intent);
    }

    private ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == PurchaseItemActivity.RESULT_OK) {
                        Intent intent = result.getData();
                        String message = intent.getStringExtra(PurchaseItemActivity.EXTRA_MESSAGE);
                        if (message.equals("purchase_complete")) {
                            finish();
                        }
                    }
                }
            });

}