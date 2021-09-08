package com.example.androidenshugroup2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ItemEdit extends AppCompatActivity {
    public TextView itemNameTextView;
    public TextView itemPriceTextView;
    public TextView itemGenreTextView;
    public EditText itemNameText;
    public EditText itemPriceText;
    public Button buttonOK;
    public Button buttonBack;
    public ItemData itemData=null;
    public int id;
    public int position;
    public int itemCount;
    private final String[] spinnerItems = {"ジュース", "お菓子", "アイス", "カップ麺", "コーヒー", "その他"};
    private String itemName;
    private String itemPrice;
    private String genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        position = intent.getIntExtra("position",0);
        itemCount = intent.getIntExtra("ItemCount",0);
        itemNameTextView = findViewById(R.id.textView2);
        itemNameText =findViewById(R.id.itemNameText);
        itemPriceTextView=findViewById(R.id.textView3);
        itemPriceText =  findViewById(R.id.itemPriceText);
        itemGenreTextView = findViewById(R.id.textView4);
        Spinner genreSpinner = findViewById(R.id.genreSpinner);
        buttonBack = findViewById(R.id.buttonRegiBack2);
        buttonOK = findViewById(R.id.buttonNewRegi2);
        ItemDatabase db = ItemDatabaseSingleton.getInstance(getApplicationContext());
        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinner に adapter をセット
        genreSpinner.setAdapter(adapter);

        /*Textの初期値設定*/
        new ItemEdit.setItemData(db,id,itemNameText,itemPriceText,genreSpinner).execute();
        new ItemEdit.getItemData(this,db,id).execute();

        // リスナーを登録
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                genre = (String) spinner.getSelectedItem();
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        /*戻る*/
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*登録ボタン*/
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // エディットテキストのテキストを取得
                String productName = itemNameText.getText().toString();
                if (productName.isEmpty()) {/*商品名なし*/
                    proNameErrorPopUp();
                } else {
                    try {
                        int int_productPrice = Integer.parseInt(itemPriceText.getText().toString());
                        /*確認画面ポップアップ*/
                        checkPopUp(db,id,productName, int_productPrice, genre,position,itemCount);
                    } catch (NumberFormatException e) {/*価格に文字列が含まれる場合*/
                        proPriceErrorPopUp();
                    }
                }
            }
        });
    }

    /*非同期でDBから取得した商品名,価格をtextviewに設定*/
    private class setItemData extends AsyncTask<Void, Void, Void> {
        private ItemDatabase db;
        private ItemData itemData = null;
        private int id;
        private EditText itemNameText;
        private EditText itemPriceText;
        private Spinner genreSpinner;

        public setItemData(ItemDatabase db, int id,EditText itemNameText,EditText itemPriceText,Spinner genreSpinner) {
            this.db = db;
            this.id= id;
            this.itemNameText = itemNameText;
            this.itemPriceText = itemPriceText;
            this.genreSpinner = genreSpinner;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemData = db.itemDao().loadItemsMatchingId(id);
            return null;
        }

        protected void onPostExecute(Void a) {
            itemNameText.setText(itemData.getItemName());
            itemPriceText.setText(String.valueOf(itemData.getItemPrice()));
            genreSpinner.setSelection(getIndex(itemData));
        }
    }

    /*DBから非同期でitemData取得,キーはid*/
    public class getItemData extends AsyncTask<Integer, Integer, Integer> {
        private ItemDatabase db;
        private int id;
        private ItemData itemData;
        public getItemData(ItemEdit activity , ItemDatabase db,int id){
            this.db=db;
            this.id=id;
        }
        // 非同期処理
        @Override
        protected Integer doInBackground(Integer... params) {
            itemData = db.itemDao().loadItemsMatchingId(id);
            return null;
        }
        // 非同期処理が終了後、結果をメインスレッドに返す
        @Override
        protected void onPostExecute(Integer result) {
           getItem(itemData);
        }
    }

    public void getItem(ItemData itemData){
        this.itemData = itemData;
    }

    /*spinnerの位置を求める*/
    public int getIndex(ItemData itemData){
        for(int i=0;i<6;i++){
            if(spinnerItems[i].equals(itemData.getGenre())){
                return i;
            }
        }
        return 0;
    }
    /*変更内容の確認画面*/
    protected void checkPopUp(ItemDatabase db,int Id, String name, int price, String genre,int position,int itemCount) {
        new AlertDialog.Builder(ItemEdit.this)
                .setMessage("入力した内容でよろしいですか？\n"
                        + "商品名 :" + name + "\n" + "価格 :" + price
                        + "円" + "\n" + "ジャンル :" + genre + "\n")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*データベースを更新*/
                       ItemData data = new ItemData(Id,genre,name,price);
                       new ItemEdit.updataItem(db,data,position,itemCount).execute();
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
        new AlertDialog.Builder(ItemEdit.this)
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
        new AlertDialog.Builder(ItemEdit.this)
                .setMessage("正しく価格を入力してください")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*何もしない*/
                    }
                })
                .show();
    }

    /*商品内容変更の非同期処理*/
    private class updataItem extends AsyncTask<Integer, Integer, Integer>{
        private ItemDatabase db;
        private ItemData itemData;
        private int position;
        private int itemCount;
        public updataItem(ItemDatabase db, ItemData itemData,int position,int itemCount) {
            this.db = db;
            this.itemData = itemData;
            this.position = position;
            this.itemCount = itemCount;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            db.itemDao().update(itemData);
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            ItemEditView.getAdapter().notifyItemChanged(position,itemData);
            ItemEditView.getAdapter().notifyItemRangeChanged(position,itemCount);
            ItemEditView.getAdapter().notifyDataSetChanged();
            compPopUp();
        }
    }
    /*完了通知*/
    protected void compPopUp() {
        new AlertDialog.Builder(ItemEdit.this)
                .setMessage("商品内容を変更しました")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    }
                })
                .show();
    }
}