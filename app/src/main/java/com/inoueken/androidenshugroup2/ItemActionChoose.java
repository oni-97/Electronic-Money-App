package com.inoueken.androidenshugroup2;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemActionChoose extends AppCompatActivity {
    public int id;
    public int position;
    public TextView itemNameText;
    public TextView itemPriceText;
    public Button buttonEdit;
    public Button buttonDelete;
    public Button buttonback;
    public ItemData itemData;
    public int itemCount;
    public ItemDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_action_choose);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        position = intent.getIntExtra("position",0);
        itemCount = intent.getIntExtra("ItemCount",0);
        itemNameText = findViewById(R.id.choosedItemNameTextView);
        itemPriceText=findViewById(R.id.choosedItemPriceTextView);
        buttonback=findViewById(R.id.buttonChooseActionBack);
        buttonDelete = findViewById(R.id.buttonDeleteItem);
        buttonEdit=findViewById(R.id.buttonEditItem);
        ItemDatabase db = ItemDatabaseSingleton.getInstance(getApplicationContext());
        new setItemData(db,id,itemNameText,itemPriceText).execute();
        new ItemActionChoose.getItemData(this,db,id).execute();

        /*戻る*/
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        /*商品削除*/
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCheckPopUp(db,itemData);
            }
        });
        /*商品編集*/
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEdit.class);
                intent.putExtra( "ID", id);
                intent.putExtra("position",position);
                intent.putExtra("ItemCount",itemCount);
                startActivityForResult(intent,100);
                new setItemData(db,id,itemNameText,itemPriceText).execute();
            }
        });
    }
    public void getItem(ItemData itemData){
        this.itemData = itemData;
    }


    /*非同期でDBから取得した商品名,価格をtextviewに設定*/
    private class setItemData extends AsyncTask<Void, Void, Void> {
        private ItemDatabase db;
        private ItemData itemData = null;
        private int id;
        private TextView itemNameText;
        private TextView itemPriceText;

        public setItemData(ItemDatabase db, int id,TextView itemNameText,TextView itemPriceText) {
            this.db = db;
            this.id= id;
            this.itemNameText = itemNameText;
            this.itemPriceText = itemPriceText;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemData = db.itemDao().loadItemsMatchingId(id);
            return null;
        }

        protected void onPostExecute(Void a) {
            itemNameText.setText(itemData.getItemName());
            itemPriceText.setText(String.valueOf(itemData.getItemPrice())+"円");
        }
    }

    /*deleteの確認画面*/
    protected void deleteCheckPopUp(ItemDatabase db,ItemData itemData) {/*確認画面ポップアップ*/
        new AlertDialog.Builder(ItemActionChoose.this)
                .setMessage("削除してもよろしいですか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*DBから削除*/
                        new deleteItem(db,itemData).execute();
                        /*ここでadapterも削除*/
                        ItemEditView.getAdapter().notifyItemRemoved(position);
                        ItemEditView.getAdapter().notifyItemChanged(position,itemData);
                        ItemEditView.getAdapter().notifyItemRangeChanged(position,itemCount);
                        deleteFinishPopUp();
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*何もしない*/
                    }
                })
                .show();
    }

    protected void deleteFinishPopUp(){
        new AlertDialog.Builder(ItemActionChoose.this)
                .setMessage("削除が完了しました")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    }
                })
                .show();
    }

    /*商品削除の非同期処理*/
    private class deleteItem extends AsyncTask<Void, Void, Void>{
        private ItemDatabase db;
        private ItemData itemData;
        public deleteItem(ItemDatabase db, ItemData itemData) {
            this.db = db;
            this.itemData = itemData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.itemDao().delete(itemData);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            ItemEditView.getAdapter().notifyItemRemoved(position);
        }
    }

    /*DBから非同期でitemData取得,キーはid*/
    public class getItemData extends AsyncTask<Integer, Integer, Integer> {
        private ItemDatabase db;
        private int id;
        private ItemData itemData;
        private ItemActionChoose activity;
        public getItemData(ItemActionChoose activity , ItemDatabase db,int id){
            this.db=db;
            this.id=id;
            this.activity = activity;
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
            activity.getItem(itemData);
        }
    }
    protected void onActivityResult(int requestCode,int resCode,Intent it) {
        super.onActivityResult(requestCode, resCode, it);
        new setItemData(ItemDatabaseSingleton.getInstance(getApplicationContext()),id,itemNameText,itemPriceText).execute();
    }

    public void setDB(ItemDatabase db){
        this.db = db;
    }

    }
