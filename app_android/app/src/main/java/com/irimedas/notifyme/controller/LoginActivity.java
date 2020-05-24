package com.irimedas.notifyme.controller;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.firebase.Auth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin;
    private TextView tv_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);

        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v != null) {
            int elementID = v.getId();
            EditText etEmail = findViewById(R.id.etEmail);
            EditText etPassword = findViewById(R.id.etPassword);
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            switch (elementID) {
                case R.id.btnlogin:
                    Auth loginAuth = new Auth(email, password, this);
                    loginAuth.singIn();
                    break;
                case R.id.tv_sign_up:
                    Auth newAuth = new Auth(email, password, this);
                    newAuth.createAccount();
                default:
                    break;
            }
        }
    }
}
