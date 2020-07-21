package com.example.test_fronttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class fronttest extends AppCompatActivity {

    SQLiteDatabase db;
    TextView tex;
    int i=0;
    int Q[];//控制哪幾題被選到

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronttest);

        tex = findViewById(R.id.textView);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Cursor c = db.rawQuery("SELECT * FROM Question ", null);
        if(c.getCount()>0) {
          c.moveToFirst();
          int j=c.getCount();
          Q=new int [j];
          for (int k=0;k<5;k++){
              //隨機產生五個數字，用來抓題目
              //題目id是1 2 3 4...
              int x=0;
              while (x==0){
                  x=(int)(Math.random()*j+1);
                  for (int b=0;b<k;b++){
                      if (Q[b]==x){
                          x=0;
                      }
                  }
                  Q[k]=x;
              }
          }
          c.close();
          String s1=String.valueOf(Q[0]);
          c = db.rawQuery("SELECT * FROM Question WHERE question_id='"+s1+"'", null);
            if(c.getCount()>0) {
                c.moveToFirst();
                String s = "1"+c.getString(1) + "\n" + c.getString(3) + "\n" + c.getString(4) + "\n" + c.getString(5) + "\n" + c.getString(6) + "\n";
                tex.setTextSize(30);
                tex.setText(s);//題目
            }
        }
    }
    public void onClick(View v){
        Cursor c;
        if (i<5){
            String s1=String.valueOf(Q[i]);
             c= db.rawQuery("SELECT * FROM Question  WHERE question_id='"+s1+"'", null);
            if(c.getCount()>0) {
                c.moveToFirst();
                String s=i+1+c.getString(1)+"\n"+c.getString(3)+"\n"+c.getString(4)+"\n"+c.getString(5)+"\n"+c.getString(6)+"\n";
                tex.setTextSize(30);
                tex.setText(s);//題目
                i++;
            }
        }
        else {
            db.close();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
