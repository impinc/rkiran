package com.akshay.leadmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    ListView lvcontact;
    String TAG = "Response";
    EditText etsearch;
    ArrayList list;
    ProgressDialog pd;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        lvcontact=(ListView)findViewById(R.id.lvcontact);
        lvcontact.setTextFilterEnabled(true);
        etsearch=(EditText)findViewById(R.id.etsearch);
        list=new ArrayList();

        GetResponseContact getCallBack=new GetResponseContact();
        getCallBack.execute();
    }

    private class GetResponseContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            pd=new ProgressDialog(Contacts.this);
            pd.setMessage("Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");


            String SOAP_ACTION = "LMServiceNamespace/SearchContactData";
            String METHOD_NAME = "SearchContactData";
            String NAMESPACE = "LMServiceNamespace";
            String URL = "http://api.leadmaster.com/lmservice/lmservice_mobile.asmx";

            try {
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("username", "chirag.aspdeveloper@gmail.com");
                Request.addProperty("pwd", "testchirag");
                Request.addProperty("company_id", "7639");
                Request.addProperty("startindex", "1");
                Request.addProperty("endindex", "15");
                Request.addProperty("lastname", "");

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
                    String contact = TypeEventData.getProperty("FirstName").toString();
                    String entered = TypeEventData.getProperty("Entered").toString();
                    String leadstatus = TypeEventData.getProperty("Lead_Status").toString();
                    String compaign = TypeEventData.getProperty("ContactID").toString();
                    String accmgr = TypeEventData.getProperty("LastName").toString();
                    String phone = TypeEventData.getProperty("Phone").toString();

                    ContactsData  contactsData=new ContactsData();

                    contactsData.setAccount(account);
                    contactsData.setContact(contact);
                    contactsData.setEntered(entered);
                    contactsData.setLeadstatus(leadstatus);
                    contactsData.setCompaign(compaign);
                    contactsData.setAccmgr(accmgr);
                    contactsData.setPhone(phone);

                    list.add(contactsData);

                }
                // Log.e("list", String.valueOf(list));


                Log.e(TAG, "Result SECOND API: " + resultRequestSOAP);
                // Log.e(TAG, "Result SECOND API: " + resultString);
            } catch (Exception ex) {
                Log.e(TAG, "Error: " + ex.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
           customAdapter=new CustomAdapter(Contacts.this,R.layout.raw_callbacktask,list);
          lvcontact.setAdapter(customAdapter);
            Helper.getListViewSize(lvcontact);
            pd.cancel();

        }
    }

    public class CustomAdapter extends ArrayAdapter {

        private List<ContactsData> list;
        private int resource;
        private LayoutInflater layoutInflater;
        Context context;

        public CustomAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            list=objects;
            this.context=context;
            this.resource=resource;
            layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            if(convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.raw_contacts,null);
            }

            TextView txaccount=(TextView)convertView.findViewById(R.id.txaccount);
            TextView txentered=(TextView)convertView.findViewById(R.id.txentered);
            TextView txcontact=(TextView)convertView.findViewById(R.id.txcontact);
            TextView txcompaign=(TextView)convertView.findViewById(R.id.txcompaign);
            TextView txleadstatus=(TextView)convertView.findViewById(R.id.txleadstatus);
            TextView txaccmgr=(TextView)convertView.findViewById(R.id.txaccmgr);
            TextView txphone=(TextView)convertView.findViewById(R.id.txphone);

            //  final Button btnmenu=(Button)convertView.findViewById(R.id.btnmenu);


            txaccount.setText(list.get(position).getAccount());
            txentered.setText(list.get(position).getEntered());
            txcontact.setText(list.get(position).getContact());
            txcompaign.setText(list.get(position).getCompaign());
            txleadstatus.setText(list.get(position).getLeadstatus());
            txaccmgr.setText(list.get(position).getAccmgr());
            txphone.setText(list.get(position).getPhone());
/*
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*PopupMenu popup = new PopupMenu(context,btnmenu);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu*//*

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custommenu);
                dialog.show();
            }
        });*/

            return convertView;
        }
    }

}
