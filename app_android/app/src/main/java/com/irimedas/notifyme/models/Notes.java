package com.irimedas.notifyme.models;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.MainActivity;
import com.irimedas.notifyme.adapters.NotesAdapter;
import com.irimedas.notifyme.adapters.UsersAdapter;
import com.irimedas.notifyme.firebase.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notes extends Database {

    //atributes
    private static String TABLE = "Notes";
    private String title;
    private String body;
    private List<String> note_files;
    private List<String> users_ids;

    private static NotesAdapter adapter;
    private Context context = MainActivity.context;
    private RecyclerView view = MainActivity.rvList_notes;


    public Notes() {
        super();
        setCollection(TABLE);
    }

    public Notes(String title, String body) {
        super();
        setCollection(TABLE);
        setId(generateId());
        this.title = title;
        this.body = body;
        this.note_files=null;
        this.users_ids=null;
    }
/*
    public void get(){
        getQuery().get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            adapter= new NotesAdapter(task.getResult(),context);
                            RecyclerView notesView = view;
                            notesView.setAdapter(adapter);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notes result = document.toObject(Notes.class);
                               // result.show();
                                Log.d("test", document.getId() + " => " + document.getData());

                                //guardar el documenten en un arraylist  de QueryDocumentSnapshot
                                //passar el arraylist al adapters corresponent

                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }*/


    public void show(){
        Toast.makeText(MainActivity.context,"Note id: "+getId()+
                        "\nNote Title: "+this.getTitle()+
                        "\nNote Body: "+this.getBody()+
                        "\nNote Files "+this.getNote_files()
                ,Toast.LENGTH_LONG).show();
    }
    /**
     * Method of create new notes and link with User
     * @param userId String Value of ID's Users Owner
     * @param title String Value of Title's note
     * @param body String Value of Body's note
     * */
    public void createNote(String userId,String title,String body){
        Notes newNote = new Notes(title,body);
        List<String> id = new ArrayList<>();
        id.add(userId);
        newNote.setUsers_ids(id);
        newNote.save();
    }

    /**
     * Method of share with one note
     * @param shareUserId String Value of ID's User target
     * */
    public void shareNote(String shareUserId){

        List<String>users_ids = this.getUsers_ids();
        /// if not exist shareUserId in users_ids
        if(!(users_ids.contains(shareUserId))){
        users_ids.add(shareUserId);
        this.setUsers_ids(users_ids);
        Map<String,Object> data = new HashMap<>();
        data.put("users_ids",users_ids);
        this.update(getId(),data);
        }
    }
    /**
     * Method of remove the share with one note
     * @param shareUserId String Value of ID's User target
     * */
    public void decomposeShareNote(String shareUserId){

        List<String>users_ids = this.getUsers_ids();
        /// if exist shareUserId in users_ids
        if(users_ids.contains(shareUserId)){
            users_ids.remove(shareUserId);
            this.setUsers_ids(users_ids);
            Map<String,Object> data = new HashMap<>();
            data.put("users_ids",users_ids);
            this.update(getId(),data);
        }
    }

    //getters && setters
    public String getId(){
        return showId();
    }
    public void setId(String id){
        editId(id);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getNote_files() {
        return note_files;
    }

    public void setNote_files(List<String> note_files) {
        this.note_files = note_files;
    }

    public List<String> getUsers_ids() {
        return users_ids;
    }

    public void setUsers_ids(List<String> users_ids) {
        this.users_ids = users_ids;
    }
}
