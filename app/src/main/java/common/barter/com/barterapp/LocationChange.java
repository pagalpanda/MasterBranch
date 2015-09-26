package common.barter.com.barterapp;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import android.preference.PreferenceManager;
/**
 * Created by amitpa on 8/21/2015.
 */
public class LocationChange {
    static SharedPreferences prefs;

    public static void saveLocationToPrefs(String location){

            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(location, String.class);
            editor.putString("MyLocation", json);
            editor.commit();

    }
    static Object obj;
    public static Object loadLocationFromSharedPrefs(){
        Gson gson = new Gson();
        obj = null;
        if(prefs != null) {
            String json = prefs.getString("MyLocation", "");
            if(obj != null)
                obj = gson.fromJson(json, String.class);
            //obj = gson.fromJson(json, Object.class);

        }
        return obj;
    }
}
