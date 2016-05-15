package common.barter.com.barterapp;

import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Panda on 15-05-2016.
 */
public class DeviceStoreUtil {

    public static void saveToSharedPrefs(SharedPreferences prefs,String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }

    public static Object loadFromSharedPrefs(SharedPreferences prefs,String key){
        Gson gson = new Gson();
        Object obj = null;
        if(prefs != null) {
            String json = prefs.getString(key, "");
            obj = gson.fromJson(json, String.class);
        }
        return obj;
    }
}
