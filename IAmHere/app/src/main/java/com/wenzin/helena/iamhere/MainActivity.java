package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";

    Mailer mailer = new Mailer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void sendMailHomeButton(View view) {
        mailer.sendMail("Hemma!", "Hej! Nu har jag kommit hem!");
        startDisplayMessageMailSent();
    }

    public void sendMailSchoolButton(View view) {
        mailer.sendMail("I skolan!", "Hej! Nu är jag i skolan!");
        startDisplayMessageMailSent();
    }

    private void startDisplayMessageMailSent() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Mail skickat"; //TODO: Put in xml file
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
