package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikram on 15/09/15.
 */
class LoginAsync extends AsyncTask<String, String, String> {

/**
 * Before starting background thread Show Progress Dialog
 * */

    private Context context;
    private int login_mode;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    FragmentManager fragmentmanager;

    public LoginAsync (Context context,int login_mode,FragmentManager fragmentmanager)
    {
        this.context=context;
        this.login_mode=login_mode;
        jsonParser = new JSONParser();
        this.fragmentmanager=fragmentmanager;
    }

    @Override
protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loggging in..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        }

/**
 * Creating product
 * */
protected String doInBackground(String... args) {

        // Building Parameters
        HashMap<String,String> params = new HashMap <String,String>();
        params.put("username", LoginDetails.getInstance().getEmail());
        params.put("password", LoginDetails.getInstance().getPassword());
        params.put("loginmode", String.valueOf(login_mode));
        String personName = LoginDetails.getInstance().getPersonName();
        if (personName !=null && personName.trim().length() != 0)
            params.put("personname", LoginDetails.getInstance().getPersonName());
        String mobileNum = LoginDetails.getInstance().getMobilenum();
        if (mobileNum != null && mobileNum.trim().length() != 0)
            params.put("mobilenum", LoginDetails.getInstance().getMobilenum());
        String gender = LoginDetails.getInstance().getGender();
        if (gender != null && gender.length() != 0)
            params.put("gender", LoginDetails.getInstance().getGender());
        String birthDate = LoginDetails.getInstance().getBirthday();
        if (birthDate != null && birthDate.trim().length() != 0)
            params.put("birthdate", LoginDetails.getInstance().getBirthday());


        // getting JSON Object
        // Note that create product url accepts POST method

        JSONObject json = jsonParser.makeHttpRequest(CommonResources.getURL("login"),
        "POST", params);
    LoginDetails.getInstance().resetDetails();


//    JSONObject json = new JSONObject();
//    try {
//        json.put("success",0);
//        json.put("error",200);
//        json.put("userid","1");
//        json.put("message","");
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
    //{"success":"0", "error":"200", "userid":"1", "message":""};
        // check log cat fro response
        if(json == null){
            return null;
        }
        Log.d("Create Response", json.toString());

        // check for success tag
        String TAG_SUCCESS = "success";
        try {

            int success = json.getInt(TAG_SUCCESS);

        if (success == 0) {
        // successfully created product
//                    Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_LONG).show();
            setLoginDetailsData(json);
            navigateToManageUser(fragmentmanager);
        //finish();
        } else if(success == 1){
        // failed to create product
            setLoginDetailsData(json);
            navigateToManageUser(fragmentmanager);
        }else if(success == 2){
            //Toast.makeText(context, json.getString("message"),Toast.LENGTH_SHORT).show();
        //Invalid input
        }
        } catch (JSONException e) {
        e.printStackTrace();
        }

        return null;

        }

/**
 * After completing background task Dismiss the progress dialog
 * **/
protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        pDialog.dismiss();
        }

    // Following method creates a map and sends the value to be saved in SharedPrefs and in the LoginDetails singleton class
    public void setLoginDetailsData(JSONObject json)
    {
        try{
            CommonResources resources = new CommonResources(context);
            resources.saveToSharedPrefs("isLoggedIn", "true");
            Map<String,String> mapUserDetails  = new HashMap<>();
            String userId = json.getString("userid");
            mapUserDetails.put(MessagesString.SHARED_PREFS_UNIQUE_ID,userId);
            String personName = json.getString("name");
            mapUserDetails.put(MessagesString.SHARED_PREFS_PERSON_NAME,personName);
            String gender = json.getString("gender");
            mapUserDetails.put(MessagesString.SHARED_PREFS_GENDER,gender);
            String email = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_EMAIL, email);
            String username = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_USERNAME,username);
            String mobileNum = json.getString("mobilenum");
            mapUserDetails.put(MessagesString.SHARED_PREFS_MOBILE,mobileNum);
            String mobVerified = json.getString("mob_verified");
            mapUserDetails.put(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED,mobVerified);
            String loginMethod = json.getString("loginmode");
            mapUserDetails.put(MessagesString.SHARED_PREFS_LOGIN_MODE,loginMethod);
            resources.setUserDetailsInSharedPref(mapUserDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void navigateToManageUser(FragmentManager fragmentManager) {
        // update the main content by replacing fragments


        Fragment fragment = null;
        fragment = new ManageUser();

        FragmentTransaction ft  = fragmentManager.beginTransaction();

        if (fragment != null) {

//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).addToBackStack("Manage User").commit();
            ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
            fragmentManager.beginTransaction()
                    .add(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating Manage user fragment");
        }
    }

}
