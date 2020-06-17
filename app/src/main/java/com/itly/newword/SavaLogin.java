package com.itly.newword;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SavaLogin {
    public static boolean saveUserInfo(Context context,String username,String password,String name,
                                       String age,String sex){
        SharedPreferences data = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = data.edit();
        edit.putString("username",username);
        edit.putString("password",password);
        edit.putString("name",name);
        edit.putString("age",age);
        edit.putString("sex",sex);
        edit.commit();
        return true;
    }
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences data = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = data.getString("username",null);
        String password = data.getString("password", null);
        String name = data.getString("name", null);
        String age = data.getString("age", null);
        String sex = data.getString("sex", null);
        HashMap<String, String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("name",name);
        map.put("age",age);
        map.put("sex",sex);
        return map;
    }

}
