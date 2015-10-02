package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
            Toast.makeText(context, json.getString("message"),Toast.LENGTH_SHORT).show();
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

    public void setLoginDetailsData(JSONObject json)
    {
        try{
            CommonResources resources = new CommonResources(context);
            resources.saveToSharedPrefs("uniqueid", json.getString("userid"));
            resources.saveToSharedPrefs("isLoggedIn", "true");
            resources.saveToSharedPrefs("username", json.getString("username"));

            LoginDetails.getInstance().setUserid(json.getString("userid"));
            String personName = json.getString("name");
            LoginDetails.getInstance().setPersonName("null".equalsIgnoreCase(personName) ? null : personName);
            LoginDetails.getInstance().setGender(json.getString("gender"));
            LoginDetails.getInstance().setEmail(json.getString("username"));
            LoginDetails.getInstance().setBirthday(json.getString("birthdate"));
            String mobileNum = json.getString("mobilenum");
            LoginDetails.getInstance().setMobilenum("null".equalsIgnoreCase(mobileNum) ? null : mobileNum);
            LoginDetails.getInstance().setMob_verified(json.getString("mob_verified"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void navigateToManageUser(FragmentManager fragmentManager) {
        // update the main content by replacing fragments


        Fragment fragment = null;
        fragment = new ManageUser();


        if (fragment != null) {

//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).addToBackStack("Manage User").commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating Manage user fragment");
        }
    }

}
