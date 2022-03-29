package com.example.tasklist.db;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tasks {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "course")
    public String course;

    @ColumnInfo(name = "deadline")
    public String deadline;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "doneTime")
    public String doneTime;

}
