package common.barter.com.barterapp;

import android.content.Context;

import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikram on 04/09/15.
 */
public class LoginDetails {

    private static LoginDetails ld = null;

    private String userid;
    private String name;
    private String email;
    private String gender;
    private String gps_location;
    private String password;
    private String mob_verified;
    private String mobilenum;

    public String getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    private String loginMethod;

    public enum loggedInMode{
        GPLUS,FACEBOOK,MANUAL
    }

    private Boolean isverifying;
    private String otp_received_from_web;
    private String otp_received_from_device;

    private LoginDetails (){

    }

    public static LoginDetails getInstance() {
        if (ld == null)
            ld = new LoginDetails();
        return ld;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender!=null){
            if ((gender.equalsIgnoreCase("male"))|| (gender.equalsIgnoreCase("m"))) {
                this.gender = "M";
            }
            else if ((gender.equalsIgnoreCase("female"))|| (gender.equalsIgnoreCase("f"))) {
                this.gender = "F";
            }
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMob_verified() {
        return mob_verified;
    }

    public void setMob_verified(String mob_verified) {
        this.mob_verified = mob_verified;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public Boolean getIsverifying() {
        return isverifying;
    }

    public void setIsverifying(Boolean isverifying) {
        this.isverifying = isverifying;
    }

    public String getOtpReceivedFromWeb() {
        return otp_received_from_web;
    }

    public void setOtpReceivedFromWeb(String otp_received_from_web) {
        this.otp_received_from_web = otp_received_from_web;
    }
    public String getOtpReceivedFromDevice() {
        return otp_received_from_device;
    }

    public void setOtpReceivedFromDevice(String otp_received_from_device) {
        this.otp_received_from_device = otp_received_from_device;
    }

    public void resetDetails()
    {
        userid=null;
        name = null;
        email = null;
        gender = null;
        mobilenum=null;
        isverifying = false;
        otp_received_from_device = null;
        otp_received_from_web = null;
    }

    public void saveLoginDetailsToDevice(Context context, JSONObject json)
    {
        try {
            CommonResources resources = new CommonResources(context);
            resources.saveToSharedPrefs("isLoggedIn", "true");
            Map<String, String> mapUserDetails = new HashMap<>();
            String userId = json.getString("userid");
            mapUserDetails.put(MessagesString.SHARED_PREFS_UNIQUE_ID, userId);
            String personName = json.getString("name");
            mapUserDetails.put(MessagesString.SHARED_PREFS_PERSON_NAME, personName);
            String gender = json.getString("gender");
            mapUserDetails.put(MessagesString.SHARED_PREFS_GENDER, gender);
            String email = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_EMAIL, email);
            String username = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_USERNAME, username);
            String mobileNum = json.getString("mobilenum");
            mapUserDetails.put(MessagesString.SHARED_PREFS_MOBILE, mobileNum);
            String mobVerified = json.getString("mob_verified");
            mapUserDetails.put(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED, mobVerified);
            String loginMethod = json.getString("loginmode");
            mapUserDetails.put(MessagesString.SHARED_PREFS_LOGIN_MODE, loginMethod);
            resources.setUserDetailsInSharedPref(mapUserDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setTestUserDetails()
    {
        this.setEmail("bhativ1303@gmail.com");
        this.setPassword("bhativ1303@gmail.com123");
        this.setName("Vikram Singh Bhati");
        this.setGender("M");
        this.setLoginMethod("GPLUS");
        this.setMob_verified("1");
        this.setMobilenum("7032910032");
        this.setUserid("1");

    }
    public void setUpForNewOtpRequest() {
        LoginDetails.getInstance().setIsverifying(true);
        LoginDetails.getInstance().setMob_verified(MessagesString.MOB_VERIFIED_IS_FALSE);
        LoginDetails.getInstance().setOtpReceivedFromDevice(null);
        LoginDetails.getInstance().setOtpReceivedFromWeb(null);
    }

    public void setUpAfterOtpVerified() {
        LoginDetails.getInstance().setIsverifying(false);
        LoginDetails.getInstance().setOtpReceivedFromDevice(null);
        LoginDetails.getInstance().setOtpReceivedFromWeb(null);
    }

}
