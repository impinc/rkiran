package com.akshay.leadmaster;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Locale;

public class Callbacktask extends AppCompatActivity {

    ListView lvcallbacktask;
    String TAG = "Response";
    EditText etsearch;
    ArrayList list;
    ProgressDialog pd;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacktask);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(android.R.drawable.ic_menu_my_calendar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        lvcallbacktask=(ListView)findViewById(R.id.lvcallbacktask);
        lvcallbacktask.setTextFilterEnabled(true);
        etsearch=(EditText)findViewById(R.id.etsearch);
        list=new ArrayList();

        GetResponseCallBack getCallBack=new GetResponseCallBack();
        getCallBack.execute();

        etsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               // Callbacktask.this.customAdapter.getFilter().filter(cs.toString());
                String text = etsearch.getText().toString().toLowerCase(Locale.getDefault());
                customAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub


            }
        });

    }

    private class GetResponseCallBack extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            pd=new ProgressDialog(Callbacktask.this);
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
            customAdapter=new CustomAdapter(Callbacktask.this,R.layout.raw_callbacktask,list);
            lvcallbacktask.setAdapter(customAdapter);
            Helper.getListViewSize(lvcallbacktask);
            pd.cancel();

        }
    }

    private void calculate() {

        String SOAP_ACTION = "LMServiceNamespace/SearchCallBackEvent";
        String METHOD_NAME = "SearchCallBackEvent";
        String NAMESPACE = "LMServiceNamespace";
        String URL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("username", "chirag.aspdeveloper@gmail.com");
            Request.addProperty("pwd", "testchirag");
            Request.addProperty("company_id", "7639");
            Request.addProperty("eventname", "");
            Request.addProperty("startindex", "1");
            Request.addProperty("endindex", "15");
            Request.addProperty("eventdate", "");

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            //list=new ArrayList();


            SoapObject resultRequestSOAP    = (SoapObject) soapEnvelope.getResponse();
            // resultString = (SoapPrimitive) soapEnvelope.getResponse();
            for (int i = 0; i < resultRequestSOAP.getPropertyCount(); i++)
            {
                SoapObject TypeEventData = (SoapObject) resultRequestSOAP .getProperty(i);
                String account = TypeEventData.getProperty("Company").toString();
                String callback = TypeEventData.getProperty("EventName").toString();
                String startTime = TypeEventData.getProperty("StartTime").toString();
                String endTime = TypeEventData.getProperty("EndTime").toString();
                String eventstatus = TypeEventData.getProperty("EventStatus").toString();

                CallbacktaskData  callbacktaskData=new CallbacktaskData();

                if(account.equals("anyType{}"))
                {
                    account="none";
                }else
                {
                    account=account+"";
                }
                callbacktaskData.setAccount(account);
                callbacktaskData.setCallback(callback);
                callbacktaskData.setStartTime(startTime);
                callbacktaskData.setEndTime(endTime);
                callbacktaskData.setEventstatus(eventstatus);
                
                list.add(callbacktaskData);

            }
           // Log.e("list", String.valueOf(list));


            Log.e(TAG, "Result SECOND API: " + resultRequestSOAP);
                                                     // Log.e(TAG, "Result SECOND API: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
