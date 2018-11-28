package com.example.shaffz.todoapp.sqlite_room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.shaffz.todoapp.sqlite_room_db.daos.TodoDaoAccess;
import com.example.shaffz.todoapp.sqlite_room_db.entities.ToDoDatabase;


@Database(entities = {ToDoDatabase.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "to_do_db";
    public static final String TABLE_NAME_TODO = "todo_table";

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context mContext) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(), AppDatabase.class, DATABASE_NAME).
                            allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TodoDaoAccess todoDaoAccess();
}
