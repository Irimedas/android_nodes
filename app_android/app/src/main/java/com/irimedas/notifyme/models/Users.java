package com.irimedas.notifyme.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.irimedas.notifyme.MainActivity;
import com.irimedas.notifyme.firebase.Database;

import java.util.List;
import java.util.Map;

public class Users extends Database  {
    //atribute

    private static String TABLE = "Users";
    private Query query;

    private String id;
    private String name;
    private String email;
    private String role;
    private String token;
    private List<String> user_notes;
    private List<String> share_notes;


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

    public void find(String id) {

        where("id","=",id);
    }

    public void where(String field, String condition, String value) {
        CollectionReference collectionReference = db.collection(TABLE);

        switch (condition) {
            case "=":
                query = collectionReference.whereEqualTo(field, value);
                break;
            case ">":
                query = collectionReference.whereGreaterThan(field, value);
                break;
            case ">=":
                query = collectionReference.whereGreaterThanOrEqualTo(field, value);
                break;
            case "<":
                query = collectionReference.whereLessThan(field, value);
                break;
            case "<=":
                query = collectionReference.whereLessThanOrEqualTo(field, value);
                break;
            case "in":
                query = collectionReference.whereArrayContains(field, value);
                break;
            default:
                query = null;
                break;
        }
        //Executa la query
        this.get();
    }
    public void get(){
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
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


    public void save(){
        db.collection(TABLE).document(id).set(this);
    }
    public void update( String id, Map<String,Object> data){
        db.collection(TABLE).document(id).update(data);
    }
    public void remove(){
        db.collection(TABLE).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.context,"User delete",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.context,"User delete Fail",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void show(){
        Toast.makeText(MainActivity.context,"User id: "+this.getId()+
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
        return id;
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
