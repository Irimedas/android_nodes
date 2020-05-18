package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.adapters.NotesListAdapter;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements View.OnClickListener {

    private NotesListAdapter notesListAdapter;
    private ArrayList<Notes> notesList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_list);

        // data to populate the RecyclerView with
        /*notesList = new ArrayList<>();

        for (int i = 1; i<=5; i++){
            notesList.add(new Notes("Nota " + i,"Descripción de nota " + i));
        }


        // set up the RecyclerView
        RecyclerView rvNotesList = findViewById(R.id.rvNotesList);
        rvNotesList.setLayoutManager(new LinearLayoutManager(this));
        notesListAdapter = new NotesListAdapter(this, notesList);
        notesListAdapter.setClickListener(this);
        rvNotesList.setAdapter(notesListAdapter);*/

        Button btnlogout = findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(this);

        FirebaseUser user = MainActivity.user;
        onload(user);


    }


    public void onload(FirebaseUser user){
        // find the user
        Users findUser = new Users();
        findUser.find(user.getUid());
        findUser.readData(new Database.FirestoreCallback() {
            @Override
            public void onCallback(QuerySnapshot documents) {
                //catch first user
                final Users user = documents.getDocuments().get(0).toObject(Users.class);
                //Find the notes of the user
                Notes findNotes = new Notes();
                findNotes.in("users_ids", user.getId());
                //Get query and load content in RecycleView
                findNotes.readData(new Database.FirestoreCallback() {
                    @Override
                    public void onCallback(QuerySnapshot documents) {
                        //Container of notes
                        ArrayList<Notes> notesList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : documents) {
                            Notes result = document.toObject(Notes.class);
                            notesList.add(result);
                        }

                        notesList.add(new Notes("Esto es una nota", "Loren ipsum dot sit amet. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."));
                        notesList.add(new Notes("Esto es otra nota", "Este es el cuerpo de la nota"));

                        RecyclerView rvNotesList = findViewById(R.id.rvNotesList);
                        rvNotesList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        notesListAdapter = new NotesListAdapter(getApplicationContext(), notesList);
                        notesListAdapter.setClickListener(new NotesListAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getApplicationContext(), "You clicked " + notesListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

                                intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                                intent.putExtra("note", notesListAdapter.getItem(position));
                                startActivity(intent);
                            }
                        });
                        rvNotesList.setAdapter(notesListAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "BUtton You clicked "+v.getId(), Toast.LENGTH_SHORT).show();
        if(v!=null){
            int idElement = v.getId();
            switch (idElement){
                case R.id.btn_logout:
                    Auth logout = new Auth();
                    logout.singout();
                    break;
                default:
                    break;
            }
        }
    }
}

