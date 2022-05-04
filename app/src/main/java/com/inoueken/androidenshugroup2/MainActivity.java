package com.inoueken.androidenshugroup2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String USER_NAME
            = "com.example.electronicmoneyapp.USERNAME";

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//特殊パスワードけんさ
    private SQLiteDatabase w;
    private SQLiteDatabase r;
    private Mysqlist mys;
    private List<St> mdata;
    private String name;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mys = new Mysqlist(this, "zhu_c", null, 1);//使用helper创建数据库
        r=mys.getReadableDatabase();
        w=mys.getWritableDatabase();
        mdata=new ArrayList<St>();
        Cursor query = r.rawQuery("select * from user_mo", null);
        while(query.moveToNext()){
            int index1 = query.getColumnIndex("name");
            int index2 = query.getColumnIndex("pass");
            name = query.getString(index1);
            pass = query.getString(index2);
            mdata.add(new St(0, name, pass));
        }
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                String name1 = editText.getText().toString().trim();
                String pass1 = editText2.getText().toString().trim();
                if (login(name1,pass1)){
                    Toast.makeText(this,"ログインできました",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MyPageActivity.class);
                    intent.putExtra(USER_NAME,name);
                    startActivity(intent);
                    editText.setText("");
                    editText2.setText("");
                }else{
                    Toast.makeText(this,"入力されたIdまたはパスワードを認証できませんでした",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button2:
                Intent intent1 = new Intent(this, ZhuActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public boolean login(String username,String password){
        SQLiteDatabase db= mys.getWritableDatabase();
        String Query = "Select * from user_mo where name =? and pass =? ";
        Cursor cursor = db.rawQuery(Query,new String[] {username,password});
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
}