package com.inoueken.electronicmoneyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.LocalDateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
        TextView coffeeTextView = findViewById(R.id.coffee_sum);
        TextView otherTextView = findViewById(R.id.others_sum);
        Button thisMonthButton = findViewById(R.id.this_month_button);
        Button lastMonthButton = findViewById(R.id.last_month_button);
        PurchaseDatabase dataBase = PurchaseDatabaseSingleton.getInstance(getApplicationContext());

        // RecycleView用
        RecyclerView recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        thisMonthButton.setOnClickListener(new ButtonClickListener(this, dataBase, recyclerView, coffeeTextView, otherTextView, userName));
        lastMonthButton.setOnClickListener(new ButtonClickListener(this, dataBase, recyclerView, coffeeTextView, otherTextView, userName));
    }

    private class ButtonClickListener implements View.OnClickListener {
        private Activity activity;
        private PurchaseDatabase dataBase;
        private RecyclerView recyclerView;
        private TextView coffeeTextView;
        private TextView otherTextView;
        private String userName;

        private ButtonClickListener(Activity activity, PurchaseDatabase dataBase, RecyclerView recyclerVeiw, TextView coffeTextView, TextView otherTextView, String userName) {
            this.activity = activity;
            this.dataBase = dataBase;
            this.recyclerView = recyclerVeiw;
            this.coffeeTextView = coffeTextView;
            this.otherTextView = otherTextView;
            this.userName = userName;
        }

        @Override
        public void onClick(View view) {
            if(view != null){
                switch (view.getId()){
                    case R.id.this_month_button:
                        new ThisMonthAsync(dataBase, activity, recyclerView, coffeeTextView, otherTextView, userName).execute();
                        break;

                    case R.id.last_month_button:
                        new LastMonthAsync(dataBase, activity, recyclerView, coffeeTextView, otherTextView, userName).execute();
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private static class ThisMonthAsync extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private PurchaseDatabase dataBase;
        private RecyclerView recyclerView;
        private TextView coffeeTextView;
        private TextView otherTextView;
        private String userName;

        private int sumOfCoffee;
        private int sumOfOthers;
        private List<PurchaseData> list = new ArrayList<>();

        public ThisMonthAsync(PurchaseDatabase db, Activity activity, RecyclerView recyclerView, TextView coffeeTextView, TextView otherTextView, String userName) {
            this.dataBase = db;
            this.userName = userName;
            weakActivity = new WeakReference<>(activity);
            this.recyclerView = recyclerView;
            this.coffeeTextView = coffeeTextView;
            this.otherTextView = otherTextView;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            PurchaseDao purchaseDao = dataBase.purchaseDao();

            // 履歴出力
            List<PurchaseData> atList = purchaseDao.getAll();

            for (PurchaseData at: atList) {
                if(userName.equals(at.getUserName())
                        && (at.getPurchaseDateTime().getMonth() == LocalDateTime.now().getMonth())
                        && (at.getPurchaseDateTime().getYear() == LocalDateTime.now().getYear())){
                    list.add(at);

                    if("コーヒー".equals(at.getItemGenre())){
                        sumOfCoffee += at.getPurchasePrice();
                    } else {
                        sumOfOthers += at.getPurchasePrice();
                    }
                }
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            recyclerView.setAdapter(new HistoryAdapter(list));
            coffeeTextView.setText("コーヒー\t合計\t" + sumOfCoffee + "\t円");
            otherTextView.setText("その他\t合計\t" + sumOfOthers + "\t円");
        }
    }

    private static class LastMonthAsync extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private PurchaseDatabase dataBase;
        private RecyclerView recyclerView;
        private TextView coffeeTextView;
        private TextView otherTextView;
        private String userName;

        private int sumOfCoffee;
        private int sumOfOthers;
        private List<PurchaseData> list = new ArrayList<>();

        public LastMonthAsync(PurchaseDatabase db, Activity activity, RecyclerView recyclerView, TextView coffeeTextView, TextView otherTextView, String userName) {
            this.dataBase = db;
            this.userName = userName;
            weakActivity = new WeakReference<>(activity);
            this.recyclerView = recyclerView;
            this.coffeeTextView = coffeeTextView;
            this.otherTextView = otherTextView;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            PurchaseDao purchaseDao = dataBase.purchaseDao();

            // 履歴出力
            List<PurchaseData> atList = purchaseDao.getAll();

            for (PurchaseData at: atList) {
                if(userName.equals(at.getUserName())
                        && (at.getPurchaseDateTime().getMonth() == LocalDateTime.now().minusMonths(1).getMonth())
                        && ((at.getPurchaseDateTime().getYear() == LocalDateTime.now().getYear())
                        || ((LocalDateTime.now().getMonth().getValue() == 1)
                        && (at.getPurchaseDateTime().getYear() == LocalDateTime.now().minusYears(1).getYear())))){
                    list.add(at);

                    if("コーヒー".equals(at.getItemGenre())){
                        sumOfCoffee += at.getPurchasePrice();
                    } else {
                        sumOfOthers += at.getPurchasePrice();
                    }
                }
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            recyclerView.setAdapter(new HistoryAdapter(list));
            coffeeTextView.setText("コーヒー\t合計\t" + sumOfCoffee + "\t円");
            otherTextView.setText("その他\t合計\t" + sumOfOthers + "\t円");
        }
    }

}
