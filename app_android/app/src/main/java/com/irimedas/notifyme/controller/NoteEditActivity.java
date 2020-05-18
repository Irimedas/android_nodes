package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.irimedas.notifyme.R;
import com.irimedas.notifyme.models.Notes;

public class NoteEditActivity extends AppCompatActivity {

    private Notes note;
    private TextView tvTitle;
    private TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);

        note = (Notes) getIntent().getSerializableExtra("note");

        tvTitle = findViewById(R.id.tvTitle);
        tvBody = findViewById(R.id.tvBody);

        tvTitle.setText(note.getTitle());
        tvBody.setText(note.getBody());
    }
}
