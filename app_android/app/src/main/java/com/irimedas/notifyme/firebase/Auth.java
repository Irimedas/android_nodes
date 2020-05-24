package com.irimedas.notifyme.firebase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.irimedas.notifyme.controller.LoginActivity;
import com.irimedas.notifyme.controller.MainActivity;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;


public class Auth extends Activity {

    private FirebaseAuth mAuth;
    private static String email;
    private static String password;
    private Activity activity;
    private static String TAG = "Auth info";

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

    public Auth(String email, String password, String name, Activity activity) {
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
     */
    public void createAccount() {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            String userName = user.getDisplayName();
                            String userEmail = email;
                            Users newUser = new Users(userID, userName, userEmail);
                            newUser.save();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Create Count Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        goTohome();

                    }
                });
    }

    /**
     * Sing IN
     */
    public void singIn() {
        if ((email != null && email.length()!=0) && (password != null && password.length()!=0)) {
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
                                Users newUser = new Users(userID, userName, userEmail);
                                sendtoPreferents(user, password);
                                goTohome();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(activity.getApplicationContext(), "SingIn Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
        }
    }

    /**
     * GetCurrentUser
     */
    public FirebaseUser getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }

    public void singout() {

        FirebaseAuth.getInstance().signOut();
        clearPreferent();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        goTohome();
    }
    /**
     * Sharepreferences
     */

    public void sendtoPreferents(FirebaseUser user, String password) {
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Users", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        //Gson gson = new Gson();
        ArrayList<String> data = new ArrayList<>();
        data.add(0, user.getEmail());
        data.add(1, password);
        //String json = gson.toJson(data);
        String json = data.toString();
        prefsEditor.putString("User", json);
        prefsEditor.commit();

    }

    public String[] readtoPreferent() {

        //Gson gson = new Gson();
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Users", MODE_PRIVATE);
        String json = mPrefs.getString("User", null);
        if (json != null) {
            String clear = json.substring(1, (json.length() - 1)).replaceAll(" ", "");
            String[] obj = clear.split(",");
            return obj;
        } else {
            return null;
        }
    }

    public void clearPreferent() {
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Users", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove("User");
        prefsEditor.commit();

        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void goTohome(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                android.os.Process.killProcess(android.os.Process.myPid());//<-- this line is black Mage!!! Why???!! Holy shit!!!
               Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 1000);
    }
}