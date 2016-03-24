package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void saveSettings(View view) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Settings är sparade";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
