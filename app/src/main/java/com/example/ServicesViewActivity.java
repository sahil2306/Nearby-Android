package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.core.app.NavUtils;

public class ServicesViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // etc...
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
