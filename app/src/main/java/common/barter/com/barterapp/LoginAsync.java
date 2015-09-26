package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

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
        //params.put("personname", LoginDetails.getInstance().getPersonName());
        if (LoginDetails.getInstance().getMobilenum()!=null)
            params.put("mobilenum", LoginDetails.getInstance().getMobilenum());
        if (LoginDetails.getInstance().getPersonName()!=null)
            params.put("personname", LoginDetails.getInstance().getPersonName());
        else
            params.put("personname", "");
        if (LoginDetails.getInstance().getGender()!=null)
            params.put("gender", LoginDetails.getInstance().getGender());
        if (LoginDetails.getInstance().getBirthday()!=null)
            params.put("birthdate", LoginDetails.getInstance().getBirthday());


        // getting JSON Object
        // Note that create product url accepts POST method

        JSONObject json = jsonParser.makeHttpRequest(CommonResources.getURL("login"),
        "POST", params);


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
        CommonResources resources = new CommonResources(context);
        resources.saveToSharedPrefs("isLoggedIn", "true");
        resources.saveToSharedPrefs("username", LoginDetails.getInstance().getEmail());
        resources.saveToSharedPrefs("uniqueid", json.getString("userid"));
        setLoginDetailsData(json);
        navigateToManageUser(fragmentmanager);
        //finish();
        } else if(success == 1){
        // failed to create product
        CommonResources resources = new CommonResources(context);
        resources.saveToSharedPrefs("uniqueid", json.getString("userid"));
        resources.saveToSharedPrefs("isLoggedIn", "true");
        resources.saveToSharedPrefs("username", LoginDetails.getInstance().getEmail());
        setLoginDetailsData(json);
        navigateToManageUser(fragmentmanager);
        }else{
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
            LoginDetails.getInstance().setUserid(json.getString("userid"));
            LoginDetails.getInstance().setPersonName(json.getString("name"));
            LoginDetails.getInstance().setGender(json.getString("gender"));
            LoginDetails.getInstance().setEmail(json.getString("userid"));
            LoginDetails.getInstance().setBirthday(json.getString("birthdate"));
            LoginDetails.getInstance().setMobilenum(json.getString("moblilenum"));
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
