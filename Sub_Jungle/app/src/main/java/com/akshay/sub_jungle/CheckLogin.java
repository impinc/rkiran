package com.akshay.sub_jungle;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by VARIANCEINFOTECH on 4/7/16.
 */
public class CheckLogin extends AsyncTask<String, String, String> {



    ProgressDialog pd;
    String temp1,temp2,strjson;
    String username,password;
    String web2msg,checkmsg,rolename;
    SessionManager manager;
    String SessionId,UserId;
    Context context;
    JSONObject mobject;

    public CheckLogin(String username, String password, Context login) {

        this.context=login;
        this.username=username;
        this.password=password;
        manager = new SessionManager();
    }

    @Override
    protected void onPreExecute() {

        pd=new ProgressDialog(context);
        pd.setTitle("Loading...");
        pd.setMessage("Wait...");
        pd.isIndeterminate();
        pd.show();

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        URL location1= null;

        try {
            location1 = new URL("http://suburbanjungler.com/modules/Mobile/api.php");

            URLConnection con1=location1.openConnection();
            con1.setDoInput(true);
            con1.setDoOutput(true);

            String req_data= URLEncoder.encode("_operation", "UTF-8" ) +"="+ URLEncoder.encode("loginAndFetchModules","UTF-8")
                    +"&"+ URLEncoder.encode("username","UTF-8") +"="+ URLEncoder.encode(username,"UTF-8")
                    +"&"+URLEncoder.encode("password","UTF-8") +"="+ URLEncoder.encode(password,"UTF-8");

            OutputStream os = con1.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(req_data);
            writer.flush();

            BufferedReader reader=new BufferedReader(new InputStreamReader(con1.getInputStream()));

            temp1=reader.readLine();

            if(temp1!=null)
            {
                String res1=temp1+"\n";
            }
        } catch (Exception e) {

        }

        Log.e("Output:", temp1);

        JSONObject jsonSession= null;
        try {
            jsonSession = new JSONObject(temp1);

            web2msg=jsonSession.getString("success");
            Log.e("Success:", web2msg);

            JSONObject jsonobject=jsonSession.getJSONObject("result");

            JSONObject object=jsonobject.getJSONObject("login");
            JSONArray modules=jsonobject.getJSONArray("modules" );


            strjson=modules.toString();
            SessionId=object.getString("session");
            UserId=object.getString("userid");
            Log.e("Sessionid:", SessionId);
            Log.e("Modules:", String.valueOf(modules));

        } catch (JSONException e) {

        }


        URL location2= null;

        try {
            location2 = new URL("http://suburbanjungler.com/modules/Mobile/api.php");

            URLConnection con2=location2.openConnection();
            con2.setDoInput(true);
            con2.setDoOutput(true);

            String req_data= URLEncoder.encode("_operation", "UTF-8" ) +"="+ URLEncoder.encode("detailRecords","UTF-8")
                    +"&"+ URLEncoder.encode("_session","UTF-8") +"="+ URLEncoder.encode(SessionId,"UTF-8")
                    +"&"+URLEncoder.encode("roleid","UTF-8") +"="+ URLEncoder.encode("1","UTF-8");

            OutputStream os = con2.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(req_data);
            writer.flush();

            BufferedReader reader=new BufferedReader(new InputStreamReader(con2.getInputStream()));

            temp2=reader.readLine();

            if(temp2!=null)
            {
                String res1=temp2+"\n";
            }
        } catch (Exception e) {

        }

        JSONObject jsoncheck= null;
        try {
            jsoncheck = new JSONObject(temp2);

            checkmsg=jsoncheck.getString("success");
            Log.e("Success:", checkmsg);

            JSONObject jsonobjectcheck=jsoncheck.getJSONObject("result");

            rolename=jsonobjectcheck.getString("rolename");




        } catch (Exception e) {

        }



        return strjson;

    }

    @Override
    protected void onPostExecute(String s) {
        pd.cancel();

        if(web2msg=="true")
        {
            String Key="SessionId";
            manager.setDataInPref(context, Key, SessionId);
            manager.setUserIdInPref(context, UserId);
            manager.setPreferences(context, "status", "1");

            if(rolename.equalsIgnoreCase("client"))
            {
                Intent i=new Intent(context,Home.class);
                i.putExtra("strJson",  s);
                context.startActivity(i);
            }
            if(rolename.equalsIgnoreCase("Coordinator"))
            {
                Intent i=new Intent(context,Home.class);
                i.putExtra("strJson",  s);
                context.startActivity(i);
            }
            if(rolename.equalsIgnoreCase("Strategist"))
            {
                Intent i=new Intent(context,Strategist_Home.class);
                i.putExtra("strJson",  s);
                context.startActivity(i);
            }

        }

        if(web2msg=="false"){

            Toast.makeText(context, "Enter Valid Username or Password.", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(context,Login.class);
            context.startActivity(i);

        }

    }
}

