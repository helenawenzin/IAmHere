package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMailHomeButton(View view) {
        System.out.println("Jag skickar mail... Hemma!");
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
       // final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Jag är här");
        intent.putExtra(Intent.EXTRA_TEXT, "Nu vill jag meddela var jag är... Hemma");
        intent.setData(Uri.parse("mailto:helena.wenzin@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);


    }

    public void sendMailSchoolButton(View view) {
        System.out.println("Jag skickar mail... Skolan!");
    }
}
