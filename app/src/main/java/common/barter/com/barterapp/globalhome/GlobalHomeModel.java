package common.barter.com.barterapp.globalhome;

import android.content.SharedPreferences;

import common.barter.com.barterapp.DeviceStoreUtil;

/**
 * Created by Panda on 15-05-2016.
 */
public class GlobalHomeModel {
    public void saveLocationDetails(String cityName, SharedPreferences prefs) {
        DeviceStoreUtil.saveToSharedPrefs(prefs, "location", cityName);
        GlobalHome.location = cityName;
    }
}
