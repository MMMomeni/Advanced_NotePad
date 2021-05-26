package com.mmomeni.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "NoteAdapter";
    private List<Note> noteList;
    private MainActivity mainAct;

    NoteAdapter(List<Note> ntList, MainActivity ma) {
        this.noteList = ntList;
        mainAct = ma;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list, parent, false);

        itemView.setOnClickListener(mainAct); // means that main activity owns the onClickListener
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note n = noteList.get(position);

        holder.noteTitle.setText(n.getTitle());

        if (n.getDesc().length() < 80){
            holder.description.setText(n.getDesc());
        }
        else{
            holder.description.setText(n.getDesc().substring(0, 60) + "...");
        }

        holder.dateTime.setText(n.getLastDate().toString());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
