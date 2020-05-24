package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.firebase.Auth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context context;
    public static Auth auth;
    public static RecyclerView View;
    private String[] userPreference; //contain pass and email last user that make login
    public static FirebaseUser user; //user from fireBase
    private Intent intent;

    private Button bNotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);
        this.context = this.getApplicationContext();
    }


    @Override
    public void onStart() {
        super.onStart();
        userPreference = new Auth().readtoPreferent();
        if (userPreference != null) {
            auth = new Auth(userPreference[0], userPreference[1], this);
        } else {
            auth = new Auth();
        }
        auth.singIn();
        user = auth.getCurrentUser();
        if (user == null) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, NotesListActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(android.view.View v) {

        if (bNotesList.isPressed()) {
            intent = new Intent(this, NotesListActivity.class);

            startActivity(intent);
        }
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
        if (id == R.id.mi_noteEdit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
