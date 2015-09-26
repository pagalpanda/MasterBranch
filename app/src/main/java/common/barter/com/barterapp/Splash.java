package common.barter.com.barterapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.gson.Gson;

import java.util.concurrent.CountDownLatch;

public class Splash extends AppCompatActivity implements LocationAddress.LocationCallback {
    SharedPreferences prefs;
    LocationAddress la;
    ImageView imgLogo;
    Intent inew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setLogoAnimation();
        prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Following lines set the activity to be aligned below the status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void setLogoAnimation() {
        Animation blink =AnimationUtils.loadAnimation(this,R.anim.blink);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgLogo.startAnimation(blink);
        imgLogo.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        inew = new Intent(Splash.this,
                GlobalHome.class);
        this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Object locationFromPrefs = loadFromSharedPrefs("location");
                    if(locationFromPrefs != null){
                        GlobalHome.location = (String)locationFromPrefs;
                        Thread.sleep(1900);
                        startActivity(inew);
                    }else{
                        Thread.sleep(1900);
                        if(!CommonResources.isNetworkAvailable(Splash.this)) {
                            GlobalHome.location = MessagesString.LOCATION_SET_MANUALLY;
                            startActivity(inew);
                        }else {

                            new LocationAddress(getApplicationContext(), Splash.this).execute();
                        }
                    }
//                    Thread.sleep(1900);
//                    startActivity(inew);

                    //imgLogo.setAnimation(null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {


                }
            }
        });

        t.start();



    }

    private void readLocation() {
//        new LocationAddress(getApplicationContext(),this).execute();
//        LocationAddress la = new LocationAddress(getApplicationContext(), this);
  //      la.setLocation();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    Object obj;
    public  Object loadFromSharedPrefs(String key){
        Gson gson = new Gson();
        obj = null;
        if(prefs != null) {
            String json = prefs.getString(key, "");
            obj = gson.fromJson(json, String.class);
        }
        return obj;
    }


    @Override
    public void UpdateMyLocation() {
        startActivity(inew);
        if(!MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveToSharedPrefs("location", GlobalHome.location);
                }
            });
            t.start();
        }

        //imgLogo.setAnimation(null);
    }
    public void saveToSharedPrefs(String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }

}
