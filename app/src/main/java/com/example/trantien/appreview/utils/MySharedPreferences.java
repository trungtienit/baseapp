package com.example.trantien.appreview.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public MySharedPreferences(Context context) {
        sharedPreferences=context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void Save(String key,String value){
        editor.putString(key, value);
        editor.commit();
        return;
    }
    public String Get(String key){
        return sharedPreferences.getString(key,"NULL");
    }
}
