package com.irimedas.notifyme.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.irimedas.notifyme.R;
import com.irimedas.notifyme.firebase.Auth;

public class LogintActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v!=null){
            int elementID = v.getId();
            switch (elementID){
                case R.id.btnlogin:
                    EditText etEmail = findViewById(R.id.etEmail);
                    EditText etPassword = findViewById(R.id.etPassword);
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    Auth loginAuth = new Auth(email,password,this);
                    ///MainActivity.auth = loginAuth;
                    loginAuth.singIn();
                    startActivity(new Intent(this, MainActivity.class));

                    //Toast.makeText(getApplicationContext(), "TEST:\nemail="+email+"\npassword="+password, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
