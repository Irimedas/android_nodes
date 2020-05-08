package com.irimedas.notifyme.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;
import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Notes> list;
    private Context context;

    public NotesAdapter(QuerySnapshot elements, Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        //guardem els elements
        for (QueryDocumentSnapshot document : elements) {
            Notes result = document.toObject(Notes.class);
            list.add(result);
        }
    }


    // RecycleView

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvBody;
        public TextView tvFiles;


        // declara el contingut de la vista del RecyclerView
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvFiles= itemView.findViewById(R.id.tvFiles);
            //posem el listener en cada element passat per parametre
            itemView.setOnClickListener(this);
        }
        //metode a implemantar la interficiea View.onclickListener
        @Override
        public void onClick(View v) {
            int positcio = getAdapterPosition();
            Log.i("test",String.valueOf(positcio));
            //mostraPopapMenu(v,positcio);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infla la vista amb el layout del pare
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_llista, parent, false);
        // retora una ViewHolder amb la view creada anteriorment.
        return new ViewHolder(view);
    }

    /// Montar cada element en la vista segons parametre
    // enllaç dades amb vista
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Notes item = list.get(position);
        if(item!=null){
        String llistNotesfiles="";
        if(item.getNote_files()!=null) {
            for (String element : item.getNote_files()) {
                llistNotesfiles += element + " ";
            }
        }

        holder.tvTitle.setText(item.getTitle());
        holder.tvBody.setText(item.getBody());
        holder.tvFiles.setText(llistNotesfiles);

        }else {
            holder.tvTitle.setText("null");
            holder.tvBody.setText("null");
            holder.tvFiles.setText("null");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
