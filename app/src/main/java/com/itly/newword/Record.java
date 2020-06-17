package com.itly.newword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Record extends AppCompatActivity {

    private ListView mlistView;
    private Button button;
    private SaveWord saveWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        mlistView = findViewById(R.id.lv);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        mlistView.setAdapter(myBaseAdapter);
        init();

    }

    private void init() {
        button = findViewById(R.id.recordBackHome);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it=new Intent();
                it.setClass(Record.this, ShowActivity.class);
                startActivity(it);
                Record.this.finish();
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Vocabulary.wordList.size();
        }

        @Override
        public Object getItem(int i) {
            return Vocabulary.wordList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(Record.this,R.layout.list_item,null);
            TextView wtextView = v.findViewById(R.id.item_w);
            TextView mtextView = v.findViewById(R.id.item_m);
            wtextView.setText(Vocabulary.wordList.get(i).getWord() + "\t");
            mtextView.setText(Vocabulary.wordList.get(i).getMeaning());

            return v;
        }
    }

}
