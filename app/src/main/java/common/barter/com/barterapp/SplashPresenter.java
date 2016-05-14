package common.barter.com.barterapp;



import android.content.SharedPreferences;


public class SplashPresenter{
    SplashView splashView;
    SharedPreferences prefs;


    public SplashPresenter() {
    }


    public void onTakeView(SplashView view) {
        this.splashView = view;
        publish();
    }

    private void publish() {
        splashView.setLogoAnimation();
        splashView.positionActivityBelowStatusBar();
        prefs  = splashView.getDefaultSharedPreferences();

    }

    public void onStartView() {
        splashView.overrideAnimation();
        splashView.startThreadForLocationSetting(prefs);
        splashView.setUserDateDetailsData(prefs);
    }



}
