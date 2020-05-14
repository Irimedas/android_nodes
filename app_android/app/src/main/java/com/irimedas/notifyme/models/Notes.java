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
import com.irimedas.notifyme.adapters.NotesAdapter;
import com.irimedas.notifyme.firebase.Database;

import java.util.List;

public class Notes extends Database {

    //atributes
    private static String TABLE = "Notes";
    private String title;
    private String body;
    private List<String> note_files;

    private static NotesAdapter adapter;
    private Context context = MainActivity.context;
    private RecyclerView view = MainActivity.View;


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
    }

    public void get(){
        getQuery().get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            adapter= new NotesAdapter(task.getResult(),context);
                            RecyclerView usersView = view;
                            usersView.setAdapter(adapter);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notes result = document.toObject(Notes.class);
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
        Toast.makeText(MainActivity.context,"Note id: "+getId()+
                        "\nNote Title: "+this.getTitle()+
                        "\nNote Body: "+this.getBody()+
                        "\nNote Files "+this.getNote_files()
                ,Toast.LENGTH_LONG).show();
    }

    public void createNote(String userId,String title,String body){
        Notes newNote = new Notes(title,body);
       // Users updateUser = new Users();

       // updateUser.update();
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
}
