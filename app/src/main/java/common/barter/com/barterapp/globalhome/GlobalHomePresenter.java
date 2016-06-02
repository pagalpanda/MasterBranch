package common.barter.com.barterapp.globalhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.view.View;
import android.widget.AdapterView;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LocationAddress;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by Panda on 15-05-2016.
 */
public class GlobalHomePresenter {

    GlobalHome globalHomeView;
    GlobalHomeModel model;
    public GlobalHomePresenter(GlobalHome view) {
        this.globalHomeView = view;
        model = new GlobalHomeModel();
    }

    public void onCreateView() {
        globalHomeView.initializeToolbar();
        globalHomeView.createLeftNavigationBar();
        globalHomeView.setActionBarBehavior();
        globalHomeView.setActionBarToggler();
        globalHomeView.setDrawerListener();
        readLocationFromGPSIfNotSet();
        globalHomeView.displayHomeFragmentByDefault();
    }

    public void onLeftNavClicked(View view, int position) {
        if (position != 0) {
            globalHomeView.displayView(position - 1);
            globalHomeView.setActionBarTitleForNavBar(view);
        }else{
            globalHomeView.showLocationDialog();
        }
    }

    public void onLocationSelect(AdapterView<?> parent, View view, int position, SharedPreferences prefs) {
        String cityName = (String) parent.getItemAtPosition(position);
        model.saveLocationDetails(cityName, prefs);


    }

    public void onCurrentLocationClick() {
        readLocationForced();
    }

    private void readLocationForced() {
        Context applicationContext = globalHomeView.getApplicationContext();
        if (CommonResources.isNetworkAvailable(globalHomeView)) {
            globalHomeView.setReadLocationForce(true);
            if (checkGPS()) {
                globalHomeView.setReadLocationForce(true);
                new LocationAddress(applicationContext, globalHomeView).execute();

            }
        } else {
            CommonUtil.flash(applicationContext, MessagesString.CONNECT_TO_INTERNET);

        }
    }

    public boolean checkGPS() {
        LocationManager locationManager = (LocationManager) globalHomeView.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        requestLocationUpdates(locationManager);

        if (!gps_enabled && !network_enabled && (CommonResources.isNetworkAvailable(globalHomeView)) ) {
            // notify user
            globalHomeView.showDialogForEnablingGPS();
            return false;
        }else {
            return true;
        }

    }

    private void readLocationFromGPSIfNotSet(){
        if(MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)){
            checkGPS();

        }
    }


    private void requestLocationUpdates(LocationManager locationManager) {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 100.0F, globalHomeView);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 100.0F, globalHomeView);
    }

}
