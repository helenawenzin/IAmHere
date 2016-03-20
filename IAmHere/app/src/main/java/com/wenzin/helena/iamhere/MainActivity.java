package com.wenzin.helena.iamhere;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Mailer mailer = new Mailer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMailHomeButton(View view) {
        mailer.sendMail();
    }

    public void sendMailSchoolButton(View view) {
        System.out.println("Jag skickar mail... Skolan!");
    }
}
