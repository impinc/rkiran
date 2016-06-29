package com.akshay.leadmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       web=(WebView)findViewById(R.id.webview);
      //  web.loadUrl("http://dev.leadmastercrm.com/mobile_dashboard.asp");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i=new Intent(Home.this,Login.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_callbacktask) {
            Intent i=new Intent(Home.this,Callbacktask.class);
             startActivity(i);
             // Handle the camera action
        }
        if (id == R.id.nav_contact) {
            Intent i=new Intent(Home.this,Contacts.class);
            startActivity(i);
            // Handle the camera action
        }
        if (id == R.id.nav_acoount) {
            Intent i=new Intent(Home.this,Accounts.class);
            startActivity(i);
            // Handle the camera action
        }
        if (id == R.id.nav_shortcuts) {
            Intent i=new Intent(Home.this,MyShortcuts.class);
            startActivity(i);
            // Handle the camera action
        }

        if (id == R.id.nav_recent) {
            Intent i=new Intent(Home.this,RecentItems.class);
            startActivity(i);
            // Handle the camera action
        }

        if (id == R.id.nav_dashboard) {
            Intent i=new Intent(Home.this,Home.class);
            startActivity(i);
            // Handle the camera action
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
