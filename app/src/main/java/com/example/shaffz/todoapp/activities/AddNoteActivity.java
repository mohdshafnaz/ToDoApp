package com.example.shaffz.todoapp.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.shaffz.todoapp.R;
import com.example.shaffz.todoapp.sqlite_room_db.AppDatabase;
import com.example.shaffz.todoapp.sqlite_room_db.entities.ToDoDatabase;
import com.example.shaffz.todoapp.utils.Constants;


public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextTitle;
    private EditText mEditTextDesc;
    private Button btnDone;
    private Button btnDelete;
    private Button btnMarkasDone;
    private boolean isNewTodo = false;
    private AppDatabase myDatabase;
    private boolean isFlagDone = false;
    private ToDoDatabase updateTodo;
    private String mStrTitle;
    private String mStrDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_note);
        //Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setActionbar();
        initView();
        //  myDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();
        myDatabase = AppDatabase.getInstance(getApplicationContext());
        checkBundle();
        initListener();

    }

    private void setActionbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_note_title);
    }

    private void initView() {
        mEditTextTitle = findViewById(R.id.edTitle);
        mEditTextDesc = findViewById(R.id.edDescription);
        btnDone = findViewById(R.id.btnDone);
        btnDelete = findViewById(R.id.btnDelete);
        btnMarkasDone = findViewById(R.id.btnMarkAsDone);
    }

    private void checkBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int todo_id = bundle.getInt(Constants.BundleParameters._ID, -100);

            if (todo_id == -100)
                isNewTodo = true;

            if (!isNewTodo) {
                fetchTodoById(todo_id);
                btnDelete.setVisibility(View.VISIBLE);
                btnMarkasDone.setVisibility(View.VISIBLE);
            }
        } else {
            isNewTodo = true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AddNoteActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListener() {
        btnDone.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnMarkasDone.setOnClickListener(this);

    }

    @SuppressLint("StaticFieldLeak")
    private void fetchTodoById(final int todo_id) {
        new AsyncTask<Integer, Void, ToDoDatabase>() {
            @Override
            protected ToDoDatabase doInBackground(Integer... params) {

                return myDatabase.todoDaoAccess().fetchTodoListById(params[0]);

            }

            @Override
            protected void onPostExecute(ToDoDatabase todo) {
                super.onPostExecute(todo);
                mEditTextTitle.setText(todo.name);
                mEditTextDesc.setText(todo.description);
                isFlagDone = todo.is_done;
                if (todo.is_done) {
                    btnMarkasDone.setText("Mark as Undone");
                } else {
                    btnMarkasDone.setText("Mark as Done");

                }
                updateTodo = todo;
            }
        }.execute(todo_id);

    }

    @SuppressLint("StaticFieldLeak")
    private void insertRow(ToDoDatabase todo) {
        new AsyncTask<ToDoDatabase, Void, Long>() {
            @Override
            protected Long doInBackground(ToDoDatabase... params) {
                return myDatabase.todoDaoAccess().insertTodo(params[0]);
            }

            @Override
            protected void onPostExecute(Long id) {
                super.onPostExecute(id);

       /*         Intent intent = getIntent();
                intent.putExtra("isNew", true).putExtra("id", id);
                setResult(RESULT_OK, intent);*/
                Toast.makeText(AddNoteActivity.this, "Inserted", Toast.LENGTH_SHORT).show();

                finish();
            }
        }.execute(todo);

    }

    @SuppressLint("StaticFieldLeak")
    private void deleteRow(ToDoDatabase todo) {
        new AsyncTask<ToDoDatabase, Void, Integer>() {
            @Override
            protected Integer doInBackground(ToDoDatabase... params) {
                return myDatabase.todoDaoAccess().deleteTodo(params[0]);
            }

            @Override
            protected void onPostExecute(Integer number) {
                super.onPostExecute(number);

            /*    Intent intent = getIntent();
                intent.putExtra("isDeleted", true).putExtra("number", number);
                setResult(RESULT_OK, intent);*/
                Toast.makeText(AddNoteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                finish();
            }
        }.execute(todo);

    }


    @SuppressLint("StaticFieldLeak")
    private void updateRow(ToDoDatabase todo) {
        new AsyncTask<ToDoDatabase, Void, Integer>() {
            @Override
            protected Integer doInBackground(ToDoDatabase... params) {
                return myDatabase.todoDaoAccess().updateTodo(params[0]);
            }

            @Override
            protected void onPostExecute(Integer number) {
                super.onPostExecute(number);

          /*      Intent intent = getIntent();
                intent.putExtra("isNew", false).putExtra("number", number);
                setResult(RESULT_OK, intent);*/
                Toast.makeText(AddNoteActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.execute(todo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                mStrTitle = mEditTextTitle.getText().toString();
                mStrDesc = mEditTextDesc.getText().toString();
                if (inputValidation(mStrTitle, mStrDesc)) {
                    if (isNewTodo) {
                        ToDoDatabase todo = new ToDoDatabase();
                        todo.name = mStrTitle;
                        todo.description = mStrDesc;
                        todo.is_done = isFlagDone;
                        insertRow(todo);
                    } else {

                        updateTheRow();
                    }
                }
                break;

            case R.id.btnDelete:
                deleteRow(updateTodo);
                break;

            case R.id.btnMarkAsDone:
                if (isFlagDone) {
                    isFlagDone = false;
                    btnMarkasDone.setText("Mark as done");
                } else {
                    isFlagDone = true;
                    btnMarkasDone.setText("Mark as Undone");

                }
                updateTheRow();
                break;


        }
    }

    private boolean inputValidation(String mStrTitle, String mStrDesc) {
        /**
         * Title
         */
        if (mStrTitle == null || mStrTitle.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_string), Toast.LENGTH_SHORT).show();
            requestFocus(mEditTextTitle);
            return false;
        }

        /**
         * Description
         */
        else if (mStrDesc == null || mStrDesc.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_string), Toast.LENGTH_SHORT).show();
            requestFocus(mEditTextDesc);
            return false;
        } else {
            return true;
        }
    }

    private void requestFocus(EditText view) {
        view.requestFocus();
    }


    private void updateTheRow() {
        updateTodo.name = mEditTextTitle.getText().toString();
        updateTodo.description = mEditTextDesc.getText().toString();
        updateTodo.is_done = isFlagDone;
        updateRow(updateTodo);
    }
}



