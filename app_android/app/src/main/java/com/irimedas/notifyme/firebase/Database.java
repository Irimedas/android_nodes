package com.irimedas.notifyme.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.controller.MainActivity;

import java.util.Map;


public class Database {

    //attributes connect Firebase
    protected static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();

    //attributes of model
    private static String collection;
    private static Query query;
    private static String pk;


    public Database() {
        db.setFirestoreSettings(settings);
    }

    //CRUD
    public void save() {
        db.collection(collection).document(pk).set(this);
    }

    public void update(String id, Map<String, Object> data) {
        db.collection(collection).document(id).update(data);
    }

    public void remove() {
        db.collection(collection).document(pk).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.context, R.string.removed, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.context,R.string.removed_fail, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String generateId() {
        return db.collection(collection).document().getId();
    }

    ///Queries
    /**
     * Method that create a query of find element by id
     * @param id String contain the id from element that is find
     * */
    public void find(String id) {

        where("id", "=", id);
    }

    public void all() {
        query = db.collection(collection);
    }
    /**
     * Method that create a query of filter result where object accomplish conditions
     * @param  field String this is field in search
     * @param  condition String this is the condition accomplish, accept ( =, >, >=, <, <= )
     * */
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
    /**
     * Method that create a query of filter result if value is in to array field
     * @param field String name from field to search, is required that this is array
     * @param value String name from the value to search
     * */
    public void in(String field, String value) {
        CollectionReference collectionReference = db.collection(collection);
        query = collectionReference.whereArrayContains(field, value);
    }

    //exectuion queries
    //Interface from need call to method readData
    public interface FirestoreCallback {
        void onCallback(QuerySnapshot documents);
    }

    /**
     * Method that is use from read data from the dataabase Firestone
     * */
    public void readData(final FirestoreCallback firestoreCallback) {

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            firestoreCallback.onCallback(task.getResult());
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

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

    public static String getPk() {
        return Database.pk;
    }

    public static void setPk(String id) {
        Database.pk = id;
    }
}
