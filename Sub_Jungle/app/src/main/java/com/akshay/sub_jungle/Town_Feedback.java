package com.akshay.sub_jungle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Town_Feedback extends AppCompatActivity {

    ListView tfeedbacklist;
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town__feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Town Feedback");
        setSupportActionBar(toolbar);

        tfeedbacklist=(ListView)findViewById(R.id.tfeedbacklist);
        manager = new SessionManager();

        String SessionId=manager.getDataInPref(getApplicationContext());

        GetData_TownFeedback getData_townFeedback=new GetData_TownFeedback(Town_Feedback.this,SessionId,tfeedbacklist);
        getData_townFeedback.execute();

        tfeedbacklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String TownName = parent.getItemAtPosition(position).toString();

                Intent feedbackintent = new Intent(Town_Feedback.this, Client_Feedback.class);
                feedbackintent.putExtra("TownName", TownName);
                startActivity(feedbackintent);
            }
        });
    }

}
