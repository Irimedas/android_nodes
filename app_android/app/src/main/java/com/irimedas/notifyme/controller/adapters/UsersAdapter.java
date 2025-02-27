package com.irimedas.notifyme.controller.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.firebase.firestore.QuerySnapshot;
import com.irimedas.notifyme.R;

import com.irimedas.notifyme.models.Notes;
import com.irimedas.notifyme.models.Users;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private ArrayList<Users> list;
    private Context context;

    public UsersAdapter(QuerySnapshot elements, Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        //guardem els elements
        for (QueryDocumentSnapshot document : elements) {
            Users result = document.toObject(Users.class);
            list.add(result);
        }
    }


    // RecycleView

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public TextView tvEmail;
        public TextView tvShare_notes;
        public TextView tvUser_notes;

        // declara el contingut de la vista del RecyclerView
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvShare_notes = itemView.findViewById(R.id.tvShare_notes);
            tvUser_notes = itemView.findViewById(R.id.tvUser_notes);
            //posem el listener en cada element passat per parametre
            itemView.setOnClickListener(this);
        }

        //metode a implemantar la interficiea View.onclickListener
        @Override
        public void onClick(View v) {
            int positcio = getAdapterPosition();
            Log.i("test", String.valueOf(positcio));
            // seleciona el usuari
            Users user = list.get(positcio);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infla la vista amb el layout del pare
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_llista, parent, false);
        // retora una ViewHolder amb la view creada anteriorment.
        return new ViewHolder(view);
    }

    /// Montar cada element en la vista segons parametre
    // enllaç dades amb vista
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Users item = list.get(position);
        if (item != null) {
            String llistShare_notes = "";
            holder.tvName.setText(item.getName());
            holder.tvEmail.setText(item.getEmail());
            holder.tvShare_notes.setText(llistShare_notes);
        } else {
            holder.tvName.setText("null");
            holder.tvEmail.setText("null");
            holder.tvShare_notes.setText("null");
            holder.tvUser_notes.setText("null");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
