package common.barter.com.barterapp;

import android.content.SharedPreferences;


/**
 * Created by Panda on 15-05-2016.
 */
public class SplashModel {


    public void setUserDateDetailsData(SharedPreferences prefs)
    {
        try{

            LoginDetails.getInstance().resetDetails();
            String userId = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_UNIQUE_ID);

            if(null == userId || "null".equalsIgnoreCase(userId) || "".equalsIgnoreCase(userId)){
                //User not logged in
                return;
            }
            LoginDetails.getInstance().setUserid(userId);
            String loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_PERSON_NAME);
            LoginDetails.getInstance().setPersonName("null".equalsIgnoreCase(loadedString) ? null : loadedString);
            loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_GENDER);
            LoginDetails.getInstance().setGender(loadedString);
            loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_EMAIL);
            LoginDetails.getInstance().setEmail(loadedString);
            loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_MOBILE);
            LoginDetails.getInstance().setMobilenum("null".equalsIgnoreCase(loadedString) ? null : loadedString);
            loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED);
            LoginDetails.getInstance().setMob_verified(loadedString);
            loadedString = (String)DeviceStoreUtil.loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_LOGIN_MODE);
            LoginDetails.getInstance().setLoginMethod(loadedString);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void startThreadForLocationSetting(final OnAnimationFinishedListener listener, final SharedPreferences prefs) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setLocationAndNavigateToHome(listener, prefs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {


                }
            }
        });

        t.start();
    }

    public void updateLocationInSharedPrefs(final SharedPreferences prefs){
        if(!MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    DeviceStoreUtil.saveToSharedPrefs(prefs, "location", GlobalHome.location);
                }
            });
            t.start();
        }
    }



    public void setLocationAndNavigateToHome(OnAnimationFinishedListener listener,SharedPreferences prefs) throws InterruptedException{

        Object locationFromPrefs = DeviceStoreUtil.loadFromSharedPrefs(prefs, "location");
        if(locationFromPrefs != null){
            GlobalHome.location = (String)locationFromPrefs;
            Thread.sleep(1900);
            listener.onSuccess();
        }else{
            Thread.sleep(1900);
            listener.onFailure();

        }
    }
}
