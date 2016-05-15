package common.barter.com.barterapp;



import android.content.SharedPreferences;


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
        splashView.positionActivityBelowStatusBar();
        prefs  = splashView.getDefaultSharedPreferences();

    }

    public void onStartView() {
        splashView.overrideAnimation();
        splashModel.startThreadForLocationSetting(this,prefs);
        splashModel.setUserDateDetailsData(prefs);
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
}
