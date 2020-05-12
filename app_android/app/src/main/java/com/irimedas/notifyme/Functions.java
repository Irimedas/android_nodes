package com.irimedas.notifyme;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.adapters.NotesAdapter;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.firebase.Database;
import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;

public class Functions {

    private Context context;
    private Auth auth;
    private RecyclerView rvList_notes;
    private static NotesAdapter adapter;

    public Functions(Auth auth,RecyclerView rvList_notes, Context context ) {
        this.auth = auth;
        this.rvList_notes = rvList_notes;
        this.context = context;
    }

    public void onload() {

        FirebaseUser user = null;

        auth.singIn();
        user = auth.getCurrentUser();
        if (user == null) {
            // sent to user in login
            auth.createAccount();
        } else {

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
                            ArrayList<Notes> listNotes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : documents) {
                                Notes result = document.toObject(Notes.class);
                                listNotes.add(result);
                            }

                            adapter = new NotesAdapter(listNotes, context);
                            RecyclerView notesView = rvList_notes;
                            notesView.setAdapter(adapter);
                        }
                    });
                }
            });
        }
    }
}
