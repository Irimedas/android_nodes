package com.irimedas.notifyme.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

public class NoteViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Notes note;
    private TextView tvTitle;
    private TextView tvBody;
    private Intent intent;
    private ImageButton ib_editNote;
    private ImageButton ib_ShareNote;
    private ImageButton ib_deleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);
        //buttons
        ib_editNote = findViewById(R.id.ib_editNote);
        ib_editNote.setOnClickListener(this);
        ib_ShareNote = findViewById(R.id.ib_shareNote);
        ib_ShareNote.setOnClickListener(this);
        ib_deleteNote = findViewById(R.id.ib_deleteNote);
        ib_deleteNote.setOnClickListener(this);

        note = (Notes) getIntent().getSerializableExtra("note");

        tvTitle = findViewById(R.id.tvTitle);
        tvBody = findViewById(R.id.tvBody);

        tvTitle.setText(note.getTitle());
        tvBody.setText(note.getBody());
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            int id = v.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            switch (id) {
                case R.id.ib_editNote:
                    intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                    intent.putExtra("note", note);
                    startActivity(intent);
                    break;
                case R.id.ib_deleteNote:

                    builder.setTitle(R.string.note_delete_title);
                    builder.setMessage(R.string.note_delete_message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "idnote "+note.getId(), Toast.LENGTH_SHORT).show();
                            Database.setPk(note.getId());
                            note.remove();
                            intent = new Intent(getApplicationContext(), NotesListActivity.class);
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
                    break;
                case R.id.ib_shareNote:
                    final EditText edtText = new EditText(v.getContext());
                    builder.setTitle(R.string.note_share_title);
                    builder.setMessage(R.string.note_share_message);
                    builder.setCancelable(false);
                    builder.setView(edtText);
                    builder.setPositiveButton(R.string.note_add_accept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Share
                            // find user with email
                            Users findUser = new Users();
                            findUser.where("email", "=", edtText.getText().toString());
                            findUser.readData(new Database.FirestoreCallback() {
                                @Override
                                public void onCallback(QuerySnapshot documents) {
                                    if (documents.getDocuments().size() > 0) {
                                        final Users user = documents.getDocuments().get(0).toObject(Users.class);
                                        Database.setCollection("Notes");
                                        note.shareNote(user.getId());
                                    } else {
                                        Toast.makeText(NoteViewActivity.this, R.string.invalid_username, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), R.string.note_cancel, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                    break;
                default:
                    break;
            }
        }
    }

}
