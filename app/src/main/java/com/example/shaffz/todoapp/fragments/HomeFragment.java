package com.example.shaffz.todoapp.fragments;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shaffz.todoapp.R;
import com.example.shaffz.todoapp.activities.AddNoteActivity;
import com.example.shaffz.todoapp.adapters.RecyclerViewAdapter;
import com.example.shaffz.todoapp.interfaces.FragmentInterface;
import com.example.shaffz.todoapp.sqlite_room_db.AppDatabase;
import com.example.shaffz.todoapp.sqlite_room_db.entities.ToDoDatabase;
import com.example.shaffz.todoapp.utils.Constants;
import com.example.shaffz.todoapp.views.ErrorProgressView;

import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapter.ClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private AppDatabase myDatabase;
    private FragmentInterface mFragmentInterface;
    private FloatingActionButton mFabAddNote;
    private ErrorProgressView mErrorProgressView;

    public HomeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInterface) {
            mFragmentInterface = (FragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Fragmentinterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInterface = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  myDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();
        myDatabase = AppDatabase.getInstance(getActivity());

        if (mFragmentInterface != null) {
            mFragmentInterface.setActionBarTitle(getString(R.string.title_home));
            mFragmentInterface.showActionBar(true);
            mFragmentInterface.showHomeButton(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
    }

    private void initListener() {
        mFabAddNote.setOnClickListener(this);
    }

    @Override
    public void onResume() {

        super.onResume();
        loadAllTodos();

    }

    private void initView(View view) {
        mFabAddNote = view.findViewById(R.id.fabAddNote);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        mErrorProgressView = view.findViewById(R.id.errorView);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddNote:
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void launchIntent(int id, boolean is_done) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleParameters._ID, id);
        Intent intent = new Intent(getActivity(), AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllTodos() {
        new AsyncTask<String, Void, List<ToDoDatabase>>() {
            @Override
            protected List<ToDoDatabase> doInBackground(String... params) {
                return myDatabase.todoDaoAccess().fetchAllTodos();
            }

            @Override
            protected void onPostExecute(List<ToDoDatabase> todoList) {
                recyclerViewAdapter.updateTodoList(todoList);

                if (todoList.size()>0) {
                    mErrorProgressView.setVisibility(View.GONE);
                } else {
                    mErrorProgressView.setVisibility(View.VISIBLE);
                    mErrorProgressView.setErrorType(ErrorProgressView.TYPE_EMPTY);                }
            }
        }.execute();
    }
}
