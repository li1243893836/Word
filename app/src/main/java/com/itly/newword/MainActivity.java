package com.itly.newword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ename;
    private Button btn_login;
    private Button btn_send;
    private EditText epassword;
    public static List<Activity> activityList = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename = findViewById(R.id.et_name);
        epassword = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(this, ShowActivity.class);
                intent.putExtra("username",ename.getText().toString().trim());
                intent.putExtra("password",epassword.getText().toString().trim());
                Map<String, String> userInfo = SavaLogin.getUserInfo(this);
                intent.putExtra("name",userInfo.get("name"));
                intent.putExtra("sex",userInfo.get("sex"));
                if(userInfo.get("username") == null || "".equals(userInfo.get("username"))
                        || userInfo.get("password") == null || "".equals(userInfo.get("password"))
                        || userInfo.get("name") == null || "".equals(userInfo.get("name"))
                        || userInfo.get("sex") == null || "".equals(userInfo.get("sex"))){
                    new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("请先注册").setIcon(R.mipmap.title).setPositiveButton("确定",null)
                            .setNegativeButton("取消",null).create().show();
                    return;
                }
                if((!(userInfo.get("username").toString()).equals(ename.getText().toString())) ||
                        (!userInfo.get("password").equals(epassword.getText().toString()))){
                    new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("您输出的账号或密码错误").setIcon(R.mipmap.title).setPositiveButton("确定",null)
                            .setNegativeButton("取消",null).create().show();
                    return;
                }
                startActivity(intent);
                finish();
                break;
            case R.id.btn_send:
                Intent intent1 = new Intent(this, Register.class);
                startActivity(intent1);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(resultCode);
        if (resultCode == 1){
            System.exit(0);
        }
    }
}
