package com.mmomeni.notepad;

import androidx.annotation.NonNull;

import java.util.Date;

public class Note {

    private String title;
    private String description;
    private Date lastDate;

    private static int a = 1; //it has to be static otherwise wont be updated

    Note(String t, String d) {
        this.title = t;
        this.description = d;
        lastDate = new Date();

    }


    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return description;
    }

    public Date getLastDate() { return lastDate; }

    public void setLastDate(Date d){
        this.lastDate = d;
    }

    @NonNull
    @Override
    public String toString() {
        return title + description;
    }



}
