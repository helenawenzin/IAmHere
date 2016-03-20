package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";

    Mailer mailer = new Mailer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMailHomeButton(View view) {
        mailer.sendMail();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Mail skickat"; //TODO: Put in xml file
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void sendMailSchoolButton(View view) {
        System.out.println("Jag skickar mail... Skolan!");

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Mail skickat"; //TODO: Put in xml file
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
