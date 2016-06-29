package com.akshay.leadmaster;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {


    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("Androidwarriors", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);

        editor.commit();

    }

    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("Androidwarriors", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }

    public void setUser(Context context, String username, String password) {
        SharedPreferences.Editor editor1 = context.getSharedPreferences("User", Context.MODE_PRIVATE).edit();
        editor1.putString("username", username);
        editor1.putString("password", password);
        editor1.commit();
    }

    public  String getUserName(Context context) {
        SharedPreferences prefs1 = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String username = prefs1.getString("username", "");

        return username;
    }

    public  String getUserPass(Context context) {
        SharedPreferences prefs1 = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String password = prefs1.getString("password","");
        return password;
    }
}
