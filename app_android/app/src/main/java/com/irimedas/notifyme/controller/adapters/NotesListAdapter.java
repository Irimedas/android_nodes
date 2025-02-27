package com.irimedas.notifyme.controller.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irimedas.notifyme.R;
import com.irimedas.notifyme.controller.MainActivity;
import com.irimedas.notifyme.models.Notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    static public List<Notes> notes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public NotesListAdapter(Context context, List<Notes> notes) {
        this.mInflater = LayoutInflater.from(context);
        this.notes = notes;
        sendtoPreference();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.note_list_object, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String body = notes.get(position).getBody();
        String newBody = "";

        if (body.length() >= 50) {
            newBody = body.substring(0, 50);
            newBody += "...";

        } else {
            newBody = body;
        }

        holder.tvTitle.setText(title);
        holder.tvBody.setText(newBody);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return notes.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvBody;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Notes getItem(int id) {
        return notes.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    //send List notes in sharepreference
    public void sendtoPreference() {
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        ArrayList<String> data = new ArrayList<>();
        for (Notes element : notes) {
            data.add(element.getId());
        }

        String json = data.toString();
        prefsEditor.putString("Note", json);
        prefsEditor.commit();
    }

    //read sharepreference
    public static List<String> readtoPreferent() {
        SharedPreferences mPrefs = MainActivity.context.getSharedPreferences("Notes", Context.MODE_PRIVATE);
        String json = mPrefs.getString("Note", null);
        if (json != null) {
            String clear = json.substring(1, (json.length() - 1)).replaceAll(" ", "");
            String[] obj = clear.split(",");
            List<String> result = new ArrayList<>();
            result = Arrays.asList(obj);
            return result;
        } else {
            return null;
        }
    }


}
