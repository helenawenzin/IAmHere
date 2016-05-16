package com.wenzin.helena.iamhere;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

import static com.google.android.gms.location.LocationServices.API;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";
    private static final int MY_PERMISSIONS_REQUEST_TO_ACCESS_LOCATION = 222; //can be any number

    private SharedPreferences pref;
    private Mailer mailer;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGoogleApiClient();
        setContentView(R.layout.activity_main);
        getPreferencesAndRemindOfSettingsIfEmpty();
    }

    private void createGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }
    }

    //A help Toast for the user to remember to fill in settings
    //Shows every time MainActivity is loaded
    private void getPreferencesAndRemindOfSettingsIfEmpty() {
        pref = getApplicationContext().getSharedPreferences(SettingsActivity.MY_PREFS_NAME, MODE_PRIVATE);
        mailer = new Mailer(pref);

        if (pref.getString("sendTo", "").equals("")) {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toastMessageSettings);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void sendMailHomeButton(View view) {
        sendMailWithTextAndDisplayMessageMailSent(getString(R.string.subjectHome), getString(R.string.bodyHome));
    }

    public void sendMailSchoolButton(View view) {
        sendMailWithTextAndDisplayMessageMailSent(getString(R.string.subjectSchool), getString(R.string.bodySchool));
    }

    public void sendMailIAMHereButton(View view) {
        sendMailWithTextAndDisplayMessageMailSent(getString(R.string.subjectIAmHere), getString(R.string.bodyIAmHere));
    }

    public void sendMailWithTextAndDisplayMessageMailSent(String subject, String body) {
        String bodyWithLinkToMap = body + "<p/>" + getString(R.string.bodyTextBeforeMaplink) + "http://www.google.com/maps/place/" +
                mLastLocation.getLatitude() +"," + mLastLocation.getLongitude();
        mailer.sendMail(subject, bodyWithLinkToMap);
        startDisplayMessageMailSent();
    }

    //Displays message sent when any of the buttons are clicked
    private void startDisplayMessageMailSent() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, getString(R.string.messageMailSent));
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_TO_ACCESS_LOCATION);
            return;
        }
        mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);

        //if (mLastLocation != null) {
        //    System.out.println("LATITUD ÄR: " + String.valueOf(mLastLocation.getLatitude()));
        //    System.out.println("LONGITUD ÄR: " + String.valueOf(mLastLocation.getLongitude()));
        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_TO_ACCESS_LOCATION: {
                //If request denied, array is empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //We got permission! Call the location service!
                    onConnected(null);
                } else {
                    //no permissions received
                    //TODO: Make sure we dont ask permissions again
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
