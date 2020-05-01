package com.irimedas.notifyme.models;

import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.interfaces.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User extends Database implements models {
    //atribute
    private String id;
    private String name;
    private String email;
    private String role;
    private String token;
    private List<String> user_notes;
    private List<String> share_notes;

    private static String TABLE = "Users";


    public User(){
    };

    public User(String id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = "user";
        this.token = null;
        this.share_notes = null;
        this.user_notes = null;

    }
    @Override
    public void save(){
        db.collection(TABLE).document(id).set(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void find() {

    }

    @Override
    public ArrayList<Object> all() {
        return null;
    }

    @Override
    public Object show() {
        return null;
    }


    public String getId() {
        return id;
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

    public List<String> getUser_notes() {
        return user_notes;
    }

    public void setUser_notes(String[] user_notes) {
        this.user_notes = Arrays.asList(user_notes);
    }

    public List<String> getShare_notes() {
        return share_notes;
    }

    public void setShare_notes(String[] share_notes) {
        this.share_notes = Arrays.asList(share_notes);
    }

    public static String getTABLE() {
        return TABLE;
    }

    public static void setTABLE(String TABLE) {
        User.TABLE = TABLE;
    }
}
