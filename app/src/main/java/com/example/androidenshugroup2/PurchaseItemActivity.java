package com.example.androidenshugroup2;

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
            = "com.example.androidenshugroup2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_item_activity);

        //「戻る」ボタン
        Button returnBtn = findViewById(R.id.return_btn);
        returnBtn.setOnClickListener(v -> finish());

        //「ジャンル」ボタン
        Button juiceBtn = findViewById(R.id.juice_btn);
        juiceBtn.setOnClickListener(v -> moveToClickedGenre("juice"));
        Button snackBtn = findViewById(R.id.snack_btn);
        snackBtn.setOnClickListener(v -> moveToClickedGenre("snack"));
        Button iceBtn = findViewById(R.id.ice_btn);
        iceBtn.setOnClickListener(v -> moveToClickedGenre("ice"));
        Button noodleBtn = findViewById(R.id.noodle_btn);
        noodleBtn.setOnClickListener(v -> moveToClickedGenre("noodle"));
        Button coffeeBtn = findViewById(R.id.mv_register_item_btn);
        coffeeBtn.setOnClickListener(v -> moveToClickedGenre("coffee"));
        Button othersBtn = findViewById(R.id.others_btn);
        othersBtn.setOnClickListener(v -> moveToClickedGenre("others"));
    }

    private void moveToClickedGenre(String genre) {
        //クリックされたジャンルのページに遷移
        //遷移先のアクティビティに「ジャンル (String型)」を渡す
        Intent intent = new Intent(getApplication(), PurchaseByGenreActivity.class);
        intent.putExtra(EXTRA_MESSAGE, genre);
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