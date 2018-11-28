package com.example.shaffz.todoapp.sqlite_room_db.daos;

import android.arch.persistence.room.*;
import com.example.shaffz.todoapp.sqlite_room_db.AppDatabase;
import com.example.shaffz.todoapp.sqlite_room_db.entities.ToDoDatabase;

import java.util.List;

@Dao
public interface TodoDaoAccess {
    @Insert
    long insertTodo(ToDoDatabase todo);

    @Insert
    void insertTodoList(List<ToDoDatabase> todoList);

    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_TODO)
    List<ToDoDatabase> fetchAllTodos();



    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_TODO + " WHERE todo_id = :todoId")
    ToDoDatabase fetchTodoListById(int todoId);

    @Update
    int updateTodo(ToDoDatabase todo);

    @Delete
    int deleteTodo(ToDoDatabase todo);
}
