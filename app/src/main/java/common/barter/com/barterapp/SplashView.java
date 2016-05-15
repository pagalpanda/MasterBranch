package common.barter.com.barterapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;

public class SplashView extends AppCompatActivity implements LocationAddress.LocationCallback {

    ImageView imgLogo;
    Intent iNew;
    SplashPresenter splashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (splashPresenter == null)
            splashPresenter = new SplashPresenter();
        splashPresenter.onTakeView(this);



    }

    public void positionActivityBelowStatusBar() {
        //Following lines set the activity to be aligned below the status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void setLogoAnimation() {
        Animation blink =AnimationUtils.loadAnimation(this,R.anim.blink);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgLogo.startAnimation(blink);
        imgLogo.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStart() {
        super.onStart();

        splashPresenter.onStartView();
        iNew = new Intent(SplashView.this,
                GlobalHome.class);






    }




    public void navigateToGlobalHome() {
        startActivity(iNew);
    }

    public void overrideAnimation() {
        this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
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



    @Override
    public void UpdateMyLocation(final SharedPreferences prefs) {
        navigateToGlobalHome();
        if(!MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveToSharedPrefs(prefs, "location", GlobalHome.location);
                }
            });
            t.start();
        }

        //imgLogo.setAnimation(null);
    }
    public void saveToSharedPrefs(SharedPreferences prefs,String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }

}
