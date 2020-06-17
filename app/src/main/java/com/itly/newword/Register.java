package com.itly.newword;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText eusername;
    private EditText epassword;
    private EditText ename;
    private EditText eage;
    private EditText esex;
    private Button restiger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        eusername = findViewById(R.id.et_username);
        epassword = findViewById(R.id.et_password);
        ename = findViewById(R.id.et_name);
        eage = findViewById(R.id.et_age);
        esex = findViewById(R.id.et_sex);
        restiger = findViewById(R.id.btn_restiger);
        restiger.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String username = eusername.getText().toString();
        String password = epassword.getText().toString();
        String name = ename.getText().toString();
        String age = eage.getText().toString();
        String sex = esex.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"请输入姓名",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(age)){
            Toast.makeText(this,"请输入年龄",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(sex)){
            Toast.makeText(this,"请输入性别",Toast.LENGTH_LONG).show();
            return;
        }
        try{
            if(Integer.parseInt(age) > 150 || Integer.parseInt(age) < 0){
                Toast.makeText(this,"请输入正确的年龄",Toast.LENGTH_LONG).show();
                return;
            }
        }catch (Exception e){
            Toast.makeText(this,"请输入正确的年龄",Toast.LENGTH_LONG).show();
        }

        if( !"男".equals(sex) &&  !"女".equals(sex)){
            Toast.makeText(this,"请输入正确的性别",Toast.LENGTH_LONG).show();
            return;
        }

        boolean b = SavaLogin.saveUserInfo(this, username, password, name, age, sex);
        if(b){
            Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
            finish();
            return;
        }else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_LONG).show();
            return;
        }

    }
}
