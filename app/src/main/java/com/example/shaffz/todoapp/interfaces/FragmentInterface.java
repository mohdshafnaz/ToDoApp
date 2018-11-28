package com.example.shaffz.todoapp.interfaces;

import android.os.Bundle;



public interface FragmentInterface {

    void action(int action, Bundle args);

    void showActionBar(boolean show);

    void showHomeButton(boolean show);

    void setActionBarTitle(String title);
}
