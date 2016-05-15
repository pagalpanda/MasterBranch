package common.barter.com.barterapp;

import android.content.SharedPreferences;

/**
 * Created by Panda on 15-05-2016.
 */
public class GlobalHomeModel {
    public void saveLocationDetails(String cityName, SharedPreferences prefs) {
        DeviceStoreUtil.saveToSharedPrefs(prefs, "location", cityName);
        GlobalHome.location = cityName;
    }
}
