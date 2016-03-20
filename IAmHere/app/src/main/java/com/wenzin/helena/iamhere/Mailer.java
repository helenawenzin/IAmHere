package com.wenzin.helena.iamhere;

import android.os.AsyncTask;
import com.sendgrid.*;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Helena on 2016-03-18.
 */
public class Mailer {

    public void sendMail() {
        String result = "";
        Hashtable<String,String> params = new Hashtable<String,String>();
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
        System.out.println(result);
    }

    private class SendEmailWithSendGrid extends AsyncTask<Hashtable<String,String>, Void, String> {

        @Override
        protected String doInBackground(Hashtable<String,String>... params) {
            //TODO: Use params instead of hardcoded values in email
            Hashtable<String,String> h = params[0];
            SendGrid sendgrid = new SendGrid("");

            SendGrid.Email email = new SendGrid.Email();

            email.addTo("helena.wenzin@gmail.com");
            email.setFrom("helena.wenzin@gmail.com");
            email.setSubject("Simon är nu hemma!");
            email.setHtml("Hej! Nu har jag kommit hem! //Simon");

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
