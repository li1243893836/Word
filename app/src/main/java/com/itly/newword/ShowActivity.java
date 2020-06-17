package com.itly.newword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {
    static final int DIALOG_SELECT_LIST = 0;
    static final int PROGRESS_DIALOG = 1;

    public static final String PREFS_NAME = "PrefsFile";
    public static final String LIST_NAME="ListName";
    public static final String LIST_FULL_NAME="ListFullName";
    public static final String IS_RANDOM="IsRandom";
    public static List<Activity> activityList = new LinkedList();
    private TextView stv1;
    private TextView welcome;
    private Button schenge;
    private Button srecite;
    private Button smean;
    private Button spell;
    private Button exit;
    private Button record;
    private Button download;
    private Button myWord;
    private DownloadManager downloadManager;
    private ProgressDialog progressDialog;
    private long id;
    public static String nowListName;
    public static String myListName;
    public static ArrayList<Word> wordList;
    public static final String APK_URL = "http://m.youdao.com/requestlog?c=MOBILE_DOWNLOAD&keyfrom=soft.download.web&url=youdaodict_android.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Map<String, String> userInfo = SavaLogin.getUserInfo(this);
        String name = userInfo.get("name");
        welcome = findViewById(R.id.s_welcome);
        welcome.setText(name + ",欢迎您！");

        init();
    }
    private void updateListName(String listFullName,String listname) {
        setPref(PREFS_NAME,LIST_FULL_NAME,listFullName);
        setPref(PREFS_NAME,LIST_NAME,listname);
        stv1.setText(listFullName);
        myListName=listname;
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
            case DIALOG_SELECT_LIST:
                final CharSequence[] items =  getResources().getStringArray(R.array.VListFullName);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true)
                        .setTitle(getResources().getString(R.string.PleaseSelectAList))
                        .setNegativeButton("退出",  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                updateListName(items[item].toString(),getResources().getStringArray(R.array.VListName)[item].toString());
                            }
                        });
                dialog = (AlertDialog) builder.create();
                break;
            case PROGRESS_DIALOG:
                progressDialog = new ProgressDialog(ShowActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage(getResources().getString(R.string.Loading));
                progressDialog.setCancelable(false);
                return progressDialog;
            default:
                dialog = null;
        }
        return dialog;
    }

    private void download() {
    }

    private void init() {
        stv1=findViewById(R.id.s_tv1);
        stv1.setText(getPref(PREFS_NAME,LIST_FULL_NAME,"大学英语四级"));

        schenge =  findViewById(R.id.s_chenge);
        schenge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_SELECT_LIST);
            }
        });

        srecite=findViewById(R.id.s_recite);
        srecite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);

                Thread getListThread=new Thread() {
                    public void run() {
                        getWordList();
                        progressDialog.dismiss();
                    }
                };
                getListThread.start();
                Intent it=new Intent();
                it.setClass(ShowActivity.this, Vocabulary.class);
                startActivity(it);
                ShowActivity.this.finish();
            }
        });

        smean=findViewById(R.id.s_mean);
        smean.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);

                Thread getListThread=new Thread() {
                    public void run() {
                        getWordList();
                        progressDialog.dismiss();
                    }
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(ShowActivity.this, MeaningTest.class);
                startActivity(it);
                ShowActivity.this.finish();
            }
        });

        spell=findViewById(R.id.s_spell);
        spell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);

                Thread getListThread=new Thread() {
                    public void run() {
                        getWordList();
                        progressDialog.dismiss();
                    }
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(ShowActivity.this, SpellTest.class);
                startActivity(it);
                ShowActivity.this.finish();
            }
        });
        exit=findViewById(R.id.s_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
        record=findViewById(R.id.s_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(PROGRESS_DIALOG);

                Thread getListThread=new Thread() {
                    public void run() {
                        getWordList();
                        progressDialog.dismiss();
                    }
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(ShowActivity.this, Record.class);
                startActivity(it);
                ShowActivity.this.finish();
            }
        });
        download = findViewById(R.id.s_down);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://mhhy.dl.gxpan.cn/apk/ml/MBGYD092101/Gardenscapes-ledou-MBGYD092101.apk";
                InstallUtils.installAPKWithBrower(ShowActivity.this, APK_URL);
            }
        });
        myWord = findViewById(R.id.myword);
        myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(PROGRESS_DIALOG);

                Thread getListThread=new Thread() {
                    public void run() {
                        getWordList();
                        progressDialog.dismiss();
                    }
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(ShowActivity.this, MyWord.class);
                startActivity(it);
                ShowActivity.this.finish();
            }
        });
    }

    private void wordDownload() {
        String uri = "http://m.youdao.com/requestlog?c=MOBILE_DOWNLOAD&keyfrom=soft.download.web&url=youdaodict_android.apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("下载");
        request.setDescription("有道词典正在下载....");
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,"youdao.apk");
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        id = downloadManager.enqueue(request);
    }

    public void setPref(String PrefName,String key,String value) {
        SharedPreferences settings=getSharedPreferences(PrefName,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getPref(String PrefName,String key,String defValue) {
        SharedPreferences settings=getSharedPreferences(PrefName,0);
        String ln=settings.getString(key, defValue);
        return ln;
    }

    private void getWordList() {
        if (nowListName==stv1.getText()) return;
        if (wordList==null) wordList=new ArrayList<Word>();
        else wordList.clear();
        nowListName=stv1.getText().toString();
        InputStream is = getResources().openRawResource(getListId());
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String line,word,meaning;

        try {
            String[] lines;
            while((line = br.readLine()) != null) {
                line=line.trim();
                if (line.length()>0) {
                    lines=line.split("#");
                    word=lines[0];
                    meaning=lines[1];
                    wordList.add(new Word(word,meaning));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getListId() {
        String ln=getPref(ShowActivity.PREFS_NAME,ShowActivity.LIST_NAME,"cet4");
        int listId=getResources().getIdentifier(ln,"raw",getPackageName());
        System.out.print(listId);
        if (listId==0) return R.raw.cet4;
        else return listId;
    }
    public void showdialog()
    {
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("确定要退出程序吗？");
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();

    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            Intent intent = new Intent();
            setResult(1,intent);
            finish();
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            arg0.cancel();
        }
    };

    public class Word {
        public String Word;
        public String Meaning;

        @Override
        public String toString() {
            return "Word{" +
                    "Word='" + Word + '\'' +
                    ", Meaning='" + Meaning + '\'' +
                    '}';
        }

        public String getWord() {
            return Word;
        }

        public String getMeaning() {
            return Meaning;
        }

        public Word() {
            Word="";
            Meaning="";
        }
        public Word(String word,String meaning) {
            Word=word;
            Meaning=meaning;
        }
    }


}
