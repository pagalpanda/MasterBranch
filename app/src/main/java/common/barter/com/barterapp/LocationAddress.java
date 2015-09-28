package common.barter.com.barterapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class LocationAddress extends AsyncTask<String, String, String> implements ConnectionCallbacks,
        OnConnectionFailedListener{
    private Context context;

    private static final String TAG = LocationAddress.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private double latitude,longitude;
    private String address,locality, pinCode, countryCode;

    public static volatile String location_city = MessagesString.LOCATION_SET_MANUALLY;

    private void setLatitude (double latitude) {this.latitude=latitude;}
    private void setLongitude (double longitude) {this.longitude=longitude;}
    private void setLocality (String locality) {this.locality=locality; location_city = locality;}
    private void setPinCode (String pinCode) {this.pinCode=pinCode;}
    private void setCountryCode (String countryCode) {this.countryCode=countryCode;}
    private void setAddress (String address) {this.address=address;}
    // To be called in calling class
    public String getCountryCode () {return countryCode;}
    public double getLatitude () {return latitude;}
    public String getPinCode () {return pinCode;}
    public String getLocality () {return locality;}
    public double getLongitude () {return longitude;}
    public String getAddress () {return address;}
    SharedPreferences prefs;
    private boolean isLocationErrorShown ;
    LocationCallback callerActivity;
    Activity activity;

    public LocationAddress(Context context, Activity activity)
    {

        this.context= context;//activity.getApplicationContext();
        this.callerActivity = (LocationCallback)activity;
        this.activity = activity;


    }

    @SuppressLint("InlinedApi")
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        setLocation();
        //setLocation();

           // prefs  = PreferenceManager.getDefaultSharedPreferences(context);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //callerActivity.UpdateMyLocation();
    }

    public interface LocationCallback {

        void UpdateMyLocation();
    }

    public void setLocation() {
        // Check if Google Play Services are available on device
        //prefs  = PreferenceManager.getDefaultSharedPreferences(context);
        if (checkPlayServices())
        {
            // Build Google API Client
            buildGoogleApiClient();
            if (mGoogleApiClient !=null)
            {
                mGoogleApiClient.connect();
            }
            else
            {
                if(!isLocationErrorShown)
                    showLocationError();//Please check connectivity
            }
        }
        else
            showLocationError();

    }

    public  void flash(String message){

        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    private void getLatLong() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation == null) {

            showLocationError();
        } else {

            setLatitude(mLastLocation.getLatitude());
            setLongitude(mLastLocation.getLongitude());
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        try {
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    GlobalHome.location = MessagesString.LOCATION_SET_MANUALLY;
                    flash("This device is not supported.");
                }
                return false;
            }
        }catch (Exception e){
            Log.i(TAG,"Exception while checking services\n" +
                    "    "+ e.getMessage());
        }
        return true;
    }

    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

        showLocationError();//"Connection failed. Please try again later";

    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        getLatLong();
        try {
            getAddressFromLocation(latitude, longitude,
                    context);
        }catch (Exception e){
            Log.i(TAG,"Unable connect to Geocoder\n" +
                    "    java.io.IOException: Timed out waiting for response from server"+ e.getMessage());
        }


    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public void getAddressFromLocation(final double latitude, final double longitude,
                                       final Context context) {

                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();


                        GlobalHome.location = address.getLocality().toString();
                        callerActivity.UpdateMyLocation();

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                }catch (Exception e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {

//                    Message message = Message.obtain();
//                    message.setTarget(handler);
//                    if (result != null) {
//                        message.what = 1;
//                        Bundle bundle = new Bundle();
//                        result = "Latitude:" + " \n" + result;
//                        bundle.putString("address", result);
//                        message.setData(bundle);
//                    } else {
//                        message.what = 1;
//                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n Unable to get address for this lat-long.";
//                        bundle.putString("address", result);
//                        showLocationError();
//
//                        message.setData(bundle);
//                    }
//                    message.sendToTarget();
                }


    }


    private class GeocoderHandler extends Handler {


        @Override
        public void handleMessage(Message message) {
            String locationAddress[];
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    //flash(bundle.getString("address"));
                    setAddress(bundle.getString("address"));
                    locationAddress = bundle.getString("address").split("\n");
                    break;
                default:
                    locationAddress = null;
            }
            if ( (locationAddress!=null) && (locationAddress.length>3))
            {
                //flash("ADD: "+ locationAddress[1] + locationAddress[2]+locationAddress[3]);
                location_city = locationAddress[1];
                setLocality(locationAddress[1]);
                setPinCode(locationAddress[2]);
                setCountryCode(locationAddress[3]);
                saveToSharedPrefs("location", locationAddress[1]);
                GlobalHome.location = location_city;
                callerActivity.UpdateMyLocation();

            }else{

                showLocationError();
            }
        }
    }


    public void saveToSharedPrefs(String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }



    private void showLocationError(){
        if(!isLocationErrorShown) {
            flash(MessagesString.LOCATION_NOT_READ);
            if("".equalsIgnoreCase(GlobalHome.location) || null == GlobalHome.location)
            GlobalHome.location = MessagesString.LOCATION_SET_MANUALLY;
            callerActivity.UpdateMyLocation();
        }
        isLocationErrorShown = true;

    }
}
