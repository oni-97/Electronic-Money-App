package com.inoueken.androidenshugroup2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhuActivity extends AppCompatActivity {

    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.editText4)
    EditText editText4;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.activity_zhu)
    RelativeLayout activityZhu;
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//验证密码是否有特殊符号或长度不满6位
    private SQLiteDatabase sdb;
    private Mysqlist mys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        ButterKnife.bind(this);
        mys = new Mysqlist(this, "zhu_c", null, 1);//使用helper创建数据库
        sdb=mys.getWritableDatabase();
    }

    @OnClick({R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                String name = editText3.getText().toString().trim();
                String pass = editText4.getText().toString().trim();
                if (name == null || "".equals(name) || pass == null || "".equals(pass)) {
                    Toast.makeText(this, "Idとパスワードを入力してください", Toast.LENGTH_SHORT).show();
                } else{
                    String number = editText3.getText().toString();
                    boolean judge = isPassword(number);
                    String pa = editText4.getText().toString();
                    boolean judge1 = isPassword(pa);
                    if (CheckIsDataAlreadyInDBorNot(number)) {
                        Toast.makeText(this, "すでに登録されたユーザ名", Toast.LENGTH_SHORT).show();
                    }
                    else if (judge == true && judge1 == true) {
                        Toast.makeText(this, "新規登録成功！", Toast.LENGTH_SHORT).show();
                        sdb.execSQL("insert into user_mo(name,pass)values('"+name+"','"+pass+"')");
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);//启动跳转
                    } else {
                        Toast.makeText(this, "idとパスワードは5文字以下また特殊符号が含まれている", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.button4:
                editText3.setText("");
                editText4.setText("");
                finish();
                break;
        }
    }


    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db= mys.getWritableDatabase();
        String Query = "Select * from user_mo where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

}