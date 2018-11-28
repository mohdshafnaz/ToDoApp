package com.example.shaffz.todoapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.shaffz.todoapp.R;
import com.example.shaffz.todoapp.fragments.HomeFragment;
import com.example.shaffz.todoapp.interfaces.FragmentInterface;
import com.example.shaffz.todoapp.utils.Constants;

import static com.example.shaffz.todoapp.utils.Constants.FragmentKeyNames.FRAGMENT_HOME;


public class MainActivity extends AppCompatActivity implements FragmentInterface {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  mToolBar = (Toolbar) findViewById(R.id.toolbarToDoApp);
//   setSupportActionBar(mToolBar);
if(getSupportActionBar()!=null){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
        checkBundle();

    }


    @Override
    public void action(int action, Bundle args) {
        switch (action) {

            case Constants.FragmentInterfaceConstants.ACTION_HOME:
                showHomeScreen();
                break;

        }
    }

    private void showHomeScreen() {
        HomeFragment homeFragment = new HomeFragment();
        addFragment(homeFragment, HomeFragment.TAG);
    }



    private void checkBundle() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            String fragName = bundle.getString(Constants.BundleParameters.FRAGMENT_NAME);
            if (fragName != null) {
                if (fragName.equals(FRAGMENT_HOME)) {
                    this.action(Constants.FragmentInterfaceConstants.ACTION_HOME, bundle);
                } else
                    Log.e("HomeFrag", "Bundle Error");
            }
        }
    }

    @Override
    public void showActionBar(boolean show) {
        if (getSupportActionBar() != null) {
            if (show) {
                getSupportActionBar().show();
            } else {
                getSupportActionBar().hide();
            }
        }
    }

    @Override
    public void showHomeButton(boolean show) {
        if (getSupportActionBar() != null) {

            if (show) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setTitle(title);
        }
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction ft =
                getSupportFragmentManager()
                        .beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Do u", Toast.LENGTH_SHORT).show();
        backButtonPressed();
    }

    private void backButtonPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }



}
