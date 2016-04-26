package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        EditText textMailTo = (EditText) findViewById(R.id.save_Email_Send_To);
        EditText textMailFrom = (EditText) findViewById(R.id.save_Email_Send_From);

        textMailTo.setText(pref.getString("sendTo", ""));
        textMailFrom.setText(pref.getString("sendFrom", ""));
    }

    public void saveSettings(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        EditText textMailTo = (EditText) findViewById(R.id.save_Email_Send_To);
        EditText textMailFrom = (EditText) findViewById(R.id.save_Email_Send_From);

        editor.putString("sendTo", textMailTo.getText().toString());
        editor.putString("sendFrom", textMailFrom.getText().toString());
        editor.apply();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = getString(R.string.seetingsMessage);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
