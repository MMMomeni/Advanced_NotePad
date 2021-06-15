package com.mmomeni.notepad;

import androidx.annotation.NonNull;

import java.util.Date;

public class Note implements Comparable{

    private String title;
    private String description;
    private Date lastDate;

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


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
