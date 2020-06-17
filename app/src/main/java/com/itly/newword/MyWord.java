package com.itly.newword;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyWord extends AppCompatActivity implements View.OnClickListener{

    private EditText myWord;
    private EditText myMeaning;
    private Button insert;
    private Button update;
    private Button delete;
    private Button select;
    private Button backHome;
    private TextView mShow;
    MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_word);
        myHelper = new MyHelper(this);
        init();
    }

    private void init() {
        myWord = findViewById(R.id.et_word);
        myMeaning = findViewById(R.id.et_meaning);
        insert = findViewById(R.id.Insert);
        update = findViewById(R.id.Update);
        delete = findViewById(R.id.Delete);
        select = findViewById(R.id.Select);
        backHome = findViewById(R.id.MyBackHome);
        mShow = findViewById(R.id.tv_show);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        select.setOnClickListener(this);
        backHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String word;
        String meaning;
        SQLiteDatabase db;
        ContentValues values;
        switch (view.getId()){
            case R.id.Insert:
                word = myWord.getText().toString();
                meaning = myMeaning.getText().toString();
                db = myHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("word",word);
                values.put("meaning",meaning);
                db.insert("word_info",null,values);
                Toast.makeText(this,"单词录入成功",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.Select:
                db = myHelper.getReadableDatabase();
                Cursor cursor = db.query("word_info", null, null, null, null, null, null);
                if(cursor.getCount() == 0){
                    mShow.setText("");
                    Toast.makeText(this,"没有数据请先插入",Toast.LENGTH_SHORT).show();
                }else{
                    cursor.moveToFirst();
                    mShow.setText("Word:"+cursor.getString(1)+",Meaning:"+cursor.getString(2));
                }
                while (cursor.moveToNext()){
                    mShow.append("\n"+"Word:"+cursor.getString(1)+",Meaning:"+cursor.getString(2));
                }
                cursor.close();
                db.close();
                break;
            case R.id.Update:
                db = myHelper.getReadableDatabase();
                values = new ContentValues();
                values.put("meaning",meaning = myMeaning.getText().toString());
                db.update("word_info",values,"word=?",new String[]{myWord.getText().toString()});
                Toast.makeText(this,"单词修改成功",Toast.LENGTH_LONG).show();
                db.close();
                break;
            case R.id.Delete:
                db = myHelper.getReadableDatabase();
                db.delete("word_info","word=?",new String[]{myWord.getText().toString()});
                Toast.makeText(this,"单词删除成功",Toast.LENGTH_LONG).show();
                db.close();
                break;
            case R.id.MyBackHome:
                Intent it=new Intent();
                it.setClass(MyWord.this, ShowActivity.class);
                startActivity(it);
                MyWord.this.finish();
                break;
        }
    }
}
