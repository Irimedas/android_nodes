package com.irimedas.notifyme.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.irimedas.notifyme.controller.MainActivity;
import com.irimedas.notifyme.adapters.UsersAdapter;
import com.irimedas.notifyme.firebase.Database;

import java.util.List;

public class Users extends Database  {

    //atribute
    private static String TABLE = "Users";
    private String name;
    private String email;
    private String role;
    private String token;
    private List<String> user_notes;
    private List<String> share_notes;

    private static UsersAdapter adapter;
    private Context context = MainActivity.context;
    private RecyclerView view = MainActivity.View;

    public Users(){
        super();
        setCollection(TABLE);
    };

    public Users(String id, String name, String email) {
        super();
        setCollection(TABLE);
        editId(id);
        this.name = name;
        this.email = email;
        this.role = "user";
        this.token = null;
        this.share_notes = null;
        this.user_notes = null;
    }


    public void get(){
        getQuery().get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            adapter= new UsersAdapter(task.getResult(),context);
                            RecyclerView usersView = view;
                            usersView.setAdapter(adapter);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Users result = document.toObject(Users.class);
                                result.show();
                                Log.d("test", document.getId() + " => " + document.getData());

                                //guardar el documenten en un arraylist  de QueryDocumentSnapshot
                                //passar el arraylist al adapters corresponent

                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    public void show(){
        Toast.makeText(MainActivity.context,"User id: "+getId()+
                        "\nUser name: "+this.getName()+
                        "\nUser email: "+this.getEmail()+
                        "\nUser role: "+this.getRole()+
                        "\nUser Token: "+this.getToken()+
                        "\nUser User_notes: "+this.getUser_notes()+
                        "\nUser share_notes: "+this.getShare_notes()
                ,Toast.LENGTH_LONG).show();
    }
    //Getters && Setters
    public String getId(){
        return showId();
    }
    public void setId(String id){
        editId(id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
