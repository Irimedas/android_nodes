package com.irimedas.notifyme.models;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.irimedas.notifyme.controller.MainActivity;
import com.irimedas.notifyme.controller.adapters.UsersAdapter;
import com.irimedas.notifyme.firebase.Database;

import java.util.List;

public class Users extends Database {

    //atribute
    private static String TABLE = "Users";
    private String name;
    private String email;
    private String role;
    private String token;
    private String id;

    private static UsersAdapter adapter;
    private Context context = MainActivity.context;
    private RecyclerView view = MainActivity.View;

    public Users() {
        super();
        setCollection(TABLE);
    }

    public Users(String id, String name, String email) {
        super();
        setCollection(TABLE);
        setPk(id);
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = "user";
        this.token = null;
    }

    public void show() {
        Toast.makeText(MainActivity.context, "User id: " + getId() +
                        "\nUser name: " + this.getName() +
                        "\nUser email: " + this.getEmail() +
                        "\nUser role: " + this.getRole() +
                        "\nUser Token: " + this.getToken()
                , Toast.LENGTH_LONG).show();
    }

    //Getters && Setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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


}
