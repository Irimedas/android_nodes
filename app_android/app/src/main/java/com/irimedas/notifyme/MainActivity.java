package com.irimedas.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.irimedas.notifyme.adapters.NotesAdapter;
import com.irimedas.notifyme.firebase.Auth;
import com.irimedas.notifyme.fragments.LoginFragment;


public class MainActivity extends AppCompatActivity {

    public static Context context;
    private static Auth auth;
    //public static RecyclerView View;
    public static RecyclerView rvList_notes;
    private static NotesAdapter adapter;
    public static Functions functions;
    public static FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setContentView(R.layout.fragment_login);
        this.context = this.getApplicationContext();
       /* View = findViewById(R.id.rvView);
        View.setLayoutManager(new LinearLayoutManager(this));
        View.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));*/

        rvList_notes = findViewById(R.id.rvList_notes);
        rvList_notes.setLayoutManager(new LinearLayoutManager(this));
        rvList_notes.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        //read sharepreferents
        //if exist Auth read
        //else send to view load


        //auth = new Auth("trolldeprueva@gmail.com","testing",this);
        //auth = new Auth();
       // user = auth.readtoPreferent();

        //read prefrensces
        //ArrayList<String> userPreference = new Auth().readtoPreferent();
        String[] userPreference = new Auth().readtoPreferent();
//        Toast.makeText(this, userPreference.get(0)+"\n"+userPreference.get(1), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "TEST"+userPreference[0]+"\nTEST"+userPreference[1], Toast.LENGTH_SHORT).show();
        if(userPreference!=null) {
            auth = new Auth(userPreference[0], userPreference[1], this);
        }else{
            auth=new Auth();
        }
        functions = new Functions();

    }

    @Override
    public void onStart() {
        super.onStart();


//        user = null;

          auth.singIn();
          user = auth.getCurrentUser();


        if (user != null){
            //auth.goTohome(user);
            functions.onload(user);
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_layout, new NotesFragment());
            transaction.commit();*/
        }else {

           //setContentView(R.layout.fragment_login);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.replace(R.id.main_layout, new LoginFragment());
            transaction.add(R.id.main_layout,new LoginFragment());
            transaction.commit();
        }


       /* FirebaseUser user = null;

            auth.singIn();
            user = auth.getCurrentUser();
            if(user == null){
                auth.createAccount();
            }else{

            // find the user
            Users findUser = new Users ();
            findUser.find(user.getUid());
                findUser.readData(new Database.FirestoreCallback() {
                                      @Override
                                      public void onCallback(QuerySnapshot documents) {
                                          //catch first user
                                          final Users user = documents.getDocuments().get(0).toObject(Users.class);
                                          /*List<String> users_ids = new ArrayList<>();
                                          users_ids.add(user.getId());*/
                                       /*   Notes findNotes = new Notes();
                                          findNotes.in("users_ids",user.getId());
                                          findNotes.readData(new Database.FirestoreCallback() {
                                              @Override
                                              public void onCallback(QuerySnapshot documents) {
                                                  ArrayList<Notes> listNotes = new ArrayList<>();
                                                  for (QueryDocumentSnapshot document : documents) {
                                                      Notes result = document.toObject(Notes.class);
                                                     //result.shareNote("sñlfjsdlñfsjñlsdfasdñl");
                                                     //result.decomposeShareNote("sñlfjsdlñfsjñlsdfasdñl");
                                                      listNotes.add(result);
                                                  }

                                                  adapter= new NotesAdapter(listNotes,context);
                                                  RecyclerView notesView = rvList_notes;
                                                  notesView.setAdapter(adapter);
                                              }
                                          });
                                      }
                                  });

//            Notes newNote = new Notes();
//            newNote.createNote(user.getUid(),"this is Title","this is body");
            /*findUser.readData(new Database.FirestoreCallback() {
                @Override
                public void onCallback(QuerySnapshot documents) {
                    //catch first user
                    final Users user = documents.getDocuments().get(0).toObject(Users.class);

                   // user.show();
                    // find notes
                    final Notes findNotes = new Notes();
                    findNotes.find(user.getUser_notes().get(0));
                    findNotes.readData(new Database.FirestoreCallback() {

                        @Override
                        public void onCallback(QuerySnapshot documents) {
//                            List<String> users_ids = Arrays.asList("Users/"+user.getId());
//                            Map<String,Object> data = new HashMap<>();
//                            data.put("users_ids",users_ids);
//                            findNotes.update("S7MyAZdhAAWG8CcP63x1",data);

                            ArrayList<Notes> listNotes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : documents) {
                                Notes result = document.toObject(Notes.class);
                                listNotes.add(result);
                                ///
//                               List<String>users_ids = result.getUsers_ids();
//                                Users owner = new Users();
//                                owner.find(users_ids.get(0));
//                                owner.readData(new Database.FirestoreCallback() {
//                                    @Override
//                                    public void onCallback(QuerySnapshot documents) {
//                                        for (QueryDocumentSnapshot document : documents) {
//                                            document.toObject(Users.class).show();
//                                        }
//                                    }
//                                });
                            }

                            adapter= new NotesAdapter(listNotes,context);
                            RecyclerView notesView = rvList_notes;
                            notesView.setAdapter(adapter);
                        }
                    });

                }
            });*/

            //findUser.saveInPreferents();
            /*Notes newNotes = new Notes();
            Users userShare =  auth.readtoPreferent();
            String id = userShare.getId();
          //  Users readPreferent =auth.readtoPreferent();
           // readPreferent.show();
            // Name, email address, and profile photo Url
            /*String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            Toast.makeText(this,"name: "+name+"\nEmail: "+email+"\nuid: "+uid,Toast.LENGTH_LONG).show();
            //test db
                //all user
                Users allusers = new Users();
                allusers.all();
                allusers.get();

                //new user
                /*String idTest = "sñlfjsdlñfsjñlsdfasdñl";
                Users newUser = new Users(idTest,"test","test@test.com");
                newUser.save();*/
                /*//find user
                Users findUser = new Users();
                findUser.find(uid);
                //update user
                Users updateUser = new Users();
                Map<String,Object> dataUser = new HashMap<>();
                dataUser.put("name","Trolldeprueva");
                updateUser.update(uid,dataUser);
                //remove user
                newUser.remove();

                //Notes
                //allNotes*/
                /*Notes notes = new Notes();
                notes.all();
                notes.get();
                //new note
                /*Notes newNote = new Notes("test1","LOREM IPSUM");
                newNote.save();
                newNote.show();
                //find note
                Notes findNote = new Notes();
                findNote.find(newNote.getId());
                //update note
                Notes updateNote = new Notes();
                Map<String,Object> dataNote = new HashMap<>();
                dataNote.put("subtitle","RANDOM");
                updateNote.update(newNote.getId(),dataNote);
                //remove note
                newNote.remove();*/
//            }


   }
}
