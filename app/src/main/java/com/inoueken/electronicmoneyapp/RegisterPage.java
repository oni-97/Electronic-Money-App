package com.inoueken.electronicmoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterPage extends AppCompatActivity {
    private Button buttonJuice;
    private Button buttonSnack;
    private Button buttonIce;
    private Button buttonNoodle;
    private Button buttonCoffee;
    private Button buttonOther;
    private Button buttonRegiBack;
    private Button buttonNewRegi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        buttonJuice = findViewById(R.id.buttonJuice);
        buttonSnack = findViewById(R.id.buttonSnack);
        buttonIce = findViewById(R.id.buttonIce);
        buttonNoodle = findViewById(R.id.buttonNoodle);
        buttonCoffee = findViewById(R.id.buttonCoffee);
        buttonOther = findViewById(R.id.buttonOther);
        buttonRegiBack = findViewById(R.id.buttonRegiBack);
        buttonNewRegi = findViewById(R.id.buttonNewRegi);

        buttonJuice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "ジュース" );
                startActivity(intent);
            }
        });
        buttonSnack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "お菓子");
                startActivity(intent);
            }
        });
        buttonIce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "アイス" );
                startActivity(intent);
            }
        });
        buttonNoodle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "カップ麺");
                startActivity(intent);
            }
        });
        buttonCoffee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "コーヒー" );
                startActivity(intent);
            }
        });
        buttonOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ItemEditView.class);
                intent.putExtra( "genre", "その他" );
                startActivity(intent);
            }
        });

        buttonRegiBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        buttonNewRegi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegiNewItem.class);
                startActivity(intent);
            }
        });


    }
}