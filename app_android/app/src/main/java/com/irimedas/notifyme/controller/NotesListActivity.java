package com.irimedas.notifyme.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.irimedas.notifyme.R;
import com.irimedas.notifyme.adapters.NotesListAdapter;
import com.irimedas.notifyme.models.Notes;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesListAdapter.ItemClickListener {

    private NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);

        // data to populate the RecyclerView with
        ArrayList<Notes> notesList = new ArrayList<>();

        for (int i = 1; i<=5; i++){
            notesList.add(new Notes("Nota " + i,"Descripción de nota " + i));
        }


        // set up the RecyclerView
        RecyclerView rvNotesList = findViewById(R.id.rvNotesList);
        rvNotesList.setLayoutManager(new LinearLayoutManager(this));
        notesListAdapter = new NotesListAdapter(this, notesList);
        notesListAdapter.setClickListener(this);
        rvNotesList.setAdapter(notesListAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + notesListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
