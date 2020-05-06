package com.irimedas.notifyme.firebase;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.irimedas.notifyme.models.Users;


public class Auth extends Activity {

    private FirebaseAuth mAuth;
    private static String email;
    private static String password;
    private Activity activity;
    private static String TAG="Auth info";

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
                            String userID= user.getUid();
                            String userName= user.getDisplayName();
                            String userEmail = email;
                            Users newUser = new Users(userID,userName,userEmail);
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
     * */
    public void singIn(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                         //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "SingIn Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
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
        FirebaseAuth.getInstance().signOut();
    }

}
