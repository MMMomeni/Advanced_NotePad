package com.mmomeni.notepad;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView noteTitle; // we can make these three public too, but we should never make them private
    TextView description;
    TextView dateTime;

    MyViewHolder(View view){ //this objects will hold references to the items in our notes_list layout
        super(view);
        noteTitle = view.findViewById(R.id.noteTitle);
        description = view.findViewById(R.id.description);
        dateTime = view.findViewById(R.id.dateTime);
    }
}
