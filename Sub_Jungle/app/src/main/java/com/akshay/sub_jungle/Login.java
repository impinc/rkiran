package com.akshay.sub_jungle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText etusername,etpassword;
    Button btnlogin;
    SessionManager manager;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername=(EditText)findViewById(R.id.username);
        etpassword=(EditText)findViewById(R.id.password);
        tv=(TextView)findViewById(R.id.txtforgetpass);
        btnlogin=(Button)findViewById(R.id.btn);
        manager=new SessionManager();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if (username.equals("") || password.equals("")) {

                    if(username.equals(""))
                    {
                        etusername.setError("Enter Username.");
                    }else
                    {
                        etpassword.setError("Enter Password.");
                    }

                } else {

                    CheckLogin checkLogin = new CheckLogin(username, password, Login.this);
                    checkLogin.execute();


                }

            }
        });

    }
    public void forgetPass(View v)
    {
        Intent forgetIntent=new Intent(Login.this,ForgetPassword.class);
        startActivity(forgetIntent);
    }


    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

}
