package com.akshay.sub_jungle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class Strategist extends AppCompatActivity {

    ListView treportlist;
    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Strategist");
        setSupportActionBar(toolbar);

        treportlist=(ListView)findViewById(R.id.treportlist);
        manager = new SessionManager();

        String SessionId=manager.getDataInPref(getApplicationContext());

        GetData_TownReport getData_townReport=new GetData_TownReport(Strategist.this,SessionId,treportlist);
        getData_townReport.execute();

        /*treportlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String TownName = parent.getItemAtPosition(position).toString();

                Intent feedbackintent = new Intent(Strategist.this, Feedback.class);
                feedbackintent.putExtra("TownName", TownName);
                startActivity(feedbackintent);
            }
        });*/
    }

}
