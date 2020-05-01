package com.irimedas.notifyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.models.User;


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
                User testUser = new User(uid,name,email);
                testUser.save();

                Toast.makeText(this,"TABLE: "+testUser.getTABLE(),Toast.LENGTH_LONG).show();
               /* User usuario = new User();
                ArrayList<User> users = usuario.all();
                String sendUsers;
                for (User user1 : users){

                }
                Toast.makeText(this,"Answer db: "+db.show(),Toast.LENGTH_LONG).show();*/
            }
    }
}
