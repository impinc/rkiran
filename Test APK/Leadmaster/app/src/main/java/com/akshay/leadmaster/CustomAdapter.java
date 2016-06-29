package com.akshay.leadmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by VARIANCEINFOTECH on 4/13/16.
 */
public class CustomAdapter extends ArrayAdapter{

    private List<CallbacktaskData> list;
    private ArrayList<CallbacktaskData> arraylist;
    private int resource;
    private LayoutInflater layoutInflater;
    Context context;

    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        list=objects;
        this.context=context;
        this.resource=resource;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.arraylist = new ArrayList<CallbacktaskData>();
        this.arraylist.addAll(list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.raw_callbacktask,null);
        }

        TextView txaccount=(TextView)convertView.findViewById(R.id.txaccount);
        TextView txcallback=(TextView)convertView.findViewById(R.id.txcallback);
        TextView txstarttime=(TextView)convertView.findViewById(R.id.txstarttime);
        TextView txendtime=(TextView)convertView.findViewById(R.id.txendtime);
        TextView txeventstatus=(TextView)convertView.findViewById(R.id.txeventstatus);
      //  final Button btnmenu=(Button)convertView.findViewById(R.id.btnmenu);


        txaccount.setText(list.get(position).getAccount());
        txcallback.setText(list.get(position).getCallback());
        txstarttime.setText(list.get(position).getStartTime());
        txendtime.setText(list.get(position).getEndTime());
        txeventstatus.setText(list.get(position).getEventstatus());
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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        else
        {
            for (CallbacktaskData wp : arraylist)
            {
                if (wp.getAccount().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
