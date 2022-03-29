package com.example.tasklist.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        Tasks.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskListDao taskListDao();
    public static AppDatabase INSTANCE;
    public static AppDatabase getDBinstance(Context context) {
        if(INSTANCE == null ) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDB")
            .allowMainThreadQueries()
            .build();
        }
        return INSTANCE;
    }
}
