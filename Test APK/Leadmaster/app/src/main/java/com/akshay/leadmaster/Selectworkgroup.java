package com.akshay.leadmaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class Selectworkgroup extends AppCompatActivity {

    String TAG = "Response";
    SoapPrimitive resultString;
    String Logon,DBid;
    String DbId;
    ArrayList list;
    Spinner SpWKlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectworkgroup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i=getIntent();
        Logon=i.getStringExtra("Logon");
        DBid=i.getStringExtra("id");

        SpWKlist=(Spinner)findViewById(R.id.spWKlist);

        GetWorkgroup getWorkgroup=new GetWorkgroup();
        getWorkgroup.execute();

        SpWKlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(Selectworkgroup.this,Home.class);
                startActivity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private class GetWorkgroup extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");

            String SOAP_ACTION ="LMServiceNamespace/GetWorkgroups";
            String METHOD_NAME = "GetWorkgroups";
            String NAMESPACE = "LMServiceNamespace";
            String URL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";

            try {
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("logon_id", Logon);
                Request.addProperty("database_id", DBid);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                list=new ArrayList();

                String x0 = "---Select WorkGroup---";
                list.add(x0);

                SoapObject resultRequestSOAP    = (SoapObject) soapEnvelope.getResponse();
                // resultString = (SoapPrimitive) soapEnvelope.getResponse();
                for (int i = 0; i < resultRequestSOAP.getPropertyCount(); i++)
                {
                    SoapObject TypeDataBaseData = (SoapObject) resultRequestSOAP .getProperty(i);
                    String x = TypeDataBaseData.getProperty("WorkgroupName").toString();
                    list.add(x);
                }
                Log.e("list", String.valueOf(list));


                Log.e(TAG, "Result Workgroup API: " + resultRequestSOAP);
                // Log.e(TAG, "Result SECOND API: " + resultString);
            } catch (Exception ex) {
                Log.e(TAG, "Error: " + ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");

            ArrayAdapter adb=new ArrayAdapter(Selectworkgroup.this,android.R.layout.simple_list_item_1,list);
            SpWKlist.setAdapter(adb);

        }
    }
}
