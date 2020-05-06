package com.irimedas.notifyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.models.*;


import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static Context context;
    private static Auth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this.getApplicationContext();
        auth = new Auth("trolldeprueva@gmail.com","testing",this);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = null;

            auth.singIn();
            user = auth.getCurrentUser();
            if(user == null){
                auth.createAccount();
            }else{


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
                //new user
                String idTest = "sñlfjsdlñfsjñlsdfasdñl";
                Users newUser = new Users(idTest,"test","test@test.com");
                newUser.save();
                //find user
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
                //allNotes
                Notes notes = new Notes();
                notes.all();
                //new note
                Notes newNote = new Notes("test1","LOREM IPSUM");
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
                newNote.remove();
            }
        auth.singout();
    }
}
