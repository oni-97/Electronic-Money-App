package com.example.androidenshugroup2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PurchaseDatabaseHelper extends AppCompatActivity {
    private PurchaseDao pDao;
    private PurchaseByGenreActivity pbgActivity;
    boolean isInsertDataSuccess = false;

    public PurchaseDatabaseHelper(Context context, PurchaseByGenreActivity activity) {
        PurchaseDatabase pDb = PurchaseDatabaseSingleton.getInstance(context);
        this.pDao = pDb.purchaseDao();
        this.pbgActivity = activity;
    }

    public void insertData(PurchaseData data) {
        isInsertDataSuccess = false;
        //データベース関連は非同期処理で行う
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pDao.insert(data);
                    isInsertDataSuccess = true;
                } catch (Exception e) {
                }
                ;
            }
        });

        //購入情報の登録が完了or失敗するのを待つ
        //結果がわかるまでダイアログ表示は待つ必要がある
        try {
            future.get();
        } catch (Exception e) {
        }

        if (!isInsertDataSuccess) {
            purchaseFailDialog(data);
        } else {
            purchaseSuccessDialog(data);
        }
    }

    public void purchaseSuccessDialog(PurchaseData data) {
        new AlertDialog.Builder(pbgActivity)
                .setTitle("購入完了")
                .setMessage(data.getItemName() + " " + data.getPurchasePrice() + " 円 "
                        + "の購入が完了しました。")
                .setPositiveButton("OK", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        pbgActivity.completePurchase();
                    }
                })
                .show();
    }

    public void purchaseFailDialog(PurchaseData data) {
        new AlertDialog.Builder(pbgActivity)
                .setTitle("購入失敗")
                .setMessage(data.getItemName() + " " + data.getPurchasePrice() + " 円"
                        + "の購入に失敗しました。もう一度お試しください。")
                .setPositiveButton("OK", null)
                .show();
    }

}
