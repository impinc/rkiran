package com.akshay.sub_jungle;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by VARIANCEINFOTECH on 4/7/16.
 */
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



    public void setDataInPref(Context context,String Key,String SessionId)
    {
        SharedPreferences.Editor editor1 = context.getSharedPreferences("PrefData", Context.MODE_PRIVATE).edit();
        editor1.putString(Key, SessionId);
        editor1.commit();

    }

    public  String getDataInPref(Context context)
    {
        SharedPreferences prefs1 = context.getSharedPreferences("PrefData", Context.MODE_PRIVATE);
        String id = prefs1.getString("SessionId","null12");
        return id;
    }


    public void setUserIdInPref(Context context,String UserId)
    {
        SharedPreferences.Editor editor1 = context.getSharedPreferences("UserId", Context.MODE_PRIVATE).edit();
        editor1.putString("UserId", UserId);
        editor1.commit();
    }

    public  String getUserIdInPref(Context context) {
        SharedPreferences prefs1 = context.getSharedPreferences("UserId", Context.MODE_PRIVATE);
        String id = prefs1.getString("UserId","null");
        return id;
    }


}
