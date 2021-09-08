package com.example.androidenshugroup2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.threeten.bp.LocalDateTime;

import java.lang.ref.WeakReference;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // データ受け取り
        Intent intent = getIntent();
        String userName = intent.getStringExtra(MainActivity.USER_NAME);

        // 戻るボタン
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(v -> finish());

        // room関連
        TextView tv = findViewById(R.id.history_text);
        Button thisMonthButton = findViewById(R.id.this_month_button);
        PurchaseDatabase db = PurchaseDatabaseSingleton.getInstance(getApplicationContext());

        thisMonthButton.setOnClickListener(new ThisMonthButtonClickListener(this, db, tv, userName));
    }

    private class ThisMonthButtonClickListener implements View.OnClickListener {
        private Activity activity;
        private PurchaseDatabase db;
        private TextView tv;
        private String userName;

        private ThisMonthButtonClickListener(Activity activity, PurchaseDatabase db, TextView tv, String userName) {
            this.activity = activity;
            this.db = db;
            this.tv = tv;
            this.userName = userName;
        }

        @Override
        public void onClick(View view) {
            new DataStoreAsyncTask(db, activity, tv, userName).execute();
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private PurchaseDatabase db;
        private TextView textView;
        private StringBuilder sb;
        private String userName;

        public DataStoreAsyncTask(PurchaseDatabase db, Activity activity, TextView textView, String userName) {
            this.db = db;
            this.userName = userName;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            PurchaseDao purchaseDao = db.purchaseDao();

            // 履歴出力
            sb = new StringBuilder();
            int sum = 0;
            List<PurchaseData> atList = purchaseDao.getAll();

            for (PurchaseData at: atList) {
                if((userName == at.getUserName()) && (at.getPurchaseDateTime().getMonth() == LocalDateTime.now().getMonth())){
                    sb.append(at.getPurchaseDateTime()).append("\t");
                    sb.append(at.getItemName()).append("\t");
                    sb.append("￥").append(at.getPurchasePrice()).append("\n");

                    sum += at.getPurchasePrice();
                }
            }

            sb.append("合計").append("￥").append(sum).append("\n");

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            textView.setText(sb.toString());
        }
    }
}
