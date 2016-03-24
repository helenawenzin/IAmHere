package com.wenzin.helena.iamhere;

import android.os.AsyncTask;
import com.sendgrid.*;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Helena on 2016-03-18.
 */
public class Mailer {

    SettingsFromUser settingsFromUser = new SettingsFromUser();

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
        System.out.println(result);
    }

    private class SendEmailWithSendGrid extends AsyncTask<Hashtable<String,String>, Void, String> {

        @Override
        protected String doInBackground(Hashtable<String,String>... params) {
            SendGrid sendgrid = new SendGrid("");

            SendGrid.Email email = new SendGrid.Email();

            email.addTo(settingsFromUser.getTo());
            email.setFrom(settingsFromUser.getFrom());
            email.setSubject(params[0].get("subject"));
            email.setHtml(params[0].get("body"));

            SendGrid.Response response = null;
            try {
                response = sendgrid.send(email);
            } catch (SendGridException e) {
                e.printStackTrace();
            }
            System.out.println("Status=" + response.getStatus() + " Message: " + response.getMessage());
            return response.toString();
        }
    }
}
