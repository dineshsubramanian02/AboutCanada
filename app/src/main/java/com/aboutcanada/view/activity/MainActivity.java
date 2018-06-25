package com.aboutcanada.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aboutcanada.view.fragments.NewsFragment;
/**
 * Created by Dinesh on 25/06/18.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, NewsFragment.newInstance()).commit();
    }
}
