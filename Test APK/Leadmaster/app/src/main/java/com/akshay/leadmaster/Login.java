package com.akshay.leadmaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Login extends Activity {

    String wsURL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";
    //String soapMessage = '<?xml version="1.0" encoding="utf-8"?><soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope"><soap12:Body><APIValidateLogon xmlns="LMServiceNamespace"><username>' + username + '</username><pwd>' + password + '</pwd></APIValidateLogon></soap12:Body></soap12:Envelope>';
    String soapaction="LMServiceNamespace/APIValidateLogon";
    String Methodname="APIValidateLogon";
    String Namespace="LMServiceNamespace";
    String parameter="username";
    String TAG = "Response";
    SoapPrimitive resultString;
    String uname,pass;
    Button btnlogin;
    EditText etuname,etpass;
    SessionManager manager;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin=(Button)findViewById(R.id.btn);
        etuname=(EditText)findViewById(R.id.username);
        etpass=(EditText)findViewById(R.id.password);
        manager = new SessionManager();

        String i=manager.getPreferences(Login.this, "status");

        if(i.equals("1"))
        {
            String u1= manager.getUserName(Login.this);
            String p1=manager.getUserPass(Login.this);
            etuname.setText(u1);
            etpass.setText(p1);
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname=etuname.getText().toString();
                pass=etpass.getText().toString();

                MyTask myTask = new MyTask();
                myTask.execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            pd=new ProgressDialog(Login.this);
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
            pd.cancel();
            if(resultString.toString().equals("0"))
            {
                Toast.makeText(Login.this, "Enter valid input", Toast.LENGTH_LONG).show();
            }else {
                Intent i=new Intent(Login.this,SelectDB.class);
                i.putExtra("response",resultString.toString());
                startActivity(i);
            }
            Toast.makeText(Login.this, "Response : "+ resultString.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void calculate() {

        String SOAP_ACTION = "LMServiceNamespace/APIValidateLogon";
        String METHOD_NAME = "APIValidateLogon";
        String NAMESPACE = "LMServiceNamespace";
        String URL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("username", uname);
            Request.addProperty("pwd", pass);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.e(TAG, "Result FIRST API: " + resultString);

            manager.setPreferences(Login.this, "status", "1");
            manager.setUser(Login.this, uname,pass);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
