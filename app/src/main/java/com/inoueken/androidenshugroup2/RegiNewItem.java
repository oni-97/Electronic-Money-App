package com.inoueken.androidenshugroup2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegiNewItem extends AppCompatActivity {
    private final String[] spinnerItems = {"ジュース", "お菓子", "アイス", "カップ麺", "コーヒー", "その他"};
    private Button buttonRegiBack2;
    private Button buttonNewRegi2;
    private EditText proNameText;
    private EditText proPriceText;
    private String genreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_new_item);
        buttonRegiBack2 = findViewById(R.id.buttonRegiBack2);
        buttonNewRegi2 = findViewById(R.id.buttonNewRegi2);
        proNameText = findViewById(R.id.itemNameText);
        proPriceText = findViewById(R.id.itemPriceText);
        Spinner genreSpinner = findViewById(R.id.genreSpinner);
        ItemDatabase db = ItemDatabaseSingleton.getInstance(getApplicationContext());

        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner に adapter をセット
        genreSpinner.setAdapter(adapter);

        // リスナーを登録
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String genre = (String) spinner.getSelectedItem();
                genreName = genre;
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        /*戻るボタン*/
        buttonRegiBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*登録ボタン*/
        buttonNewRegi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // エディットテキストのテキストを取得
                String productName = proNameText.getText().toString();
                if (productName.isEmpty()) {/*商品名なし*/
                    proNameErrorPopUp();
                } else {
                    try {
                        int int_productPrice = Integer.parseInt(proPriceText.getText().toString());
                        /*確認画面ポップアップ*/
                        checkPopUp(db,productName, int_productPrice, genreName);
                    } catch (NumberFormatException e) {/*価格に文字列が含まれる場合*/
                        proPriceErrorPopUp();
                    }
                }
            }
        });
    }

    /*DBへ非同期アクセス/登録*/
    private class registerDB extends AsyncTask<Void, Void, Void> {
        private ItemDatabase db;
        private ItemData itemdata;
        public registerDB(ItemDatabase db,ItemData itemdata) {
            this.db=db;
            this.itemdata = itemdata;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.itemDao().insert(itemdata);
            return null;
        }
        protected void onPostExecute(Void a){
            new AlertDialog.Builder(RegiNewItem.this)
                    .setMessage("新しい商品を登録しました")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    protected void checkPopUp(ItemDatabase db, String name, int price, String genre) {/*確認画面ポップアップ*/
        new AlertDialog.Builder(RegiNewItem.this)
                .setMessage("入力した内容でよろしいですか？\n"
                        + "商品名 :" + name + "\n" + "価格 :" + price
                        + "円" + "\n" + "ジャンル :" + genre + "\n")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*データベースに商品情報入力*/
                        ItemData itemdata = new ItemData(0,genre,name,price);
                        new registerDB(db,itemdata).execute();
                    }
                })
                .setNegativeButton("戻る", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*何もしない*/
                    }
                })
                .show();
    }


    protected void proNameErrorPopUp() {
        new AlertDialog.Builder(RegiNewItem.this)
                .setMessage("商品名を入力してください")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*何もしない*/
                    }
                })
                .show();
    }

    protected void proPriceErrorPopUp() {
        new AlertDialog.Builder(RegiNewItem.this)
                .setMessage("正しく価格を入力してください")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*何もしない*/
                    }
                })
                .show();
    }


}