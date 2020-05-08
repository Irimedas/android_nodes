package com.irimedas.notifyme.firebase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.irimedas.notifyme.MainActivity;

import java.util.List;
import java.util.Map;


public class Database {

    //attributes connect Firebase
    protected static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();

    //attributes of model
    private static String collection;
    private static Query query;
    private static String id;


    public Database() {
        db.setFirestoreSettings(settings);
    }

    //CRUD
    public void save(){
        db.collection(collection).document(id).set(this);
    }

    public void update( String id, Map<String,Object> data){
        db.collection(collection).document(id).update(data);
    }
    public void remove(){
        db.collection(collection).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public String generateId(){
        return db.collection(collection).document().getId();
    }

    ///Queries
    public void find(String id) {

        where("id","=",id);
    }
    public void all(){
        query = db.collection(collection);
    }

    public void where(String field, String condition, String value) {
        CollectionReference collectionReference = db.collection(collection);

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
    }
    public void in (String field, List<String> value){
        CollectionReference collectionReference = db.collection(collection);
        query = collectionReference.whereArrayContains(field, value);
    }

    //getters & setters


    public static String getCollection() {
        return collection;
    }

    public static void setCollection(String collection) {
        Database.collection = collection;
    }

    public static Query getQuery() {
        return query;
    }

    public static void setQuery(Query query) {
        Database.query = query;
    }

    public static String showId() {
        return id;
    }

    public static void editId(String id) {
        Database.id = id;
    }
}
