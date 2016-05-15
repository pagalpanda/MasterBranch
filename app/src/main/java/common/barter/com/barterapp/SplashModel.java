package common.barter.com.barterapp;

import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Panda on 15-05-2016.
 */
public class SplashModel {


    public void setUserDateDetailsData(SharedPreferences prefs)
    {
        try{

            LoginDetails.getInstance().resetDetails();
            String userId = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_UNIQUE_ID);

            if(null == userId || "null".equalsIgnoreCase(userId) || "".equalsIgnoreCase(userId)){
                //User not logged in
                return;
            }
            LoginDetails.getInstance().setUserid(userId);
            String personName = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_PERSON_NAME);
            LoginDetails.getInstance().setPersonName("null".equalsIgnoreCase(personName) ? null : personName);
            String gender = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_GENDER);
            LoginDetails.getInstance().setGender(gender);
            String email = (String)loadFromSharedPrefs(prefs, MessagesString.SHARED_PREFS_EMAIL);
            LoginDetails.getInstance().setEmail(email);
            String mobileNum = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_MOBILE);
            LoginDetails.getInstance().setMobilenum("null".equalsIgnoreCase(mobileNum) ? null : mobileNum);
            String mobVerified = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED);
            LoginDetails.getInstance().setMob_verified(mobVerified);
            String loginMode = (String)loadFromSharedPrefs(prefs,MessagesString.SHARED_PREFS_LOGIN_MODE);
            LoginDetails.getInstance().setLoginMethod(loginMode);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Object obj;
    public  Object loadFromSharedPrefs(SharedPreferences prefs,String key){
        Gson gson = new Gson();
        obj = null;
        if(prefs != null) {
            String json = prefs.getString(key, "");
            obj = gson.fromJson(json, String.class);
        }
        return obj;
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

    public void setLocationAndNavigateToHome(OnAnimationFinishedListener listener,SharedPreferences prefs) throws InterruptedException{

        Object locationFromPrefs = loadFromSharedPrefs(prefs,"location");
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
