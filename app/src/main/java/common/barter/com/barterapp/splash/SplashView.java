package common.barter.com.barterapp.splash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.LocationAddress;
import common.barter.com.barterapp.R;

public class SplashView extends AppCompatActivity implements LocationAddress.LocationCallback {

    private ImageView imgLogo;

    private SplashPresenter splashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (splashPresenter == null) {
            splashPresenter = new SplashPresenter();
        }
        splashPresenter.onTakeView(this);
    }



    SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    void setLogoAnimation() {
        Animation blink =AnimationUtils.loadAnimation(this,R.anim.blink);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgLogo.startAnimation(blink);
        imgLogo.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStart() {
        super.onStart();

        splashPresenter.onStartView();

   }

    void navigateToGlobalHome() {
        Intent iNew = new Intent(SplashView.this,
                GlobalHome.class);
        startActivity(iNew);
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
        splashPresenter.onLocationRead(prefs);

    }


}
