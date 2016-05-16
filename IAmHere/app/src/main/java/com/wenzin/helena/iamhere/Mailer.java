package com.wenzin.helena.iamhere;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.sendgrid.*;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

//This class handles sending the email to the parent from SendGrid
public class Mailer {

    private SharedPreferences pref;

    public Mailer(SharedPreferences pref) {
        this.pref = pref;
    }

    //Prepare the email for sending
    public void sendMail(String subject, String body) {
        String result = "";
        Hashtable<String,String> params = new Hashtable<String,String>();
        params.put("subject", subject);
        params.put("body", body);
        // Send the Email
        SendEmailWithSendGrid sendEmailWithSendGrid = new SendEmailWithSendGrid();
        try {
            result = sendEmailWithSendGrid.execute(params).get();
        } catch (InterruptedException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        }
    }

    //Send the email with SendGrid
    private class SendEmailWithSendGrid extends AsyncTask<Hashtable<String,String>, Void, String> {

        @Override
        protected String doInBackground(Hashtable<String,String>... params) {
            SendGrid sendgrid = new SendGrid("SendGrid API key");

            SendGrid.Email email = new SendGrid.Email();

            email.addTo(pref.getString("sendTo", ""));
            email.setFrom(pref.getString("sendFrom", ""));
            email.setSubject(params[0].get("subject"));
            email.setHtml(params[0].get("body"));

            SendGrid.Response response = null;
            try {
                response = sendgrid.send(email);
            } catch (SendGridException e) {
                e.printStackTrace();
            }
            return response.toString();
        }
    }
}
