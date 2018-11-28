package com.example.shaffz.todoapp.sqlite_room_db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.example.shaffz.todoapp.sqlite_room_db.AppDatabase;

@Entity(tableName = AppDatabase.TABLE_NAME_TODO)
public class ToDoDatabase {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int todo_id;
    public String name;
    public String description;
    public boolean is_done;




}
