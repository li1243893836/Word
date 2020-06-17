package com.itly.newword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itly.newword.ShowActivity.Word;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary extends Activity {

    static final int PROGRESS_DIALOG = 0;

    private SaveWord saveWord;
    private TextView reciteListName;
    private TextView reciteWord;
    private TextView reciteMeaning;
    private Button reciteNext;
    private Button backToHome;
    private Button share;

    private java.util.Random random = new java.util.Random();
    private int wordId;
    public final static List<Word> wordList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recite);
        saveWord = new SaveWord(this);
        init();
        Word word = getNextWord();
		wordList.add(word);
        updateDisplay(word);
    }

    public void setPref(String PrefName, String key, String value) {
        SharedPreferences settings = getSharedPreferences(PrefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPref(String PrefName, String key, String defValue) {
        SharedPreferences settings = getSharedPreferences(PrefName, 0);
        String ln = settings.getString(key, defValue);
        return ln;
    }

    public Word getNextWord() {
        String isRan = getPref(ShowActivity.PREFS_NAME, ShowActivity.IS_RANDOM, "true");
        if (isRan == "true")
            return ShowActivity.wordList.get(random.nextInt(ShowActivity.wordList.size()));
        else return ShowActivity.wordList.get((wordId++) % ShowActivity.wordList.size());
    }

    protected void updateDisplay(Word word) {
        reciteWord.setText(word.Word);
        reciteMeaning.setText(word.Meaning);
    }

    private void init() {
        reciteListName = findViewById(R.id.reciteListName);
        reciteListName.setText(getPref(ShowActivity.PREFS_NAME, ShowActivity.LIST_FULL_NAME, "大学英语四级"));
        reciteWord = findViewById(R.id.reciteWord);
        reciteMeaning = findViewById(R.id.reciteMeaning);
        reciteNext = findViewById(R.id.reciteNext);
        reciteNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Word word = getNextWord();
				wordList.add(word);
                updateDisplay(word);
            }
        });
        backToHome = findViewById(R.id.backHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(Vocabulary.this, ShowActivity.class);
                startActivity(it);
                Vocabulary.this.finish();
            }
        });
        share = findViewById(R.id.Share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setAction(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_TEXT,wordList.get(wordList.size()-1).getWord()
				+ "\t" + wordList.get(wordList.size()-1).getMeaning());
                it.setType("text/plain");
                startActivity(it);
            }
        });
    }
}
