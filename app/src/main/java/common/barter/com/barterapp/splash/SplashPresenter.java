package common.barter.com.barterapp.splash;



import android.content.SharedPreferences;
import android.view.WindowManager;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.GlobalHome;
import common.barter.com.barterapp.LocationAddress;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.OnAnimationFinishedListener;
import common.barter.com.barterapp.R;


public class SplashPresenter implements OnAnimationFinishedListener {
    private SplashView splashView;
    private SharedPreferences prefs;
    private SplashModel splashModel;


    public SplashPresenter() {
        splashModel = new SplashModel();
    }


    void onTakeView(SplashView view) {
        this.splashView = view;
        publish();
    }

    void publish() {
        splashView.setLogoAnimation();
        positionActivityBelowStatusBar();
        prefs  = splashView.getDefaultSharedPreferences();

    }

    void onStartView() {
        overrideAnimation();
        splashModel.startThreadForLocationSetting(this, prefs);
        splashModel.setUserDateDetailsData(prefs);
    }

    void overrideAnimation() {
        splashView.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }


    void positionActivityBelowStatusBar() {
        //Following lines set the activity to be aligned below the status bar
        splashView.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        splashView.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onSuccess() {
        splashView.navigateToGlobalHome();
    }

    @Override
    public void onFailure() {
        if(!CommonResources.isNetworkAvailable(splashView)) {
            GlobalHome.location = MessagesString.LOCATION_SET_MANUALLY;
            onSuccess();
        }else {
            new LocationAddress(splashView.getApplicationContext(), splashView).execute();
        }
    }

    void onLocationRead(SharedPreferences prefs){
        splashView.navigateToGlobalHome();
        splashModel.updateLocationInSharedPrefs(prefs);
    }
}
