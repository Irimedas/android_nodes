package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.controller.adapters.NotesListAdapter;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

    private NotesListAdapter notesListAdapter;
    private ArrayList<Notes> notesList;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);

        //Floating Button
        FloatingActionButton fb_addNote = findViewById(R.id.fb_addNote);
        fb_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edtText = new EditText(v.getContext());

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.note_add_title);
                builder.setMessage(R.string.note_add_message);
                builder.setCancelable(false);
                builder.setView(edtText);
                builder.setPositiveButton(R.string.note_add_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //create Note
                        Notes newNote = new Notes(edtText.getText().toString(), null);
                        //edit Note
                        intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                        intent.putExtra("note", newNote);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), R.string.note_cancel, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        FirebaseUser user = MainActivity.user;
        onload(user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onload(FirebaseUser user) {
        // find the user
        Users findUser = new Users();
        findUser.find(user.getUid());
        findUser.readData(new Database.FirestoreCallback() {
            @Override
            public void onCallback(QuerySnapshot documents) {
                //if documents is empty go to home
                if (documents.size() == 0) {
                    Auth newAuth = new Auth();
                    newAuth.singout();
                } else {
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
                            notesList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : documents) {
                                //check null or empty document
                                if (document.exists()) {
                                    Notes result = document.toObject(Notes.class);
                                    result.setId(document.getData().get("id").toString());
                                    notesList.add(result);
                                }
                            }
                            //send data to view
                            RecyclerView rvNotesList = findViewById(R.id.rvNotesList);
                            rvNotesList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            //mount ClickListener
                            notesListAdapter = new NotesListAdapter(getApplicationContext(), notesList);
                            notesListAdapter.setClickListener(new NotesListAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    intent = new Intent(getApplicationContext(), NoteViewActivity.class);
                                    intent.putExtra("note", notesListAdapter.getItem(position));
                                    startActivity(intent);
                                }
                            });
                            rvNotesList.setAdapter(notesListAdapter);

                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mi_noteEdit:
                Auth logout = new Auth();
                logout.singout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

