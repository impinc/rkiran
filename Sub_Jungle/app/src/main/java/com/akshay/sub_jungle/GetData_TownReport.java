package com.akshay.sub_jungle;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;

/**
 * Created by VARIANCEINFOTECH on 4/7/16.
 */

public class GetData_TownReport extends AsyncTask<String, String, ArrayList> {

    ProgressDialog pd;
    String temp1,temp2,strjson;
    ListView lvtreport;
    String RecordId;
    String web2msg;
    SessionManager manager;
    String SessionId;
    Context context;
    JSONObject mobject;
    ArrayList list;

    public GetData_TownReport(Context context, String sessionId, ListView listView) {
        this.context=context;
        this.lvtreport=listView;
        this.SessionId=sessionId;
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
    protected ArrayList doInBackground(String... params) {

        URL location1= null;

        try {
            location1 = new URL("http://suburbanjungler.com/modules/Mobile/api.php");

            URLConnection con1=location1.openConnection();
            con1.setDoInput(true);
            con1.setDoOutput(true);

            String req_data= URLEncoder.encode("_operation", "UTF-8") +"="+ URLEncoder.encode("listModuleRecords","UTF-8")
                    +"&"+ URLEncoder.encode("_session","UTF-8") +"="+ URLEncoder.encode(SessionId,"UTF-8")
                    +"&"+URLEncoder.encode("module","UTF-8") +"="+ URLEncoder.encode("Documents","UTF-8");

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
            e.printStackTrace();
        }

        Log.e("Output TownReport", temp1);

        JSONObject jsonSession= null;
        try {
            jsonSession = new JSONObject(temp1);

            web2msg=jsonSession.getString("success");
            Log.e("Success:", web2msg);

            JSONObject jsonobject=jsonSession.getJSONObject("result");


            JSONArray Records=jsonobject.getJSONArray("records" );
            list=new ArrayList();
            for(int i=0;i<Records.length();i++)
            {
                JSONObject json=Records.getJSONObject(i);

                RecordId=json.getString("id");
                String Label=json.getString("label");

                list.add(Label);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    protected void onPostExecute(ArrayList s) {
        pd.cancel();

        ArrayAdapter adbtreport=new ArrayAdapter(context,android.R.layout.simple_list_item_1,s);
        lvtreport.setAdapter(adbtreport);

    }
}