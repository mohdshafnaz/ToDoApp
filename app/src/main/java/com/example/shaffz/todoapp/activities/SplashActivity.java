package com.example.shaffz.todoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;
import com.example.shaffz.todoapp.BuildConfig;
import com.example.shaffz.todoapp.R;
import com.example.shaffz.todoapp.utils.Constants;




public class SplashActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private TextView mTextViewVersionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mTextViewVersionNumber=findViewById(R.id.textViewVersion);
        mTextViewVersionNumber.setText("v "+BuildConfig.VERSION_NAME);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleParameters.FRAGMENT_NAME, Constants.FragmentKeyNames.FRAGMENT_HOME);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                // close this activity
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
