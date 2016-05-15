package common.barter.com.barterapp;



import android.content.SharedPreferences;
import android.view.WindowManager;


public class SplashPresenter implements OnAnimationFinishedListener{
    SplashView splashView;
    SharedPreferences prefs;
    SplashModel splashModel;


    public SplashPresenter() {
        splashModel = new SplashModel();
    }


    public void onTakeView(SplashView view) {
        this.splashView = view;
        publish();
    }

    private void publish() {
        splashView.setLogoAnimation();
        positionActivityBelowStatusBar();
        prefs  = splashView.getDefaultSharedPreferences();

    }

    public void onStartView() {
        overrideAnimation();
        splashModel.startThreadForLocationSetting(this, prefs);
        splashModel.setUserDateDetailsData(prefs);
    }

    public void overrideAnimation() {
        splashView.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }


    public void positionActivityBelowStatusBar() {
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
