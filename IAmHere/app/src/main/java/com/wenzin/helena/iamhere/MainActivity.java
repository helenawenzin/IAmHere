package com.wenzin.helena.iamhere;

import android.Manifest;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

import static com.google.android.gms.location.LocationServices.API;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.iamhere.MESSAGE";
    private static final int MY_PERMISSIONS_REQUEST_TO_ACCESS_LOCATION = 222;

    private SharedPreferences pref;
    private Mailer mailer;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGoogleApiClient();
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences(SettingsActivity.MY_PREFS_NAME, MODE_PRIVATE);
        mailer = new Mailer(pref);
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

    private void createGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }
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
        String bodyHome = "Hej! Nu har jag kommit hem!"; //TODO:xml-file
        sendMailWithTextAndDisplayMessageMailSent("Hemma!", bodyHome);
    }

    public void sendMailSchoolButton(View view) {
        String bodySchool = "Hej! Nu är jag i skolan!"; //TODO:xml-file
        sendMailWithTextAndDisplayMessageMailSent("I skolan!", bodySchool);
    }

    public void sendMailIAMHereButton(View view) {
        String bodyIAmHere = "Hej! Nu är jag här!"; //TODO:xml-file
        sendMailWithTextAndDisplayMessageMailSent("Här är jag!", bodyIAmHere);
    }

    public void sendMailWithTextAndDisplayMessageMailSent(String subject, String body) {
        String bodyWithLinkToMap = body + "<p/>" + " och på kartan är jag: " + "http://www.google.com/maps/place/" +
                mLastLocation.getLatitude() +"," + mLastLocation.getLongitude();
        mailer.sendMail(subject, bodyWithLinkToMap);
        startDisplayMessageMailSent();
    }

    private void startDisplayMessageMailSent() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, getString(R.string.messageMailSent));
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permissions saknas");
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

        if (mLastLocation != null) {
            System.out.println("LATITUD ÄR: " + String.valueOf(mLastLocation.getLatitude()));
            System.out.println("LONGITUD ÄR: " + String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("Yay, got a permissions callback!");

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_TO_ACCESS_LOCATION: {
                //If request denied, array is empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Hooray! We got permission! Call the location service!
                    onConnected(null);
                } else {
                    //Cry cry, no permissions, dont ask again!
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
