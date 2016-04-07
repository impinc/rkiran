package com.akshay.sub_jungle;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by VARIANCEINFOTECH on 4/7/16.
 */
public class Get_Record extends AsyncTask<String,String,String> {

    SessionManager manager;
    String SessionId,UserId,temp1,msg;
    Context context1;
    ListView lvhagent,lvhstrategist;
    ArrayList list;

    Get_Record(Context context,ListView lvhagent,ListView lvhstrategist)
    {
        this.context1=context;
        manager=new SessionManager();
        SessionId=manager.getDataInPref(context1);
        UserId=manager.getUserIdInPref(context1);
    }
    @Override
    protected String doInBackground(String... params) {
        URL location1 = null;

        try {
            location1 = new URL("http://suburbanjungler.com/modules/Mobile/api.php");

            URLConnection con1 = location1.openConnection();
            con1.setDoInput(true);
            con1.setDoOutput(true);

            String req_data = URLEncoder.encode("_operation", "UTF-8") + "=" + URLEncoder.encode("dashboardStrategiesRecords", "UTF-8")
                    + "&" + URLEncoder.encode("_session", "UTF-8") + "=" + URLEncoder.encode(SessionId, "UTF-8")
                    + "&" + URLEncoder.encode("module", "UTF-8") + "=" + URLEncoder.encode("Documents", "UTF-8")
                    + "&" + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(UserId, "UTF-8");

            OutputStream os = con1.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(req_data);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con1.getInputStream()));

            temp1 = reader.readLine();

            if (temp1 != null) {
                String res1 = temp1 + "\n";
            }
        } catch (Exception e) {

        }


        JSONObject jsonSession= null;
        try {
            jsonSession = new JSONObject(temp1);

            msg=jsonSession.getString("success");
            Log.e("Success:", msg);


                JSONObject jsonobject=jsonSession.getJSONObject("result");

                JSONObject object1=jsonobject.getJSONObject("Strategies");

            JSONObject object2=object1.getJSONObject("data");

            JSONArray object3=object2.getJSONArray("Record");
            for(int i=0;i<object3.length();i++) {

                JSONObject object4 = object3.getJSONObject(i);

                JSONObject object5 = object4.getJSONObject("rawData");

                String Title = object5.getString("title");

                Log.e("Title", Title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Home", temp1);
            return null;
    }
}
