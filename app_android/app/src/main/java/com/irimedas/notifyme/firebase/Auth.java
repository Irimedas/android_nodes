package com.irimedas.notifyme.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.Booleans;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;
import com.irimedas.notifyme.Functions;
import com.irimedas.notifyme.fragments.LoginFragment;
import com.irimedas.notifyme.MainActivity;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.fragments.NotesFragment;
import com.irimedas.notifyme.models.Users;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Auth extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static String email;
    private static String password;
    private Activity activity;
    private static String TAG="Auth info";
    public static boolean loaded = false;
    public Fragment fragment;

    public Auth(){
        this.mAuth = FirebaseAuth.getInstance();
        this.email = null;
        this.password = null;
    }

    public Auth(String email, String password, Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.email = email;
        this.password = password;
        this.activity = activity;
    }
    public Auth(String email, String password,String name, Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.email = email;
        this.password = password;
        this.activity = activity;
        // set Displayname
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileUpdates);
    }

    /**
     * Create Account
     * */
    public void createAccount (){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Create Users in db
                            String userID = user.getUid();
                            String userName = user.getDisplayName();
                            String userEmail = email;
                            Users newUser = new Users(userID,userName,userEmail);
                            newUser.save();
                            // Save user in sharepreferents
                            sendtoPreferents(user,password);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Create Count Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    /**
     * Sing IN
     * */
    public void singIn(){
        if (email != null && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userID = user.getUid();
                                String userName = user.getDisplayName();
                                String userEmail = email;
                                Users newUser = new Users(userID,userName,userEmail);
                                sendtoPreferents(user,password);
                                goTohome(user);
                                //Toast.makeText(activity.getApplicationContext(), "TEST:\nemail="+email+"\npassword="+password+"\nloaded="+loaded, Toast.LENGTH_SHORT).show();
                                //   updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(activity.getApplicationContext(), "SingIn Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                                Fragment fragment_login = new LoginFragment();
                            }

                            // ...
                        }
                    });
        }else{
            singout();
        }
    }
    /**
     * GetCurrentUser
     * */
    public FirebaseUser getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
        /*if (user != null) {
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

        }*/
    }
    public void singout(){
        MainActivity.user = null;
        this.clearPreferent();
        FirebaseAuth.getInstance().signOut();

    }

    public void sendtoPreferents(FirebaseUser user, String password){
        SharedPreferences mPrefs =MainActivity.context.getSharedPreferences("Users",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        //Gson gson = new Gson();
        ArrayList<String> data = new ArrayList<>();
        data.add(0,user.getEmail());
        data.add(1,password);
        //String json = gson.toJson(data);
        String json = data.toString();
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }
    public String[] readtoPreferent(){

        //Gson gson = new Gson();
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Users",MODE_PRIVATE);
        String json = mPrefs.getString("User",null);
        if(json!=null) {
            String clear = json.substring(1, (json.length() - 1)).replaceAll(" ", "");
            String[] obj = clear.split(",");
            return obj;
        }else{
            return null;
        }

    }
    public void clearPreferent(){
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Users",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove("User");
        prefsEditor.commit();
    }
    public void goTohome(FirebaseUser user){
         //MainActivity.user = user;
         loaded=true;
       // MainActivity.functions.onload(user);
        if(fragment!=null){
            FragmentTransaction transaction =fragment.getFragmentManager().beginTransaction();
            //transaction.remove(fragment);
            transaction.replace(R.id.main_layout,new NotesFragment());
            transaction.commit();
        }



    }
    public boolean isLoaded(){
        return loaded;
    }
    public void setFragment(Fragment fragment){
        this.fragment= fragment;
    }
}
