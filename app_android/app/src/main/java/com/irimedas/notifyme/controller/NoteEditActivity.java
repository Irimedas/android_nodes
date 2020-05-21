package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.controller.adapters.NotesListAdapter;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.models.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Notes note;
    private TextView tvTitle;
    private TextView tvBody;
    private ImageButton ib_checkNote;
    private ImageButton ib_cancelNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);

        note = (Notes) getIntent().getSerializableExtra("note");

        //Imagebuttons
        ib_checkNote= findViewById(R.id.ib_checkNote);
        ib_checkNote.setOnClickListener(this);
        ib_cancelNote= findViewById(R.id.ib_cancelNote);
        ib_cancelNote.setOnClickListener(this);
        //texview
        tvTitle = findViewById(R.id.tvTitle);
        tvBody = findViewById(R.id.tvBody);

        tvTitle.setText(note.getTitle());
        tvBody.setText(note.getBody());
    }

    @Override
    public void onClick(View v) {
        if(v!=null){
            int id = v.getId();
            Intent intent = new Intent(getApplicationContext(), NotesListActivity.class);
            switch (id){
                case R.id.ib_checkNote:

                    //get id object
                    String idNote = note.getId();
                    //prepare data
                    String titleNote = tvTitle.getText().toString();
                    String bodyNote = tvBody.getText().toString();
                    String idUser = MainActivity.user.getUid();
                    Toast.makeText(getApplicationContext(), titleNote+"\n"+bodyNote+"\n"+idNote+"\n"+idUser, Toast.LENGTH_SHORT).show();
                    //boolean found Duplicates
                    Boolean exist=false;
                    List<String> listNotes = NotesListAdapter.readtoPreferent();
                    for (String element: listNotes) {
//                        Toast.makeText(getApplicationContext(), "test=> "+element+ "\n"+idNote, Toast.LENGTH_SHORT).show();
                        if(element.equalsIgnoreCase(idNote)){
//                            Toast.makeText(getApplicationContext(), "Find "+element, Toast.LENGTH_SHORT).show();
                            // if exist create newNote
                         exist=true;
                        }
                    }
                    if(exist){
                        //upadate note
                        Map<String,Object> data = new HashMap<>();
                        data.put("title",titleNote);
                        data.put("body",bodyNote);
                        note.update(idNote,data);
                    }else {
                        //update id from db
                        Database.editId(idNote);
                        // if exist create newNote
                        note.setTitle(titleNote);
                        note.setBody(bodyNote);
                        // add user in userlist
                        List<String> dataUser = new ArrayList<>();
                        dataUser.add(idUser);
                        note.setUsers_ids(dataUser);
                        note.save();
                        //note.createNote(idUser,titleNote,bodyNote);
                    }

                    //check if object exitst in db
              /*      note.find(note.getId());
                    note.readData(new Database.FirestoreCallback() {
                        @Override
                        public void onCallback(QuerySnapshot documents) {
                            for (QueryDocumentSnapshot document : documents) {
                                Notes result = document.toObject(Notes.class);
                                // if exist create newNote
                                if(result.getId().equalsIgnoreCase(note.getId())){
                                    note.createNote(idUser,titleNote,bodyNote);
                                }else{
                                 //upadate note
                                    Map<String,Object> data = new HashMap<>();
                                   data.put("title",titleNote);
                                   data.put("body",bodyNote);
                                   note.update(idNote,data);
                                }

                            }
                            Intent intent = new Intent(getApplicationContext(), NotesListActivity.class);
                            startActivity(intent);
                        }
                    });*/
                    //count match
                    startActivity(intent);
                    break;
                case R.id.ib_cancelNote:

                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
