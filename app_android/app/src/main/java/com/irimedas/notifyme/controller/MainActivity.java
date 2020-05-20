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
    //Te lleva a la lista de notas(cambiar)
    private Button bNotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);
        this.context = this.getApplicationContext();


        /*View = findViewById(R.id.rvView);
        View.setLayoutManager(new LinearLayoutManager(this));
        View.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));*/




        //auth.singout();
        //intent = new Intent();

        //auth = new Auth("trolldeprueva@gmail.com","testing",this);

        //bNotesList = findViewById(R.id.bNotesList);
        //bNotesList.setOnClickListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        userPreference=new Auth().readtoPreferent();
        if(userPreference!=null) {
            auth = new Auth(userPreference[0], userPreference[1], this);
        }else{
            auth=new Auth();
        }
        //FirebaseUser user = null;

            auth.singIn();
            user = auth.getCurrentUser();
            if(user == null){
                //auth.createAccount();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }else{
                intent = new Intent(this, NotesListActivity.class);
                startActivity(intent);
/*

            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            Toast.makeText(this,"name: "+name+"\nEmail: "+email+"\nuid: "+uid,Toast.LENGTH_LONG).show();
            //test db
                //all user
                Users allusers = new Users();
                allusers.all();
                allusers.get();

                //new user
                /*String idTest = "sñlfjsdlñfsjñlsdfasdñl";
                Users newUser = new Users(idTest,"test","test@test.com");
                newUser.save();*/
                /*//find user
                Users findUser = new Users();
                findUser.find(uid);
                //update user
                Users updateUser = new Users();
                Map<String,Object> dataUser = new HashMap<>();
                dataUser.put("name","Trolldeprueva");
                updateUser.update(uid,dataUser);
                //remove user
                newUser.remove();

                //Notes
                //allNotes*/
               /* Notes notes = new Notes();
                notes.all();
                notes.get();*/
                //new note
                /*Notes newNote = new Notes("test1","LOREM IPSUM");
                newNote.save();
                newNote.show();
                //find note
                Notes findNote = new Notes();
                findNote.find(newNote.getId());
                //update note
                Notes updateNote = new Notes();
                Map<String,Object> dataNote = new HashMap<>();
                dataNote.put("subtitle","RANDOM");
                updateNote.update(newNote.getId(),dataNote);
                //remove note
                newNote.remove();*/
            }
       // auth.singout();
    }

    //Te lleva a la lista de notas(cambiar)
    @Override
    public void onClick(android.view.View v) {
        //intent = new Intent();

        if(bNotesList.isPressed()){
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
