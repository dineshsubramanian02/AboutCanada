package com.aboutcanada.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Dinesh on 25/06/18.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    public static int TIME_SEC=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //showing splash screen for 5 seconds
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //wait for 5seconds in the splash screen
                    sleep(TIME_SEC);
                }
                catch (Exception e)
                {
                    Log.d(TAG,"Error while displaying splash screen");
                }
                finally
                {
                    //launch homescreen
                    Intent myIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(myIntent);
                    finish();// close the splash activity
                }
            }
        };
        timer.start();//start the timer
    }
}
