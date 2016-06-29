package com.akshay.leadmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectDB extends AppCompatActivity {

    String TAG = "Response";
    SoapPrimitive resultString;
    String Logon;
    String DbId,DBid;
    ArrayList list;
    Spinner spDBlist;
    int check=0;
    ProgressDialog pd;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdb);

        Intent i=getIntent();
        Logon=i.getStringExtra("response");

        spDBlist=(Spinner)findViewById(R.id.spDBlist);

        MyTask myTask = new MyTask();
        myTask.execute();

        spDBlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                check += 1;
                if (check > 1) {

                    HashMap map = (HashMap) parent.getItemAtPosition(position);
                    DBid = (String) map.get("DbId");

                    GetWorkgroup getWorkgroup=new GetWorkgroup();
                    getWorkgroup.execute();

                    if(count>1)
                    {
                        Intent i= new Intent(SelectDB.this,Selectworkgroup.class);
                        i.putExtra("id",DBid);
                        i.putExtra("Logon", Logon);
                        startActivity(i);

                    }else{
                        Intent i= new Intent(SelectDB.this,Home.class);
                        startActivity(i);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            pd=new ProgressDialog(SelectDB.this);
            pd.setMessage("Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
          /*  ArrayAdapter adb=new ArrayAdapter(SelectDB.this,android.R.layout.simple_list_item_1,list);
            spDBlist.setAdapter(adb);*/



            String[] from = { "DbName","DbId" };
            int[] to = { R.id.tname};
            SimpleAdapter adapter=new SimpleAdapter(SelectDB.this,list,R.layout.single,from,to);
            spDBlist.setAdapter(adapter);
            pd.cancel();

            //Toast.makeText(SelectDB.this, "Response : "+ resultString.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void calculate() {

        String SOAP_ACTION = "LMServiceNamespace/GetDatabase";
        String METHOD_NAME = "GetDatabase";
        String NAMESPACE = "LMServiceNamespace";
        String URL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("logon_id", Logon);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            list=new ArrayList();
           // list.add("-Selecte Database-");

            HashMap map0=new HashMap();

            map0.put("DbId","0");
            map0.put("DbName", "---Select Database---");

            list.add(map0);

            SoapObject resultRequestSOAP    = (SoapObject) soapEnvelope.getResponse();
           // resultString = (SoapPrimitive) soapEnvelope.getResponse();
            for (int i = 0; i < resultRequestSOAP.getPropertyCount(); i++)
            {
                SoapObject TypeDataBaseData = (SoapObject) resultRequestSOAP .getProperty(i);
                String DbName = TypeDataBaseData.getProperty("DatabaseName").toString();
                DbId = TypeDataBaseData.getProperty("DatabaseID").toString();

                HashMap map=new HashMap();

                map.put("DbId",DbId);
                map.put("DbName",DbName);

                list.add(map);

                Log.e("id.......", DbName+" :"+DbId);
            }
            Log.e("list", String.valueOf(list));



            Log.e(TAG, "Result SECOND API: " + resultRequestSOAP);
           // Log.e(TAG, "Result SECOND API: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
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


                SoapObject resultRequestSOAP    = (SoapObject) soapEnvelope.getResponse();
                // resultString = (SoapPrimitive) soapEnvelope.getResponse();
                count=resultRequestSOAP.getPropertyCount();
                for (int i = 0; i < resultRequestSOAP.getPropertyCount(); i++)
                {
                    SoapObject TypeDataBaseData = (SoapObject) resultRequestSOAP .getProperty(i);

                    //String x = TypeDataBaseData.getProperty("DatabaseName").toString();
                   // list.add(x);
                }
             //   Log.e("list", String.valueOf(list));

                Log.e("===============", "Count: " + count);
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

        }
    }
}
