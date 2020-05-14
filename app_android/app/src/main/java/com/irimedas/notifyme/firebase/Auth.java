package com.irimedas.notifyme.firebase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            String userName = user.getDisplayName();
                            String userEmail = email;
                            Users newUser = new Users(userID, userName, userEmail);
                            newUser.save();
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
     */
    public void singIn() {
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
                                Users newUser = new Users(userID, userName, userEmail);
                                sendtoPreferents(user, password);
                                //goTohome(user);
                                //Toast.makeText(activity.getApplicationContext(), "TEST:\nemail="+email+"\npassword="+password+"\nloaded="+loaded, Toast.LENGTH_SHORT).show();
                                //   updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(activity.getApplicationContext(), "SingIn Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                                // Fragment fragment_login = new LoginFragment();
                            }

                            // ...
                        }
                    });
        } else {
            singout();
        }
    }

    /**
     * GetCurrentUser
     */
    public FirebaseUser getCurrentUser() {
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

    public void singout() {

        FirebaseAuth.getInstance().signOut();
        clearPreferent();
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
    }
}