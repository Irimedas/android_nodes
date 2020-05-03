package com.irimedas.notifyme.models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.irimedas.notifyme.MainActivity;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.interfaces.models;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Users extends Database  {
    //atribute
    private String id;
    private String name;
    private String email;
    private String role;
    private String token;
    private List<String> user_notes;
    private List<String> share_notes;

    private static String TABLE = "Users";
    private static DocumentReference idRef;
    private static DocumentSnapshot document;
    private static Users model;

    public Users(){
        super();
    };

    public Users(String id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = "user";
        this.token = null;
        this.share_notes = null;
        this.user_notes = null;
    }
    public Users(Users model){
        super();
        this.id = model.getId();
        this.name = model.getName();
        this.email = model.getEmail();
        this.role = model.getRole();
        this.token = model.getToken();
        this.share_notes = model.getShare_notes();
        this.user_notes = model.getUser_notes();
    }


    public void find(String id) {
        idRef = db.collection(TABLE).document(id);
        idRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    document = task.getResult();
                    if (document.exists()) {
                       // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Users test = new Users(document.toObject(Users.class));
                        Toast.makeText(MainActivity.context,"DocumentSnapshot data: "+document.getData(),Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.context,"Users: "+test.getEmail(),Toast.LENGTH_LONG).show();
                    } else {
                        //Log.d(TAG, "No such document");
                        Toast.makeText(MainActivity.context,"No such document",Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(MainActivity.context,"get failed with ",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Users result =document.toObject(Users.class);
        ///Toast.makeText(MainActivity.context,"Users: "+result.getEmail(),Toast.LENGTH_LONG).show();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getUser_notes() {
        return user_notes;
    }

    public void setUser_notes(List<String> user_notes) {
        this.user_notes = user_notes;
    }

    public List<String> getShare_notes() {
        return share_notes;
    }

    public void setShare_notes(List<String> share_notes) {
        this.share_notes = share_notes;
    }

    public Users getModel() {
        return model;
    }

    public void setModel(Users model) {
        this.model = model;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }


}
