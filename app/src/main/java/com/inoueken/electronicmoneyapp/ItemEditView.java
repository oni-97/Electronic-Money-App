package com.inoueken.electronicmoneyapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemEditView extends AppCompatActivity {
    private RecyclerView rv;
    private TextView genreTextView;
    private static ItemRecycleViewAdapter adapter; //staticでいいの？？
    private Button buttonItemEditBack;
    public String genre;
    public ItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit_view);

        Intent intent = getIntent();
        genre = intent.getStringExtra("genre");//static chenge
        db = ItemDatabaseSingleton.getInstance(getApplicationContext());//static chenge
        rv = (RecyclerView) findViewById(R.id.ItemEditRecyclerView);
        genreTextView = findViewById(R.id.genreTextView);
        genreTextView.setText(genre);
        buttonItemEditBack = findViewById(R.id.buttonItemEditBack);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);
        new ItemEditView.getDB(db, genre).execute();
        /*戻るボタン*/
        buttonItemEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    /*非同期でDBからlist取得*/
    private class getDB extends AsyncTask<Void, Void, Void> {
        private ItemDatabase db;
        private List<ItemData> list = null;
        String genre;

        public getDB(ItemDatabase db, String genre) {
            this.db = db;
            this.genre = genre;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list = db.itemDao().loadItemsMatchingGenreOrderByItemNameASC(genre);
            return null;
        }

        protected void onPostExecute(Void a) {
            updateDataView(list);
        }
    }

    public void updateDataView(List<ItemData> lid){
        ArrayList<ItemData> list= (ArrayList<ItemData>)lid;              //ArrayListへ変換
        ItemRecycleViewAdapter iAdapter;
        adapter = new ItemRecycleViewAdapter(list);//リサイクラーアダプターにデータを設定
        rv.setAdapter(adapter);                     //リサイクラービューにアダプターを設定
        adapter.notifyDataSetChanged();//リサイクラービューの表示を更新
        /*クリック処理*/
        adapter.setOnItemClickListener(new ItemRecycleViewAdapter.onItemClickListener()  {
            @Override
            public void onClick(View view, int id,int position) {
                Intent intent = new Intent(getApplication(), ItemActionChoose.class);
                intent.putExtra( "ID", id);
                intent.putExtra("position", position);  //インテントにタッチされたアイムのポジションを設定
                intent.putExtra("ItemCount",list.size());
                startActivityForResult(intent,100);
                //startActivity(intent)
            }

        });

    }
    public static ItemRecycleViewAdapter getAdapter(){
        return adapter;
    }

    public void onActivityResult( int requestCode, int resultCode, Intent intent ) {
        super.onActivityResult(requestCode, resultCode, intent);
        new ItemEditView.getDB(db, genre).execute();
    }

}